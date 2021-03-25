![image info](assets/anisometropia.png)

# RoboRally by Anisometropia (prosjekt i INF112)

## Hvordan å kjøre spillet:

JRE (Java Runtime Environment) må være installert.

Last ned RoboRallyVersion3.jar fra (ligger i "Assets"-listen):

https://github.com/inf112-v21/Anisometropia/releases/tag/v3.1

#### _Windows_
Her kan man være heldig, og få til å kjøre spillet ved å bare dobbeltklikke på .jar-filen.

Fungerer ikke det, kan man åpne et terminal-vindu, naviger til mappen hvor .jar-filen er, og kjøre:
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
sudo apt install default-jre
```
Naviger til mappen hvor RoboRallyVersion3.jar ligger, og kjør:
```
java -jar RoboRallyVersion3.jar
```

#### _OS X_
Naviger til mappen hvor RoboRallyVersion3.jar ligger, og kjør:
```
java -XstartOnFirstThread -jar RoboRallyVersion3.jar
```

## Hvordan å spille spillet:
1. Når man kjører spillet, velg "PLAY LOCAL" ("PLAY ON NET" er enda ikke fullstendig implementert).

2. Velg så antall spillere, og klikk "START".

3. Hvordan spillet spilles står forklart på høyresiden.

4. R restarter og setter spillerene tilbake til startposisjon.


## Kjente feil (bugs)
Dersom spiller1 og spiller2 står i samme rute (vil ikke være mulig senere i spillet når vi får 
lagt til collision med andre players) og spiller1 flytter seg, mens spiller2 står stille
vil ikke spiller2 vises før den har flytter seg igjen.

Selv om en spiller har falt ned i et hull, må man fortsatt trykke på "click to progress" for
å prosessere turen til spilleren.

Dersom den siste spilleren i rekkefølgen har utført 'power down' en runde medfører dette at andre spillere gjentar samme trekk neste runde uten å få muligheten til å velge nye kort. Spilleren som var i 'power down' foregående runde får derimot velge nye kort som forventet.
