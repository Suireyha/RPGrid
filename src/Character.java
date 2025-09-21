import java.awt.Color;
import java.awt.Polygon;
import java.util.ArrayList;

public class Character extends Actor implements MapEntity{ //Child of Actor since we'll draw these
    String name; //Characters name
    String description;
    String raceAsText;
    String roleAsText;
    RoleType role; //Characters role/class
    RaceType race; //Characters race
    ArrayList<Item> inventory = new ArrayList<>();
    Weapon equipedWeapon = null;
    Armour equipedArmour = null;
    int range = 2;
    boolean player = false; //If this character is a user controlled player, make this true
    Cell loc;


    Color nameTextCol = Color.WHITE; //White by default
    mapEntityType entity;

    PopUp<Character> popup;

    //Attributes
    public int strength = 0; //Used for non-magical combat rolls
    public int wisdom = 0; //Used for magical combat rolls
    public int constitution = 0; //Used to determine HP
    public int initiative = 3; //Used to determine turns (All have base 3 init)

    public double maxHealth = 0;
    public double health = 0;

    //Keeping these here for now, might make a package later
    public enum RaceType{ 
        HUMAN,
        ELF,
        ORC,
        DWARF
    }

    public enum RoleType{
        BARBARIAN,
        FIGHTER,
        MAGE,
        RANGER
    }

    private void setStats(){

        Weapon fists = new Weapon("Bare Hands", "The fists of " + this.name, "+0 ALL", 0, 0, 0, 0, Weapon.Type.SWORD);
        Armour rags = new Armour("Rags", "The nearly naked form of " + this.name, "+0 ALL", 0, 0, 0, 0);

        switch(role){
            case BARBARIAN: //Slow, but hard to take out and super heavy hitting
                strength += 7;
                wisdom += 1;
                constitution += 6;
                initiative += 2;
                roleAsText = "Barbarian";
                Weapon axe = new Weapon("Basic Axe", "A crude axe, made from sticks and scrap.", "+1 STR", 1, 0, 0, 0, Weapon.Type.AXE);
                inventory.add(axe);
                inventory.add(rags);
                equipedWeapon = axe; 
                equipedArmour = rags;
                break;
            case FIGHTER: //Well rounded, and hard to take out
                strength += 4;
                wisdom += 2;
                constitution += 6;
                initiative += 4;
                roleAsText = "Fighter";

                Weapon sword = new Weapon("Basic Sword", "A sharpened piece of scrap.", "+1 STR", 1, 0, 0, 0, Weapon.Type.SWORD);
                inventory.add(sword);
                inventory.add(rags);
                equipedWeapon = sword; 
                equipedArmour = rags;


                break;
            case MAGE: //High DMG and quick, but glass cannon types
                strength += 1;
                wisdom += 7;
                constitution += 2;
                initiative += 5;
                roleAsText = "Mage";

                Weapon staff = new Weapon("Basic Staff", "A fancy looking stick!", "+1 WIS", 0, 1, 0, 0, Weapon.Type.STAFF);
                inventory.add(staff);
                inventory.add(rags);
                equipedWeapon = staff; 
                equipedArmour = rags;


                break;
            case RANGER: //Super quick ranged attacks but can't take many hits
                strength += 3;
                wisdom += 2;
                constitution += 4;
                initiative += 7;
                roleAsText = "Ranger";

                Weapon bow = new Weapon("Basic Bow", "It's honestly more of a slingshot...", "+0 ALL", 0, 0, 0, 0, Weapon.Type.BOW);
                inventory.add(bow);
                inventory.add(rags);
                equipedWeapon = bow; 
                equipedArmour = rags;


                break;
            default:
                inventory.add(fists);
                inventory.add(rags);
                equipedWeapon = fists;
                equipedArmour = rags;
                break;
        }

        //**RULES** each race's attributes must sum to 5;
        switch(race){
            case HUMAN: //Good all rounder, a fine pick for all classes
                strength += 1;
                wisdom += 1;
                constitution += 1;
                initiative += 2;
                raceAsText = "Human";
                //Inventory.add();
                break;
            case ELF: //Weak and light but better spell casters and slightly quicker
                strength += 0;
                wisdom += 3;
                constitution += 0;
                initiative += 2;
                raceAsText = "Elf";
                //Inventory.add();
                break;
            case ORC: //Super strong, but dumb and slow. Best suited to fighters or barbarians
                strength += 3;
                wisdom += 0;
                constitution += 2;
                initiative += 0;
                raceAsText = "Orc";
                //Inventory.add();
                break;
            case DWARF: //Meant to be a decent pick for either rangers or mages who want more health
                strength += 0;
                wisdom += 2;
                constitution += 2;
                initiative += 1;
                raceAsText = "Dwarf";
                //Inventory.add();
                break;
            default:
                //Error **IMPLEMENT LATER**
                break;
        }

        //Set HP
        maxHealth = constitution*10; //maxHealth is the maximum threshold for a character's health. They can't heal above it, but this shouldn't be modified in combat
        health = maxHealth; //Health is the characters active health

        if(player){
            drawCol = new Color(5, 8, 90); //Players are drawn in blue on the grid
            nameTextCol = new Color(137, 207, 240); //Baby blue for name display if player (90, 185, 230)
            description = name + " the " + raceAsText + " is a " + roleAsText + " on a quest to eviscerate evil doers!";
        }
        else{
            drawCol = new Color(90, 0, 0); //Enemies are drawn in red on the grid
            nameTextCol = new Color(230, 62, 62); //Red text when displaying enemie names
            description = name + " the " + raceAsText + " has sworn to use their abilities as a " + roleAsText + " to DESTROY you!";
        }
    }



