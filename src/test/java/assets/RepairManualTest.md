### **Manuelle tester for Repair.java**
Før hver test kjøres programmet på nytt.
Testene gjøres med 2 eller flere spillere, da bevegelsen er rundebasert.

* RepairTest.java - Viser at spilleren registrerer at den står på en wrench og doubleWrench tile med isThereRepairStationOnThisPosition() \
  Ved å gi spiller skade, kan vi også teste om den reparerer skaden når den står i en tile hvor det er en repairStation.

* 
* Har lagt inn en throw new IllegalStateException, for å fange opp eventuelle feil av id-er som
  kommer inn og hvilken posisjon på spillbrettet dette skjer i.