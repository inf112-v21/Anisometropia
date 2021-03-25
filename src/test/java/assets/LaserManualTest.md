### **Manuelle tester for Laser.java**
Før hver test kjøres programmet på nytt.
Testene gjøres med 2 eller flere spillere, da bevegelsen er rundebasert.

* Starter testene med å se at alle Laser-textures vises visuelt i spillbrettet.


* Når en spillers movement stopper på laser, skal spiller ta damage. Dette vises visuelt
  ved at antall damagetokens korresponderer med antall laser som skyter på spilleren. \
  Feks at dersom en spiller lander på doubleLaserVertical, så skal dette gjøre at spilleren
  tar 2 i skade.
  

* Har lagt inn en throw new IllegalStateException, for å fange opp eventuelle feil av id-er som
  kommer inn.