package waschanlage;

public class Auto {
    private final String kennzeichen;
    private final String marke;
    private final String farbe;

    public Auto(String kennzeichen, String marke, String farbe) {
        this.kennzeichen = kennzeichen;
        this.marke = marke;
        this.farbe = farbe;
    }

    public String getKennzeichen() {
        return kennzeichen;
    }

    public String getMarke() {
        return marke;
    }

    public String getFarbe() {
        return farbe;
    }

    public boolean hatKennzeichen(String gesuchtesKennzeichen) {
        return kennzeichen.equalsIgnoreCase(gesuchtesKennzeichen);
    }

    @Override
    public String toString() {
        return "Kennzeichen: " + kennzeichen + ", Marke: " + marke + ", Farbe: " + farbe;
    }
}
