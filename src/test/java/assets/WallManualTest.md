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
  bevege seg mot venstre. [wall] <-- [player]. \
  Skal spilleren inn i en tile med vegg på høyre side, kan ikke spilleren komme fra venstre og bevege seg mot
  høyre, alle andre retninger er lov.
  
  
* Da koden for walls var under utvikling ble det gjort mye testing i konsoll, ved at rette verdier for riktig tile
  ble skrevet ut i konsollen sammen med posisjonen spilleren sto i. Jeg har prøvd flere ulike metoder for å se om 
  det er en vegg forran spiller. Mye av dette skapte nullPointExceptions og da var det greit å ha en system.out.print 
  som ga en tileID og posisjon til hvor dette skjedde. Etter endel testing og feiling, ble koden ganske bra, og jeg har 
  trukket den konklusjonen at dette nå er unødvendig for denne ferdige koden.
  
    
* Har lagt inn en throw new IllegalStateException, for å fange opp eventuelle feil av id-er som
  kommer inn og hvilken posisjon på spillbrettet dette skjer i.