    Character(String name, RoleType role, RaceType race, boolean isPlayer){ //Character constructor from preset (made by programmer in Main, not user made)
        //Set up the attributes based on the constructor arguments
        this.name = name;
        this.role = role;
        this.race = race;
        this.player = isPlayer;

        if(isPlayer){
            this.entity = mapEntityType.PLAYER;
        }
        else{
            this.entity = mapEntityType.ENEMIE;
        }
        setStats();
    }
    Character(){
        //**IMPLEMENT LATER** This should be a character creator that lets users make their own characters. Low priority though
    }

    public void setCellContentsToThisInstance(){ //The best variable name of all time
        loc.contentsChar = this;
    }

    public void displayWin(){ //Click display stuff
        popup = new PopUp<Character>(this);
    }

    public mapEntityType getEntityType(){
        if(player){
            return MapEntity.mapEntityType.PLAYER;
        }
        return MapEntity.mapEntityType.ENEMIE;
    }

    public String getName(){
        return name;
    }

    public int[] getStats(){
        int[] stats = {strength, wisdom, constitution, initiative, (int)health, (int)maxHealth};
        return stats;
    }

    public String getDescription(){
        return description;
    }

    public Color getNameTextCol(){
        return(nameTextCol);
    }

    public Cell getCurrentCell(){
        return loc;
    }

    public int getRange(){
        return range;
    }

    public void setLocation(Cell location){
        this.loc = location;
    }

    public void addItem(Item item){
        inventory.add(item);
        item.removeFromMap();
    }

    public void attack(Character defender, Grid caller){
        Color messageCol = new Color(255, 255, 255);
        double damage;
        switch(role){
            case BARBARIAN:
                damage = strength*2 + equipedWeapon.strength;
                messageCol = new Color(242, 160, 46);
                break;
            case FIGHTER:
                damage = (strength + equipedWeapon.strength)*2;
                messageCol = new Color(46, 190, 242);
                break;
            case RANGER:
                damage = strength + equipedWeapon.strength;
                messageCol = new Color(49, 242, 46);
                break;
            case MAGE:
                damage = wisdom*2 + equipedWeapon.wisdom;
                messageCol = new Color(107, 23, 232);
                break;
            default:
                damage = 0;
        }
        defender.health -= damage;
        caller.changeMessage(this.name + " the " + this.roleAsText + " hit " + defender.name + " with their " + equipedWeapon.name + " for " + damage + " damage!", messageCol);
        if(defender.checkIsDead()){
            caller.changeMessage(this.name + " the " + this.roleAsText + " KILLED " + defender.name + " with their " + equipedWeapon.name + "!!!!", Color.RED);
        }
    }

