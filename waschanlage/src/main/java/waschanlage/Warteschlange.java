package waschanlage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Warteschlange {
    private static final int MAXIMALE_ANZAHL_AUTOS = 10;
    private final List<Auto> autos;

    public Warteschlange() {
        autos = new ArrayList<>();
    }

    public synchronized boolean autoAnstellen(Auto auto) {
        if (istVoll()) {
            return false;
        }

        autos.add(auto);
        return true;
    }

    public synchronized Optional<Auto> erstesAutoInWaschanlageFahrenLassen() {
        if (autos.isEmpty()) {
            return Optional.empty();
        }

        // Durch remove(0) rücken bei der ArrayList alle anderen Autos automatisch auf.
        return Optional.of(autos.remove(0));
    }

    public synchronized Optional<Auto> autoVerlaesstSchlange(String kennzeichen) {
        for (int i = 0; i < autos.size(); i++) {
            Auto auto = autos.get(i);

            if (auto.hatKennzeichen(kennzeichen)) {
                autos.remove(i);
                return Optional.of(auto);
            }
        }

        return Optional.empty();
    }

    public synchronized List<Auto> getAutosKopie() {
        return new ArrayList<>(autos);
    }

    public synchronized int getAnzahlAutos() {
        return autos.size();
    }

    public synchronized boolean istLeer() {
        return autos.isEmpty();
    }

    public synchronized boolean istVoll() {
        return autos.size() >= MAXIMALE_ANZAHL_AUTOS;
    }

    public int getMaximaleAnzahlAutos() {
        return MAXIMALE_ANZAHL_AUTOS;
    }
}
