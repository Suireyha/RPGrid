# RPGrid
RPGrid is a simple grid based RPG game developed by [Marvin Kelly](https://github.com/Suireyha) in Java for the COMP2000 unit at Macquarie University.
To view additional doccumentation for this project including some planning and other various documents check out the doc folder. **The justifications required as per the assignment specifications are at the bottom of this document!!** The source code for the project can be found in the creatively named src folder.

This repository is built off of the [foundation repo](https://github.com/Suireyha/DMTWMC/tree/week05) *(specifically branch week05!)* that was made collectively by the Macquarie University staff, along with my team for the unit including:
- [Caleb Chew](https://github.com/ChewOnThis)
- [William Milne](https://github.com/Will-Milne-MQ)
- [Tom Hannelly](https://github.com/tomhann)
- [Daniel Hardman](https://github.com/stalebiscuit)
- [Mickel](https://github.com/M1CK3L)

You can see each of our contributions and development of the original repository [here](https://github.com/Suireyha/DMTWMC/tree/week05)

## Description
RPGrid is a simple GUI based RPG combat game written in Java!
You control the blue characters, and your objective is to defeat the red characters with your party. Due to time restrictions, enemies don't have combat logic, nor is there a propper turn queue- but you can left click on enemies and attack yourself with them if you wish to imerse yourself.
Pick up items, become stronger and slay your foe!
*Be careful not to have your ranger pick up a sword...*

## Instructions

### **Left Click**
Left clicking allows you to select characters, items or empty cells. By clicking on a **blue** character first, your next left click will be a context action performed by the selected character! Once you have a character selected, clicking on an item, cell or character will do the following:
- **Character**
    - If the clicked character is in range, your selected character will a ttack (you can make red characters attack you, and you can hit members of your party too!)
    - If it's not within range, a message will be displayed and your character will be deselected
- **Empty Cell**
    - If the clicked cell is within your character's move range (initiative + 3 tiles), the selected character will move to occupy that space. (Only functional for blue characters! Not red!)
    - If the clicked cell is outside of your character's movement range, and error message will be displayed and your character will be deselected
- **Item**
    - If your character is within range of the item, they pick it up and automatically equip it! You can see each character's items by right clicking them. Be careful to check what items do before picking them up, the Ranger is *really* bad with a sword...
    - If your character is outside of the item's reach, an error message will be displayed and your character will be deselected

### **Right Click**
Right clicking items or characters allows you to display their name, description, stats and inventory (where applicable)! Items, players and enemies each display their own information, so feel free to check things out yourself.

## To Do:
- Add start button
- Add win/loss screen
- Add more items/content
- Add randomly generated levels
- Add boss fight
- Add character creation
- Add ability to view, use and change items from inventory GUI 
- Add combat
    - Add a turn queue (ordered list of characters or make characters recursive so that it points to next character in turn)
    - Add enemy logic
    - ~~Add character death~~
- ~~Make inventory viewable from popup~~
- ~~Make items improve stats~~
- ~~Add attacking enemies~~
- ~~Add player movement~~
- ~~Create items (potions, weapons, armour)~~
- ~~Draw characters and indicate some things about them (role, and if they're a player or not)~~
- ~~Randomise player and enemy spawn locations~~
- ~~Create an interface for weapons, armour and potions respectively to support propper code design~~
- ~~Fix bug where hovering below bottom right corner of the grid gets index out of bounds error (non-fatal, just annoying)~~
- ~~Add players (Should have a class, race, name and attributes)~~
- ~~Add enemies~~
- ~~Add classes (Warrior, barbarian, ranger, mage, etc[?])~~
- ~~Add Items (Potions)~~
- ~~Add Weapons (Child of items)~~

## Project Requirements:
*If you're marking this submission, this area is for you!*
### Inheritence in the Program
- Character.java (Extends Actor)
    - During the initial stages of development, it was most convenient for Character to extend Actor in order to handle drawing Characters on the grid. However, this relationship is largely irellavent now since MapEntity.java (Character's interface) requires a paint(Graphics g) method to be implemented by children, much the way Item.java (Also implements MapEntity.java) simply has its own paint() method.
- Weapon.java (Extends Item.java)
    - Weapons rely on the common functionality provided by Items.java, while also needing to write more specific functinality for itself (Weapons have range, a TYPE, and their own special way of drawing things), and as such extends Items to inherit that functionality as well as the common variables like Item.name, Item.strength, Item.wisdom and so on. 
- Armour.java (Extends Item.java)
    - The same with Weapons.java, Armour is an item that is drawn on the map and can be picked up and equiped and so on. It gets that common functionality from Item. Armour doesn't do much specific at the moment, but I had planned flesh out its functinality with more time.
- Potion.java (Extends Item.java)
    - Potions are simple items- they don't have much functionality for themselves (again, mostly due to time restraints) but must be viewable, pick-up-able, etc.
- TextHeaders.java (Extends JLabel)
    - TextHeaders really just my class of custom JLabels, so it must inherit JLabel's functionality.
- ContentRow.java (Extends JPanel)
    - ContentRow.java is a custom 'div' class for organising the displays in the PopUp menus when you right click on something. It extends JPanel for all of the drawing, displaying and content justification functionality and so on.
- PopUp.java (Extends JFrame)
    - PopUp.java is created everytime you right click on an item/character to display it's stats. It extends JFrame for the window functionality.
*The following relationships existed in the project already*
- Cell.java (Extends Rectangle)
- Main.java (Extends JFrame)
- Canvas.java (Extends JPanel)

### Abstract Classes & Methods
- Item.java
    -Item.java an abstract class that includes a ton of general functionality for Weapons, Potions and Armour, while those child classes implement some things on their own respecively. Item.java is extended by Weapon, Potion and Armour

### Interfaces
- MapEntity.java
    - MapEntity.java is an interface that ensures anything drawn on the grid is held to a number of standards, like having a draw method. Everything that implements it also must have a displayWin() method to account for the scenario where it's right clicked, a getCurrentCell() method so that other functions/classes can get the cell the entity exists in, etc. MapEntity.java is implemented by Character and Item


### Generics
- PopUp.java 
    - PopUp.java is a generic class that can be made with any child of MapEntity.java (Character, Item, Item->Weapon, Item->Potion, Item->Armour), since all entities must have a PopUp to display when right clicked **but** they should have unique their own unique display functionality between them.
- setRandomSpawn()
    - The method setRandomSpawn() in Stage.java is an abstract method that takes any child of MapEntity.java as argument, and will place those entities on the grid randomly within different bounds depending on the type. For example, Items can be placed anywhere as long as a cell is empty, but Characters can't be placed between y=8 and y=12, and will be placed in either y=0 - y=8 or y=12 - y=20 depending on whether the character is a player or enemie 

## Storyboard
![storyboard](https://github.com/Suireyha/RPGrid/blob/main/doc/storyboards/draft-storyboard.jpg)
