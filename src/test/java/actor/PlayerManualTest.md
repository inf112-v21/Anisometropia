### **Manuelle tester for Player.java**
Før hver test kjøres programmet på nytt.

\
_Test at kort som skal låses lagres korrekt:_
- Naviger spilleren til en laser og ta minst fem skade.
- Noter hvilke kort som ble brukt runden minst fem total skade ble oppnådd.
- Bekreft visuelt ved utførelsen neste runde at de låste kortene utføres som forventet, i riktig rekkefølge.

_Test av collisions mellom spillere:_
- Beveg spilleren(1) innn i en rute hvor det står en annen spiller(2).
- Spilleren(2) som står i ruten fra før skal dyttes i samme retning spiller(1) går.
- Dersom noe blokerer for at spiller(2) ikke kan bevege seg hit. skal ikke spiller(1) kunne dytte lenger.

_Test av player shooting player:_
- I slutten av hver runde vil hver spiller avfyre en laser i direction: player is facing
- Se til at alle spillerene på brette avfyrer en laser. (Dette vises visuelt i brettet)
- Roter 1 av spillerene slik at den står vendt mot en annen spiller.
- I slutten av runden vil da laseren treffe en spiller. spilleren som blir truffet skal da miste
like mange liv som lasere som treffer.