# RoboRally by Anosometropia (prosjekt i INF112)

Man starter spillet ved å kjøre Main.java (ligger i Anisometropia\src\main\java\launcher).
1. Hvordan spillet spilles står forklart på høyresiden når main kjøres.
2. ESCAPE vil avslutte spillet
3. R restarter og setter spillerene tilbake til startposisjon.



## Known bugs
Spillet blir tregere desto flere runder som spilles. Det kan ta flere sekunder å lukke spillet etter
å ha krysset fanen eller trykket ESC.

Hender at spillbrettet forsvinner fra skjermen og at bare den brune bakgrunnen vises.
Skjermen begynner da å blinke noen sekunder før den stabliseres igjen.

Dersom spiller1 og spiller2 står i samme rute (vil ikke være mulig senere i spillet når vi får 
lagt til collision med andre players) og spiller1 flytter seg, mens spiller2 står stille
vil ikke spiller2 vises før den har flytter seg igjen.

Selv om en spiller har falt ned i et hull, må man fortsatt trykke på "click to progress" for
å gå prosessere turen til spilleren.

Foreløpig er begge spillere like på spillbrettet, men det vises hvem som er "current player"
i tekst på skjermen (altså hvem man velger kort for og hvem som forflyttet seg for øyeblikket).

Om man har spilt spillet over lengre tid, så kan det føre til at spillet kræsjer og låser seg. 
Vi har gjort forbedringer på dette punktet like før innlevering og har ikke merket at denne buggen oppstår igjen,
men vi lar denne buggen stå forklart her likevel siden vi ikke har fått testet det godt nok i praksis 
før innlevering. 

Når man staret spillet så visualiseres det 10 damage tokens for hver spiller, dette er noe vi 
kommer til å endre når vi legger inn objekter på brettet som kan skade robotene og gi de 
damage tokens. For de skal strengt tatt starte med 0. De er her nå for å vise at damage tokens
skal bli en viktig funksjonalitet i spillet senere. 