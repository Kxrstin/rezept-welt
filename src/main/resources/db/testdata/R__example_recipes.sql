INSERT INTO rezept_form (
    kategorie,
    name,
    zutaten,
    zubereitung,
    bild
)
VALUES
    (
        'Hauptspeise',
        'Saftiges Hähnchen-Geschnetzeltes in Rahmsauce',
        '500g Hähnchenbrust, 200ml Sahne, 1 Zwiebel, Champignons, Paprikapulver, Salz, Pfeffer',
        'Hähnchenbrust in Streifen schneiden und scharf anbraten. Zwiebeln und Champignons hinzufügen und kurz mitbraten. Mit Sahne ablöschen, würzen und einige Minuten cremig einkochen lassen. Dazu passen Reis oder Nudeln.',
        NULL
    ),
    (
        'Vorspeise',
        'Rote-Bete-Carpaccio mit Feta',
        '2 vorgekochte Rote Bete, 100g Feta, Walnüsse, Rucola, Olivenöl, Balsamico',
        'Rote Bete in dünne Scheiben schneiden und auf Tellern auslegen. Mit zerbröseltem Feta, Walnüssen und Rucola bestreuen. Mit Olivenöl und Balsamico beträufeln.',
        NULL
    ),
    (
        'Nachspeise',
        'Zimt-Milchreis mit Vanille',
        '200g Rundkornreis, 1 Liter Milch, 1 Vanilleschote, Zucker, Zimt',
        'Milch mit Vanille aufkochen, Reis einrühren und bei niedriger Hitze ca. 30 Minuten sanft köcheln lassen. Mit Zucker abschmecken und mit Zimt servieren.',
        NULL
    ),
    (
        'Hauptspeise',
        'Klassisches Chili con Carne',
        '500g Rinderhackfleisch, 1 Dose Kidneybohnen, 1 Dose Mais, 1 Dose Tomaten, Zwiebel, Knoblauch, Chili, Kreuzkümmel',
        'Hackfleisch krümelig anbraten, Zwiebel und Knoblauch hinzufügen. Tomaten und Gewürze einrühren und ca. 30 Minuten köcheln lassen. Bohnen und Mais unterheben und weitere 10 Minuten ziehen lassen.',
        NULL
    ),
    (
        'Vorspeise',
        'Caesar-Salat',
        'Römersalat, 50g Parmesan, Croutons, 1 Ei, Senf, Zitronensaft, Olivenöl, Knoblauch',
        'Salat waschen und zerkleinern. Aus Ei, Senf, Zitronensaft, Knoblauch und Öl ein cremiges Dressing rühren. Mit Croutons und frisch gehobeltem Parmesan servieren.',
        NULL
    ),
    (
        'Nachspeise',
        'Erfrischende Zitronen-Joghurt-Creme',
        '500g griechischer Joghurt, 2 Zitronen, 80g Zucker, Vanillezucker',
        'Joghurt mit Zucker, Vanille und Zitronensaft glatt rühren. Mindestens 1 Stunde kalt stellen und mit Zitronenabrieb servieren.',
        NULL
    ),
    (
        'Hauptspeise',
        'Mediterranes Ofengemüse',
        'Zucchini, Aubergine, Paprika, Kirschtomaten, Olivenöl, Rosmarin, Thymian',
        'Gemüse grob schneiden, mit Olivenöl und Kräutern vermengen und auf einem Blech verteilen. Bei 200 Grad ca. 35 Minuten im Ofen rösten.',
        NULL
    ),
    (
        'Vorspeise',
        'Frischer Linsensalat mit Petersilie',
        '200g grüne Linsen, rote Zwiebel, Petersilie, Zitronensaft, Olivenöl, Salz',
        'Linsen weich kochen und abkühlen lassen. Mit fein gehackter Zwiebel und Petersilie vermengen. Mit Zitronensaft, Olivenöl und Salz abschmecken.',
        NULL
    ),
    (
        'Nachspeise',
        'Schoko-Pfannkuchen mit Bananen',
        '2 Bananen, 2 Eier, 100g Mehl, Milch, Schokotropfen',
        'Bananen zerdrücken und mit Eiern, Mehl und Milch zu einem Teig verrühren. Schokotropfen unterheben und in einer Pfanne goldbraun ausbacken.',
        NULL
    ),
    (
        'Hauptspeise',
        'Gebratene Gnocchi mit Pesto',
        '500g Gnocchi, Basilikum-Pesto, Cherrytomaten, Parmesan',
        'Gnocchi in einer Pfanne knusprig anbraten. Tomaten kurz mitbraten und mit Pesto vermengen. Mit frisch geriebenem Parmesan servieren.',
        NULL
    )
ON CONFLICT (name)
DO UPDATE SET
    kategorie = EXCLUDED.kategorie,
    zutaten = EXCLUDED.zutaten,
    zubereitung = EXCLUDED.zubereitung,
    bild = EXCLUDED.bild;
