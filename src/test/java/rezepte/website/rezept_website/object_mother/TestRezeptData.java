package rezepte.website.rezept_website.object_mother;

import org.springframework.mock.web.MockMultipartFile;
import rezepte.website.rezept_website.controller.formulare.Kategorie;
import rezepte.website.rezept_website.controller.formulare.RezeptForm;

import java.util.LinkedList;
import java.util.List;

public class TestRezeptData {

    public static MockMultipartFile demoFile() {
        return new MockMultipartFile(
                "bildMultiPart",
                "demo.txt",
                "text/plain",
                "Hello World".getBytes()
        );
    }

    public static MockMultipartFile demoFile2() {
        return new MockMultipartFile(
                "bildMultiPart",
                "demo.txt",
                "text/plain",
                "Hallo Welt".getBytes()
        );
    }

    public static RezeptForm vorspeise() {
        return new RezeptForm(
                Kategorie.Vorspeise,
                "Caesar Salad",
                "Römersalat, 50g Parmesan, Croutons, 1 Ei, Senf, Zitronensaft, Olivenöl, Knoblauch",
                "Salat waschen und zerkleinern. Aus Ei, Senf, Zitronensaft ...",
                demoFile()
        );
    }

    public static RezeptForm hauptspeise() {
        return new RezeptForm(
                Kategorie.Hauptspeise,
                "Ofengemüse mediterran",
                "Zucchini, Aubergine, Paprika, Kirschtomaten, Olivenöl, Rosmarin, Thymian",
                "Gemüse grob schneiden, mit Olivenöl und Kräutern vermengen und auf einem Blech " +
                        "verteilen. Bei 200 Grad ca. 35 Minuten im Ofen rösten.",
                demoFile()
        );
    }

    public static RezeptForm nachspeise() {
        return new RezeptForm(
                Kategorie.Nachspeise,
                "Schoko-Bananen-Pfannkuchen",
                "2 Bananen, 2 Eier, 100g Mehl, Milch, Schokotropfen",
                "Bananen zerdrücken und mit Eiern, Mehl und Milch zu einem Teig verrühren. " +
                        "Schokotropfen unterheben und in einer Pfanne goldbraun ausbacken.",
                demoFile()
        );
    }


    public static List<RezeptForm> vorspeisen() {
        List<RezeptForm> vorspeisen = new LinkedList<>();
        vorspeisen.add(new RezeptForm(
                Kategorie.Vorspeise,
                "Bruschetta Test",
                "Tomaten, Salz, Öl",
                "Zunächst Tomaten schneiden ...",
                demoFile()
        ));
        vorspeisen.add(new RezeptForm(
                Kategorie.Vorspeise,
                "Linsensalat mit Petersilie",
                "200g grüne Linsen, rote Zwiebel, Petersilie, Zitronensaft, Olivenöl, Salz",
                "Linsen weich kochen und abkühlen lassen. Mit fein gehackter Zwiebel und Petersilie vermengen...",
                demoFile()
        ));
        vorspeisen.add(vorspeise());
        return vorspeisen;
    }

    public static List<RezeptForm> hauptspeisen() {
        List<RezeptForm> hauptspeisen = new LinkedList<>();
        hauptspeisen.add(new RezeptForm(
                Kategorie.Hauptspeise,
                "Chili con Carne klassisch",
                "500g Rinderhackfleisch, 1 Dose Kidneybohnen, 1 Dose Mais, 1 Dose Tomaten, Zwiebel, Knoblauch, Chili, Kreuzkümmel",
                "Hackfleisch krümelig anbraten, Zwiebel und Knoblauch hinzufügen...",
                demoFile()
        ));
        hauptspeisen.add(new RezeptForm(
                Kategorie.Hauptspeise,
                "Hähnchen-Geschnetzeltes in Rahmsauce",
                "500g Hähnchenbrust, 200ml Sahne, 1 Zwiebel, Champignons, Paprikapulver, Salz, Pfeffer",
                "Hähnchenbrust in Streifen schneiden und scharf anbraten. Zwiebeln und Champignons hinzufügen...",
                demoFile()
        ));
        hauptspeisen.add(hauptspeise());
        return hauptspeisen;
    }

    public static List<RezeptForm> nachspeisen() {
        List<RezeptForm> nachspeisen = new LinkedList<>();
        nachspeisen.add(new RezeptForm(
                Kategorie.Nachspeise,
                "Zitronen-Joghurt-Creme",
                "500g griechischer Joghurt, 2 Zitronen, 80g Zucker, Vanillezucker",
                "Joghurt mit Zucker, Vanille und Zitronensaft glatt rühren. Mindestens 1 Stunde kalt stellen...",
                demoFile()
        ));
        nachspeisen.add(new RezeptForm(
                Kategorie.Nachspeise,
                "Vanille-Milchreis mit Zimt",
                "200g Rundkornreis, 1 Liter Milch, 1 Vanilleschote, Zucker, Zimt",
                "Milch mit Vanille aufkochen, Reis einrühren und bei niedriger Hitze...",
                demoFile()
        ));
        nachspeisen.add(nachspeise());
        return nachspeisen;
    }
}
