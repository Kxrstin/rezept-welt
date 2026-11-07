document.addEventListener("DOMContentLoaded", function () {
    const modal = document.getElementById("userModal");
    const userProfile = document.getElementById("user-profile-bar");
    const displayUserName = document.getElementById("displayUserName");
    const editProfileBtn = document.getElementById("editProfileBtn");
    const modalUsernameInput = document.getElementById("modalUsername");
    const modalDescInput = document.getElementById("modalDesc");
    const snackbar = document.getElementById("snackbar");

    const KEY_NAME = "familie_username";
    const KEY_DESC = "familie_desc";
    const KEY_PIC = "familie_pic";
    const KEY_COL = "familie_col";

// --- Hilfsfunktion für die Snackbar ---
function showSnackbar(message, isError = false) {
    if (!snackbar) return;
    snackbar.textContent = message;
    snackbar.className = "snackbar show";

    if (isError) {
        snackbar.classList.add("error");
    } else {
        snackbar.classList.remove("error");
    }

        setTimeout(function () {
            snackbar.className = "snackbar";
        }, 3500);
    }

    // Server-Meldungen abfangen
    const serverSuccess = document.getElementById("serverSuccess");
    if (serverSuccess) {
        showSnackbar(serverSuccess.getAttribute("data-message"), false);
    }

    const serverError = document.getElementById("serverError");
    if (serverError) {
        showSnackbar(serverError.getAttribute("data-message"), true);
    }

    function checkUser() {
        const username = localStorage.getItem(KEY_NAME);
        if (!username || username === "null" || username.trim() === "") {
            // Ungültige alte Daten entfernen
            localStorage.removeItem(KEY_NAME);
            localStorage.removeItem(KEY_DESC);
            localStorage.removeItem(KEY_COL);

            if (modalUsernameInput) {
                modalUsernameInput.disabled = false;
                modalUsernameInput.value = "";
            }

            if (modal) modal.classList.add("is-visible");
        } else {
            updateUI(username, localStorage.getItem(KEY_PIC));
        }
    }

    function updateUI(name, pic) {
    if (displayUserName) {
        displayUserName.textContent = name;
    }

    // Profilbild-Element auswählen
    const profilePicImg = document.getElementById("userProfileImg");

    if (profilePicImg) {
    if (pic && pic.trim() !== "") {
        // Bildquelle setzen
        profilePicImg.src = pic;

        // WICHTIG: Bild sichtbar machen
        profilePicImg.classList.add("has-image");

    } else {
        // Standardbild setzen
        profilePicImg.src = "/img/default-avatar.png";

        // Auch das Standardbild sichtbar machen
        profilePicImg.classList.add("has-image");
    }

        const colors = ["Pink", "Blau", "Lila", "Orange", "Gelb", "Gruen"];
        const editButton = document.getElementById("editProfileBtn");

        // Gespeicherte Farbe auslesen
        let col = localStorage.getItem(KEY_COL);

        // Neue Farbe hinzufügen
        if (!colors.includes(col)) {
            col = "Pink";
        }

        // Alle alten Farbklassen entfernen
        if (userProfile) {
            userProfile.classList.remove(...colors);
            userProfile.classList.add(col);
        }

        if (profilePicImg) {
            profilePicImg.classList.remove(...colors);
            profilePicImg.classList.add(col);
        }

        if (editButton) {
            editButton.classList.remove(...colors);
            editButton.classList.add(col);
        }
    }

    // Modal schließen
    if (modal) {
        modal.classList.remove("is-visible");
    }
}

checkUser();

// --- 2. Profil bearbeiten Button ---
if (editProfileBtn) {
    editProfileBtn.addEventListener("click", function () {
        const currentName = localStorage.getItem(KEY_NAME);
        const savedColor = localStorage.getItem(KEY_COL);

        if (modalUsernameInput) {
            modalUsernameInput.value = currentName || "";
            modalUsernameInput.disabled = true;
        }
        if (modalDescInput) {
            modalDescInput.value = localStorage.getItem(KEY_DESC) || "";
        }

        if (savedColor) {
            const colorRadio = document.querySelector(
                `input[name="modalColor"][value="${savedColor}"]`
            );

            if (colorRadio) {
                colorRadio.checked = true;
            }
        }
        if (modal) {
            modal.classList.add("is-visible");
        }
    });
}

// --- 3. REST-API Call an Spring Boot (mit FormData für das MultipartFile) ---
async function saveAuthorToBackend(username, description, col, file, targetUrl) {
    const formData = new FormData();

    formData.append("username", username);
    formData.append("description", description);
    formData.append("color", col);

    if (file) {
        formData.append("bildMultiPart", file);
    }

    try {
        const response = await fetch(targetUrl, {
            method: "POST",
            body: formData
    });

    if (response.ok) {
        showSnackbar("Profil erfolgreich gespeichert!");
        window.location.reload();
        return true;
    }

    if (response.status === 409) {
        showSnackbar(
        "Dieser Username ist bereits vergeben. Bitte wähle einen anderen.",
        true
        );
    } else if (response.status === 404) {
        showSnackbar("User nicht gefunden.", true);
    } else if (response.status === 413) {
        showSnackbar("Das Profilbild ist zu groß.", true);
    } else if (response.status === 429) {
        showSnackbar("Zu viele Anfragen. Bitte später erneut versuchen.", true);
    } else {
        showSnackbar("Fehler beim Speichern des Profils.", true);
    }

    return false;

    } catch (error) {
        console.error(error);
        showSnackbar("Netzwerkfehler beim Speichern.", true);
        return false;
    }
}


// --- 4. Modal Formular Absenden ---
    const modalForm = document.getElementById("modalForm");
    if (modalForm) {
        modalForm.addEventListener("submit", async function (e) {
        e.preventDefault();

        const name = modalUsernameInput.disabled
            ? localStorage.getItem(KEY_NAME)
            : modalUsernameInput.value.trim();

        const desc = modalDescInput.value.trim();
        const selectedColor = document.querySelector(
            'input[name="modalColor"]:checked'
        );

        const col = selectedColor ? selectedColor.value : "Pink";

        const fileInput = document.getElementById("modalPic");
        const file = fileInput.files[0] || null;

        const targetUrl = modalUsernameInput.disabled
            ? "/api/author/update"
            : "/api/author/add";

    if (!name) return;

    if (file && file.size > 3 * 1024 * 1024) {
        showSnackbar("Das Bild ist zu groß! Maximal 3 MB erlaubt.", true);
        return;
    }

    // Erst Backend-Anfrage durchführen
    const success = await saveAuthorToBackend(
        name, desc, col, file, targetUrl
    );

    // Bei einem Fehler nichts lokal speichern
    if (!success) return;

    // Erst nach erfolgreicher Speicherung übernehmen
    localStorage.setItem(KEY_NAME, name);
    localStorage.setItem(KEY_DESC, desc);
    localStorage.setItem(KEY_COL, col);

    if (file) {
        const reader = new FileReader();

    reader.onload = function (event) {
        const base64String = event.target.result;

        localStorage.setItem(KEY_PIC, base64String);
        updateUI(name, base64String);
    };

    reader.readAsDataURL(file);
    } else {
        const existingPic = localStorage.getItem(KEY_PIC);
        updateUI(name, existingPic);
    }
    });
}

    // --- 5. Scroll-Position & Hidden Inputs beim Kommentar-Absenden befüllen ---
    const commentForm = document.getElementById("commentForm");

    const scrollPosition = sessionStorage.getItem("scrollPosition");
    if (scrollPosition) {
        window.scrollTo(0, parseInt(scrollPosition));
        sessionStorage.removeItem("scrollPosition");
    }

    if (commentForm) {
        commentForm.addEventListener("submit", function () {
        sessionStorage.setItem("scrollPosition", window.scrollY);

        const authorInput = document.getElementById("authorUsernameInput");
        if (authorInput) authorInput.value = localStorage.getItem(KEY_NAME) || "";
    });
    }
});
