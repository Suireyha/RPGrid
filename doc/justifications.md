# The Justifications
As per the assignment specifications, I must document where and my reasoning why I implemented each 
each heavy weighing components. In particular, where and why I included inheritence, generics, and interface. 
I will update this document to be a little better fleshed out soon :)

## Inheritence
Inheritence appears multiple times throughout this program. In this markdown file I will only include the times that **I** implemented it, not the cases where it existed beforehand.
### Appearances:
- Character.java (Extends Actor)
- Weapon.java (Extends Item.java)
- Armour.java (Extends Item.java)
- Potion.java (Extends Item.java)
- TextHeaders.java (Extends JLabel)
- Btn.java (Extends JButton)


## Interfaces
Interfaces appear multiple times throughout this program. In this markdown file I will only include the times that **I** implemented it, not the cases where it existed beforehand.
### Appearances:
- MapEntity.java (MapEntity is an interface for anything that is drawn on the grid, so Character and Item implement MapEntity since they're drawn on the map. MapEntity includes a couple of methods like draw(), since anything that is placed on the map must also be visible to avoid confusion, an enum that has the type of object it is and more)
- Main.java (Changed class Canvas to implement MouseListener so I could add an override mouseClicked method)
- Btn.java (Implements my own RoundedBorder file which I imported from another one of my projects [javaSketch on my github])


## Generics
Generics appear multiple times throughout this program. In this markdown file I will only include the times that **I** implemented it, not the cases where it existed beforehand.
### Appearances:
- PopUp.java (PopUp is a generic class where T extends MapEntity- since anything on the map should be able to be displayed after being right clicked with a popup, but obviously items and characters should have different functionality from there)
- Stage.java (setRandomSpawn() is a generic method that takes T extends MapEntity as parameters)
