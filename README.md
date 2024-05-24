# raider-navigator
An interactive kiosk to help the user navigate Richmond Hill High School’s building. Graphically displays the shortest route from any Room A to Room B in the school. This was my ICS3U6 final project.

**Instructions:**
1. Type a starting room and a destination room in the text fields. The room must be a room shown on the map in the main display, capitalization / whitespace does not matter. 
2. Press the “Get route” button to get a route from your starting room to your destination. Start and end points are marked with a filled circle. A floor change (meaning that you have to take a flight of stairs) is marked with an open circle. If either text field does not contain a valid start / destination room, the program will throw an exception in the console. Nothing happens in the GUI however, and the program is able to continue running as normal. 
3. The ETA (estimated time of arrival), along with the current floor currently being displayed, is displayed right above the map. 
4. At any time, you can re-input a new starting / destination room, press “Get route”, and get another route. You can do this as many times as you want.
5. At any time, you can press the “Toggle floor” button to toggle the map between the 1st and 2nd floor of the school. 
6. At any time, you can press the “Clear screen” button to clear the route and the ETA. 
7. If a route is currently being displayed on the screen, you can press the “Get text file” button to create a new text file containing the line by line directions to get from the start point to the destination. A “whooshing” sound effect is played when a new text file, containing line by line directions from the start to the destination, is successfully created. Text files are stored in the “lineByLines” folder. If a route is not currently being displayed on the screen, the program will throw an exception in the console. Nothing happens in the GUI however, and the program is able to continue running as normal. 
8. The program runs indefinitely until the user closes the window. 
