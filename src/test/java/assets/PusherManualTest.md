### **Manuelle tester for Pusher.java**
Før hver test kjøres programmet på nytt.
Testene gjøres med 2 eller flere spillere, da bevegelsen er rundebasert.

* PusherTest.java sørger for at dersom en spiller står på en pusher vil den bolske metoden isTherePusherOnThisPosition
returnere TRUE. Dette gjør den for alle de fire ulike pushers-ene
  
* Når en spiller beveger seg gjennom en tile med pusher på - så skal spiller dyttes vekk fra pusher. 
  Denne fysikken er ikke turn eller round based, men skal skje for hvert skritt en spiller går.
  Dersom Det er en Pusher i retning jeg går, og den ikke står med vegg-siden mot meg skal dette påvirke ruten jeg går
  med å dytte meg vekk fra denne tilen.
  
* Pusher skal også fungere som en vegg - Da metoden er kalt i move() i player, vil man aldri kunne klare å gå fra 
  pusher siden og inn, for å teste outOfWall, da den alltid vil dytte deg ut (selv om dette er implementert) \
  Men IntoWall can vi derimot teste, og vi ser at spiller ikke vil kunne gå gjennom en vegg. \
  Testingen for dette skjer på samme måte som beskrevet i WallManualTest.md

* Har lagt inn en throw new IllegalStateException, for å fange opp eventuelle feil av id-er som
  kommer inn og hvilken posisjon på spillbrettet dette skjer i.