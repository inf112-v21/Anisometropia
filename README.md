![image info](assets/anisometropia.png)

# RoboRally by Anisometropia (prosjekt i INF112)

## Hvordan kjøre spillet:

JDK (Java Development Kit) må være installert, evt. JRE (Java Runtime Environment). Kan lastes ned her:

https://www.oracle.com/java/technologies/javase-jdk16-downloads.html

Last ned RoboRallyVersion4.jar fra:

https://github.com/inf112-v21/Anisometropia/releases/tag/v4 (ligger i "Assets"-listen)

#### _Windows_
Her kan man være heldig, og få til å kjøre spillet ved å bare dobbeltklikke på .jar-filen.

Fungerer ikke det, kan man åpne et terminal-vindu, navigere til mappen hvor .jar-filen er, og kjøre:
```
java -jar RoboRallyVersion4.jar
```
Fungerer ikke dette, må man legge til Java som PATH, følg bruksanvisning:

https://java.com/en/download/help/path.html

Naviger så til mappen hvor RoboRallyVersion4.jar ligger, og kjør:
```
java -jar RoboRallyVersion4.jar
```
#### _Linux_
Kjør kommandoer:
```
sudo apt update
sudo apt install default-jdk
```
Naviger til mappen hvor RoboRallyVersion4.jar ligger, og kjør:
```
java -jar RoboRallyVersion4.jar
```

#### _macOS_
Naviger til mappen hvor RoboRallyVersion4.jar ligger, og kjør:
```
java -XstartOnFirstThread -jar RoboRallyVersion4.jar
```

## Hvordan spille spillet:

### To play local:

1. Når man kjører spillet, velg "PLAY LOCAL".
2. Velg så antall spillere, gi de navn ved å trykke på navnboksen (står "name" i boks for første spilleren), og klikk "START".
3. Hvordan spillet spilles står forklart på høyresiden.
4. R restarter og setter spillerne tilbake til startposisjon.

### To play on net:

1. Kjør spillet og velg "PLAY ON NET", og trykk så på "HOST"-knappen
2. Velg hvilket kart og hvilket navn du ønsker.
3. Kjør enda et nytt spill, velg "PLAY ON NET".
4. Velg hvilket navn du ønsker.
5. Trykk så på "JOIN"-knappen.
6. Den som har trykket på "HOST", kan så starte spillet ved å velge "START".
7. Den som har valgt "JOIN" vil nå også ha muligheten til å starte et spill.


## Kjente feil (bugs)
Dersom en spiller har lite skade og så trykker R-(Respawn) vil spillet starte på nytt, men spilleren
vil få antall kort tilsvarende skaden som ble tatt før respawn. Spiller vil ikke få nye kort ved
respawn (Bug av lav prioritet).


## Nåværende begrensninger
På "PLAY ON NET" er det kun mulig å spille sammen med én annen spiller (totalt 2).