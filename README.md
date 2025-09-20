# RPGrid
RPGrid is a simple grid based RPG game developed by [Marvin Kelly](https://github.com/Suireyha) in Java for the COMP2000 unit at Macquarie University.
To view additional doccumentation for this project, including the rational, some planning, and other various documents check out the doc folder- the source code for the project can be found in the creatively named src folder.

This repository is built off of the [foundation repo](https://github.com/Suireyha/DMTWMC/tree/week05) *(specifically branch week05!)* that was made collectively by the Macquarie University staff, along with my team for the unit including:
- [Caleb Chew](https://github.com/ChewOnThis)
- [William Milne](https://github.com/Will-Milne-MQ)
- [Tom Hannelly](https://github.com/tomhann)
- [Daniel Hardman](https://github.com/stalebiscuit)
- [Mickel](https://github.com/M1CK3L)

You can see each of our contributions and development of the original repository [here](https://github.com/Suireyha/DMTWMC/tree/week05)

## Instructions
*Coming soon!*

## To Do:
- Flesh out items, weapons, armour and potions
- Add player inventories, accessible from player display popUp
- Add player movement
- Add combat
    - Add a turn queue (ordered list of characters or make characters recursive so that it points to next character in turn)
    - Add character death
- Add start button
- Add win/loss screen
- Add more items/content
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

### General project requirements:
- Create substantial modifications to the repository to include unique functionality for the basics of a grid based RPG game
- ~~Include the sensible use of inheritence in the program~~
    - Character.java (Extends Actor)
    - Weapon.java (Extends Item.java)
    - Armour.java (Extends Item.java)
    - Potion.java (Extends Item.java)
    - TextHeaders.java (Extends JLabel)
    - Btn.java (Extends JButton)
    - PopUp.java (Extends JPanel)
    *The following relationships existed in the project already*
    - Cell.java (Extends Rectangle)
    - Main.java (Extends JFrame)
    - Canvas.java (Extends JPanel)

- ~~Include the sensible use of abstract methods and/or abstract classes in the program~~
    - Item.java is an abstract method that includes a ton of general functionality for Weapons, Potions and Armour, while those child classes implement some things on their own respecively.
- ~~Include the sensible use of interfaces in your program~~
    - MapEntity.java is an interface that ensures anything drawn on the grid is held to a number of standards, like having a draw method
    - RoundedBorder.java is a custom class that implements Border
- ~~Include the sensible use of generics in your program~~
    - PopUp.java is a generic class that can be made with any child of MapEntity.java (Character, Item, Item->Weapon, Item->Potion, Item->Armour), since all entities must have a PopUp to display when clicked **but** they should have unique functionality.
    - The method setRandomSpawn() in stage.java is an abstract method that takes any child of MapEntity.java as argument, and will place those entities on the grid randomly within different bounds depending on the type. For example, Items can be placed anywhere as long as a cell is empty, but Characters can't be placed between y=8 and y=12, and will be placed in either y=0 - y=8 or y=12 - y=20 depending on whether the character is a player or enemie