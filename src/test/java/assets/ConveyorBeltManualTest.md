### **Manuelle tester for ConveyorBelt.java**
Før hver test kjøres programmet på nytt.
Testene gjøres med 2 eller flere spillere, da bevegelsen er rundebasert.

   * _Starter testene med å se at alle conveyorBelts vises i spillbrettet._
   
   * _Prøver så å flytte spiller med piltastene/wasd og ser om dette gir resultater.
     Dette skal ikke gi noe utslag for spillet, da piltastene ikke er rundebasert.
     \
     Resultatet gir at conveyorBelt ikke vil bevege spiller før alle kortene til spillerene 
     er spilt og runden registreres som over._
     
   * _Dersom man programerer roboten til å bevege seg over et ConveyorBelt skal ikke dette
     påvirke ruten roboten har valgt. Tester dette ved å la spilleren gå i en rett linje 
     som krysser et conveyorBelt og ser til at ruten ikke påvirkes._ 
     
   * _Om spiller stopper på et ConveyorBelt i slutten av runden skal spiller bevege seg 
     med conveyorBelt._
     
   * _Bevegelsen skal ikke skje før alle spillerene er ferdig med runden sin._
   
   * _Gule ConveyorBelt skal dytte spilleren 1 tile. \
     Gule ConveyorBelt med sving, skal roteres og flytte spiller 1 tile. \
     Blåe ConveyorBelt skal dytte spilleren 2 tiles. \
     Blåe ConveyorBelt med sving, skal roteres og flytte spiller 2 tiles._