![image info](assets/anisometropia.png)

# RoboRally by Anisometropia (prosjekt i INF112)

Man starter spillet ved å kjøre Main.java (ligger i Anisometropia\src\main\java\launcher).
1. Hvordan spillet spilles står forklart på høyresiden når main kjøres.
2. ESCAPE vil avslutte spillet
3. R restarter og setter spillerene tilbake til startposisjon.



## Known bugs
Spillet blir tregere desto flere runder som spilles. Det kan ta flere sekunder å lukke spillet etter
å ha krysset fanen eller trykket ESC.

Dersom spiller1 og spiller2 står i samme rute (vil ikke være mulig senere i spillet når vi får 
lagt til collision med andre players) og spiller1 flytter seg, mens spiller2 står stille
vil ikke spiller2 vises før den har flytter seg igjen.

Selv om en spiller har falt ned i et hull, må man fortsatt trykke på "click to progress" for
å prosessere turen til spilleren.

Dersom den siste spilleren i rekkefølgen har utført 'power down' en runde medfører dette at andre spillere gjentar samme trekk neste runde uten å få muligheten til å velge nye kort. Spilleren som var i 'power down' foregående runde får derimot velge nye kort som forventet.
