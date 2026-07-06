# Waschanlage

Dieses Java-Projekt verwaltet eine Warteschlange vor einer Autowaschanlage.

Es können maximal 10 Autos warten. Neue Autos werden hinten angestellt. Das erste Auto kann in die Waschanlage fahren und die restlichen Autos rücken automatisch nach. Autos können auch über ihr Kennzeichen aus der Schlange entfernt werden.

## Klassen

* `Auto` speichert Kennzeichen, Marke und Farbe.
* `Warteschlange` verwaltet die Autos in einer Liste.
* `WaschAutomatik` arbeitet die Liste automatisch ab.
* `Main` enthält das Konsolenmenü.

## Starten

```bash
mvn compile exec:java
```

Oder in IntelliJ die Datei `Main.java` starten.
