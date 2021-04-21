![image info](assets/anisometropia.png)

# RoboRally by Anisometropia (prosjekt i INF112)

## Hvordan kjøre spillet:

JDK (Java Development Kit) må være installert, evt. JRE (Java Runtime Environment). Kan lastes ned her:

https://www.oracle.com/java/technologies/javase-jdk16-downloads.html

Last ned RoboRallyVersion3.jar fra:

https://github.com/inf112-v21/Anisometropia/releases/tag/v3.1 (ligger i "Assets"-listen)

#### _Windows_
Her kan man være heldig, og få til å kjøre spillet ved å bare dobbeltklikke på .jar-filen.

Fungerer ikke det, kan man åpne et terminal-vindu, navigere til mappen hvor .jar-filen er, og kjøre:
```
java -jar RoboRallyVersion3.jar
```
Fungerer ikke dette, må man legge til Java som PATH, følg bruksanvisning:

https://java.com/en/download/help/path.html

Naviger så til mappen hvor RoboRallyVersion3.jar ligger, og kjør:
```
java -jar RoboRallyVersion3.jar
```
#### _Linux_
Kjør kommandoer:
```
sudo apt update
sudo apt install default-jdk
```
Naviger til mappen hvor RoboRallyVersion3.jar ligger, og kjør:
```
java -jar RoboRallyVersion3.jar
```

#### _macOS_
Naviger til mappen hvor RoboRallyVersion3.jar ligger, og kjør:
```
java -XstartOnFirstThread -jar RoboRallyVersion3.jar
```

## Hvordan spille spillet:
1. Når man kjører spillet, velg "PLAY LOCAL" ("PLAY ON NET" er enda ikke fullstendig implementert).

2. Velg så antall spillere, gi de navn ved å trykke på navnboksen (står "name" i boks for første spilleren), og klikk "START".

3. Hvordan spillet spilles står forklart på høyresiden.

4. R restarter og setter spillerne tilbake til startposisjon.


## Kjente feil (bugs)
Selv om en spiller har falt ned i et hull, må man fortsatt trykke på "click to progress" for
å prosessere turen til spilleren.

Dersom en spiller har lite skade og så trykker R-(Respawn) vil spillet starte på nytt, men spilleren
vil få antall kort tilsvarende skaden som ble tatt før respawn. Spiller vil ikke få nye kort ved
respawn (Bug av lav prioritet).