    public boolean checkIsDead(){
        if(health <= 0){
            loc.contentsChar = null;
            this.loc = null;
            shape.clear();
            return true;
        }
        return false;
    }


    
    public void draw(){ //Function to draw each Character based on Race/Role/Player
        //System.out.println(name + "is at location (" + loc.x + "," + loc.y + ")");
        shape.clear();
        //Polygon declarations, we'll change these depending on the role
        Polygon part1 = new Polygon();
        Polygon part2 = new Polygon();
        Polygon part3 = new Polygon();
        Polygon part4 = new Polygon();
        Polygon part5 = new Polygon();

        
        switch(role){ //The contents of each case was written by AI!! I was not interested in spending hours making cool icons for no marks </3
            case BARBARIAN:
                
                // Helmet base
                part1.addPoint(loc.x + 14, loc.y + 8);
                part1.addPoint(loc.x + 26, loc.y + 8);
                part1.addPoint(loc.x + 28, loc.y + 12);
                part1.addPoint(loc.x + 28, loc.y + 18);
                part1.addPoint(loc.x + 12, loc.y + 18);
                part1.addPoint(loc.x + 12, loc.y + 12);
                
                // Left horn
                part2.addPoint(loc.x + 10, loc.y + 4);
                part2.addPoint(loc.x + 14, loc.y + 6);
                part2.addPoint(loc.x + 16, loc.y + 10);
                part2.addPoint(loc.x + 12, loc.y + 12);
                
                // Right horn  
                part3.addPoint(loc.x + 28, loc.y + 12);
                part3.addPoint(loc.x + 24, loc.y + 10);
                part3.addPoint(loc.x + 26, loc.y + 6);
                part3.addPoint(loc.x + 30, loc.y + 4);
                
                // Face
                part4.addPoint(loc.x + 16, loc.y + 14);
                part4.addPoint(loc.x + 24, loc.y + 14);
                part4.addPoint(loc.x + 22, loc.y + 18);
                part4.addPoint(loc.x + 18, loc.y + 18);
                
                // Body 
                part5.addPoint(loc.x + 10, loc.y + 18);
                part5.addPoint(loc.x + 30, loc.y + 18);
                part5.addPoint(loc.x + 28, loc.y + 34);
                part5.addPoint(loc.x + 12, loc.y + 34);
                
                shape.add(part1);
                shape.add(part2);
                shape.add(part3);
                shape.add(part4);
                shape.add(part5);
                break;

                case FIGHTER:

                //Helm
                part1.addPoint(loc.x + 14, loc.y + 6);
                part1.addPoint(loc.x + 26, loc.y + 6);
                part1.addPoint(loc.x + 28, loc.y + 10);
                part1.addPoint(loc.x + 28, loc.y + 19);
                part1.addPoint(loc.x + 12, loc.y + 19);
                part1.addPoint(loc.x + 12, loc.y + 10);
                
                // Visor
                part2.addPoint(loc.x + 15, loc.y + 13);
                part2.addPoint(loc.x + 25, loc.y + 13);
                part2.addPoint(loc.x + 25, loc.y + 17);
                part2.addPoint(loc.x + 15, loc.y + 17);
                
                // Body
                part3.addPoint(loc.x + 10, loc.y + 19);
                part3.addPoint(loc.x + 30, loc.y + 19);
                part3.addPoint(loc.x + 28, loc.y + 34);
                part3.addPoint(loc.x + 12, loc.y + 34);
                
                shape.add(part1);
                shape.add(part2);
                shape.add(part3);
                break;
                
            case MAGE:
                
                // Pointed hat
                part1.addPoint(loc.x + 20, loc.y + 4);
                part1.addPoint(loc.x + 22, loc.y + 4);
                part1.addPoint(loc.x + 28, loc.y + 16);
                part1.addPoint(loc.x + 12, loc.y + 16);
                
                // Face
                part2.addPoint(loc.x + 14, loc.y + 16);
                part2.addPoint(loc.x + 26, loc.y + 16);
                part2.addPoint(loc.x + 24, loc.y + 24);
                part2.addPoint(loc.x + 16, loc.y + 24);
                
                // Body
                part3.addPoint(loc.x + 16, loc.y + 24);
                part3.addPoint(loc.x + 24, loc.y + 24);
                part3.addPoint(loc.x + 28, loc.y + 36);
                part3.addPoint(loc.x + 12, loc.y + 36);
                
                shape.add(part1);
                shape.add(part2);
                shape.add(part3);
                break;
                
            case RANGER:
                part1.addPoint(loc.x + 18, loc.y + 4);
                part1.addPoint(loc.x + 22, loc.y + 4);
                part1.addPoint(loc.x + 26, loc.y + 12);
                part1.addPoint(loc.x + 14, loc.y + 12);
                
                // Hat
                part2.addPoint(loc.x + 10, loc.y + 12);
                part2.addPoint(loc.x + 30, loc.y + 12);
                part2.addPoint(loc.x + 28, loc.y + 16);
                part2.addPoint(loc.x + 12, loc.y + 16);
                
                // Feather
                part3.addPoint(loc.x + 24, loc.y + 2);
                part3.addPoint(loc.x + 26, loc.y + 2);
                part3.addPoint(loc.x + 28, loc.y + 8);
                part3.addPoint(loc.x + 22, loc.y + 8);
                
                // Face
                part4.addPoint(loc.x + 16, loc.y + 16);
                part4.addPoint(loc.x + 24, loc.y + 16);
                part4.addPoint(loc.x + 22, loc.y + 24);
                part4.addPoint(loc.x + 18, loc.y + 24);
                
                // Body
                part5.addPoint(loc.x + 14, loc.y + 24);
                part5.addPoint(loc.x + 26, loc.y + 24);
                part5.addPoint(loc.x + 24, loc.y + 34);
                part5.addPoint(loc.x + 16, loc.y + 34);
                
                shape.add(part1);
                shape.add(part2);
                shape.add(part3);
                shape.add(part4);
                shape.add(part5);
                break;
                
            default:
                break;
        }

    }

}