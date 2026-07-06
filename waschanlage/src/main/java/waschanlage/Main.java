package waschanlage;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Warteschlange warteschlange = new Warteschlange();

    private static WaschAutomatik waschAutomatik;
    private static Thread automatikThread;

    public static void main(String[] args) {
        int auswahl;

        do {
            menueAnzeigen();
            auswahl = zahlEinlesen("Auswahl: ");

            switch (auswahl) {
                case 1:
                    autoHintenAnstellen();
                    break;
                case 2:
                    erstesAutoWaschen();
                    break;
                case 3:
                    autoAusSchlangeEntfernen();
                    break;
                case 4:
                    warteschlangeAnzeigen();
                    break;
                case 5:
                    anzahlAutosAnzeigen();
                    break;
                case 6:
                    automatikStarten();
                    break;
                case 7:
                    automatikStoppen();
                    break;
                case 0:
                    automatikStoppenOhneMeldung();
                    System.out.println("Programm wurde beendet.");
                    break;
                default:
                    System.out.println("Ungültige Auswahl.");
                    break;
            }
        } while (auswahl != 0);
    }

    private static void menueAnzeigen() {
        System.out.println();
        System.out.println("===== Waschanlage =====");
        System.out.println("Autos in der Warteschlange: "
                + warteschlange.getAnzahlAutos()
                + "/"
                + warteschlange.getMaximaleAnzahlAutos());
        System.out.println("1. Auto hinten anstellen");
        System.out.println("2. Erstes Auto in die Waschanlage fahren lassen");
        System.out.println("3. Auto verlässt die Warteschlange");
        System.out.println("4. Warteschlange anzeigen");
        System.out.println("5. Anzahl der Autos anzeigen");
        System.out.println("6. Automatische Abarbeitung starten");
        System.out.println("7. Automatische Abarbeitung stoppen");
        System.out.println("0. Beenden");
    }

    private static void autoHintenAnstellen() {
        if (warteschlange.istVoll()) {
            System.out.println("Die Warteschlange ist voll. Es können maximal 10 Autos warten.");
            return;
        }

        String kennzeichen = textEinlesen("Kennzeichen: ");
        String marke = textEinlesen("Marke: ");
        String farbe = textEinlesen("Farbe: ");

        Auto auto = new Auto(kennzeichen, marke, farbe);
        boolean erfolgreich = warteschlange.autoAnstellen(auto);

        if (erfolgreich) {
            System.out.println("Das Auto wurde hinten angestellt.");
        } else {
            System.out.println("Das Auto konnte nicht angestellt werden, weil die Schlange voll ist.");
        }
    }

    private static void erstesAutoWaschen() {
        Optional<Auto> auto = warteschlange.erstesAutoInWaschanlageFahrenLassen();

        if (auto.isPresent()) {
            System.out.println("Dieses Auto fährt jetzt in die Waschanlage:");
            System.out.println(auto.get());
        } else {
            System.out.println("Die Warteschlange ist leer.");
        }
    }

    private static void autoAusSchlangeEntfernen() {
        if (warteschlange.istLeer()) {
            System.out.println("Die Warteschlange ist leer. Es kann kein Auto entfernt werden.");
            return;
        }

        String kennzeichen = textEinlesen("Kennzeichen des Autos: ");
        Optional<Auto> entferntesAuto = warteschlange.autoVerlaesstSchlange(kennzeichen);

        if (entferntesAuto.isPresent()) {
            System.out.println("Dieses Auto hat die Schlange verlassen:");
            System.out.println(entferntesAuto.get());
        } else {
            System.out.println("Es wurde kein Auto mit diesem Kennzeichen gefunden.");
        }
    }

    private static void warteschlangeAnzeigen() {
        List<Auto> autos = warteschlange.getAutosKopie();

        if (autos.isEmpty()) {
            System.out.println("Die Warteschlange ist leer.");
            return;
        }

        System.out.println();
        System.out.println("Aktuelle Warteschlange:");

        for (int i = 0; i < autos.size(); i++) {
            System.out.println((i + 1) + ". " + autos.get(i));
        }
    }

    private static void anzahlAutosAnzeigen() {
        System.out.println("Aktuell warten "
                + warteschlange.getAnzahlAutos()
                + " von maximal "
                + warteschlange.getMaximaleAnzahlAutos()
                + " Autos.");
    }

    private static void automatikStarten() {
        if (automatikThread != null && automatikThread.isAlive()) {
            System.out.println("Die automatische Abarbeitung läuft bereits.");
            return;
        }

        waschAutomatik = new WaschAutomatik(warteschlange, 5000);
        automatikThread = new Thread(waschAutomatik);
        automatikThread.start();

        System.out.println("Automatische Abarbeitung gestartet.");
        System.out.println("Alle 5 Sekunden fährt das erste Auto automatisch in die Waschanlage.");
    }

    private static void automatikStoppen() {
        if (automatikThread == null || !automatikThread.isAlive()) {
            System.out.println("Die automatische Abarbeitung läuft aktuell nicht.");
            return;
        }

        waschAutomatik.stoppen();
        automatikThread.interrupt();

        System.out.println("Automatische Abarbeitung wurde gestoppt.");
    }

    private static void automatikStoppenOhneMeldung() {
        if (automatikThread != null && automatikThread.isAlive()) {
            waschAutomatik.stoppen();
            automatikThread.interrupt();
        }
    }

    private static String textEinlesen(String ausgabe) {
        String eingabe;

        do {
            System.out.print(ausgabe);
            eingabe = scanner.nextLine().trim();

            if (eingabe.isEmpty()) {
                System.out.println("Die Eingabe darf nicht leer sein.");
            }
        } while (eingabe.isEmpty());

        return eingabe;
    }

    private static int zahlEinlesen(String ausgabe) {
        while (true) {
            System.out.print(ausgabe);
            String eingabe = scanner.nextLine().trim();

            try {
                return Integer.parseInt(eingabe);
            } catch (NumberFormatException e) {
                System.out.println("Bitte eine gültige Zahl eingeben.");
            }
        }
    }
}
