# **Manual tests for LocalSetupScreen.java**

### **Test Scenario**: _To check if button "START" works_

**Test Steps**

1. run game, and click on "Play Local"
2. click on the button that says "START"

**Expected Results**

The screen changes, and you see the gameboard

### **Test Scenario**: _To check if button "BACK" works_

**Test Steps**

1. run game, and click on "Play Local"
2. click on the button that says "BACK"

**Expected Results**

The screen changes, and you are now back to the main menu screen


### **Test Scenario:** _Check if possible to add players_

**Test Steps**

1. run game, and click on "Play Local"
2. click on "ADD"-button
3. click on "START"-button

**Expected Results**

There should now be 2 players in the playing area

### **Test Scenario:** _Check if possible to remove players"_

**Test Steps**

1. run game, and click on "Play Local"
2. click on "ADD"-button
3. click on "REMOVE"-button   
4. click on "START"-button

**Expected Results**

There should now only be 1 player in the playing area.

### **Test Scenario:** _Check if possible to give player a name"_

**Test Steps**

1. run game, and click on "Play Local"
2. click on the box that says "name"
3. press backspace key until "name" is erased, and box is blank
4. type "Santa", and hit enter key
5. click on "START"-button

**Expected Results**

The player should now be named "Santa" (visible on player list on the right).
