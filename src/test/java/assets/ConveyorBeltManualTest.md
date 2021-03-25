### **Manuelle tester for ConveyorBelt.java**
Før hver test kjøres programmet på nytt.
Testene gjøres med 2 eller flere spillere, da bevegelsen er rundebasert.

* Starter testene med å se at alle conveyorBelts vises visuelt i spillbrettet.
  

* Dersom man programerer roboten til å bevege seg over et ConveyorBelt skal dette
  påvirke ruten roboten dersom roboten under turen stopper på et conveyorBelt(Merk at en full runde er når 
  alle robotene har gjort alle sine 5 turer). Spiller skal alikevel krysse et conveyorbelt uten at dette påvirker ruten
  så lenge en av turene ikke stopp på ruten.
  

* Om spiller stopper på et ConveyorBelt i slutten av runden skal spiller bevege seg 
  med conveyorBelt.
  

* Bevegelsen skal ikke skje før alle spillerene er ferdig med runden sin.

* Gule ConveyorBelt
  Gule ConveyorBelt skal dytte spilleren 1 tile. \
  Gule ConveyorBelt med sving, skal roteres og flytte spiller 1 tile. \

* Blåe conveyorBelt fungerer litt anderledes. den skal dytte spilleren 1 tile for så å sjekke om denne tilen også
  er et blått conveyorBelt. Hvis dette stemmer så skal spilleren flyttes nok en gang.
  \
  Blåe ConveyorBelt skal dytte spilleren 1 tiles. \
  Blåe ConveyorBelt med sving, skal roteres og flytte spiller 1 tiles.\