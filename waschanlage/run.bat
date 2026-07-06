@echo off
echo Starte Waschanlage...
if not exist out mkdir out
javac -encoding UTF-8 -d out src\main\java\waschanlage\*.java
if errorlevel 1 (
    echo Kompilieren fehlgeschlagen.
    pause
    exit /b 1
)
java -cp out waschanlage.Main
pause
