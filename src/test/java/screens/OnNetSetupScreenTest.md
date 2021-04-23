### **Test Scenario:** _Check if possible to host and join a game_

**Test Steps**

1. run game, and click on "Play On Net"
2. click on the button that says "HOST"
3. run another instance of the game, and click on "Play on Net"
4. click on the button that says "JOIN"
5. back to the "HOST"-instance, click on "START"-button (a game should start here)
6. back to the "JOIN"-instance, the "START"-button should now have turned orange
7. click the "START"-button

**Expected Results**
The "JOIN"-instance should be able to start the game after joining a host-instance after the host has clicked "START".
The "JOIN"-instance should also receive the name of the host's player, and the selected map from the host.