#!/bin/sh
echo "Starte Waschanlage..."
mkdir -p out
javac -encoding UTF-8 -d out src/main/java/waschanlage/*.java
if [ $? -ne 0 ]; then
  echo "Kompilieren fehlgeschlagen."
  exit 1
fi
java -cp out waschanlage.Main
