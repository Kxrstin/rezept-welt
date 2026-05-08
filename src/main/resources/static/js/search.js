document.addEventListener("DOMContentLoaded", () => {
    const searchInput = document.getElementById("filterId");
    const suggestionsList = document.getElementById("suggestions");

    const speise = searchInput.getAttribute("data-category");

    searchInput.addEventListener("input", async () => {
        const query = searchInput.value;

        if(query.length < 1) {
            suggestionsList.innerHTML = "";
            return;
        }

        try {
            const response = await fetch(`/api/search-suggestions?query=${query}&speise=${speise}`);

            if (!response.ok) throw new Error("Server Fehler");

            const suggestions = await response.json();

            suggestionsList.innerHTML = suggestions
                .map(s => `<li onclick="select('${s.name}')">${s.name}</li>`)
                .join("");
        } catch (error) {
            console.error("Da lief was schief:", error);
        }
    });

    document.addEventListener("click", function(event) {
        if (event.target !== searchInput) {
            suggestionsList.innerHTML = "";
        }
    });
});

function select(value) {
    const input = document.getElementById("filterId");
    if(input) {
        input.value = value;
        document.getElementById("suggestions").innerHTML = "";
    }
}