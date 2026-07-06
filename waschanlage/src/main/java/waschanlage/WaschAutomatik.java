package waschanlage;

import java.util.Optional;

public class WaschAutomatik implements Runnable {
    private final Warteschlange warteschlange;
    private final long pauseInMillisekunden;
    private volatile boolean aktiv;

    public WaschAutomatik(Warteschlange warteschlange, long pauseInMillisekunden) {
        this.warteschlange = warteschlange;
        this.pauseInMillisekunden = pauseInMillisekunden;
        this.aktiv = true;
    }

    public void stoppen() {
        aktiv = false;
    }

    @Override
    public void run() {
        while (aktiv) {
            try {
                Thread.sleep(pauseInMillisekunden);

                Optional<Auto> auto = warteschlange.erstesAutoInWaschanlageFahrenLassen();

                if (auto.isPresent()) {
                    System.out.println();
                    System.out.println("[Automatik] Dieses Auto wurde gewaschen:");
                    System.out.println("[Automatik] " + auto.get());
                    System.out.print("Auswahl: ");
                }
            } catch (InterruptedException e) {
                aktiv = false;
                Thread.currentThread().interrupt();
            }
        }
    }
}
