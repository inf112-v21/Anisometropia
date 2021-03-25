### **Manuelle tester for Wall.java**
Før hver test kjøres programmet på nytt.
Testene gjøres med 2 eller flere spillere, da bevegelsen er rundebasert.

* Når spiller står på en tile med wall-texturen, skal denne vises visuelt rundt player.

* For hvert trekk spilleren gjør, skal vi først sjekke om det befinner seg en vegg i denne
  posisjonen, dersom det ikke finnes en vegg her kan spiller gå hit.\
  Dersom det er en vegg her, må vi finne ut hvilken side av tilen veggen er og om spilleren
  vil kollidere med veggen i det den går inn i tilen.\
  Dersom det oppstår en kollisjon, ønsker jeg at canMove() skal returnere false, og dermed
  stoppe spilleren fra å gå til gitt lokasjon.

* Jeg tester at dette fungerer for alle ulike wall-tiles.
  Et eksempel vil være at dersom det er en vegg på høyre siden så kan ikke en spiller som står i denne tilen
  bevege seg mot høyre.\
  Skal spilleren inn i en tile med vegg på høyre side, kan ikke spilleren komme fra venstre og bevege seg mot
  høyre, alle andre retninger er lov.

* Har lagt inn en throw new IllegalStateException, for å fange opp eventuelle feil av id-er som
  kommer inn og hvilken posisjon på spillbrettet dette skjer i.