import java.awt.Color;
import java.awt.Polygon;

public class Character extends Actor{ //Child of Actor since we'll draw these
    String name; //Characters name
    RoleType role; //Characters role/class
    RaceType race; //Characters race
    Inventory inventory = new Inventory(); //Characters inventory
    boolean player = false; //If this character is a user controlled player, make this true
    Cell loc;
    //Attributes
    public int strength = 0; //Used for non-magical combat rolls
    public int wisdom = 0; //Used for magical combat rolls
    public int constitution = 0; //Used to determine HP
    public int initiative = 0; //Used to determine turns

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
        //**RULES** each role'ss attributes must sum to 16 
        switch(role){
            case BARBARIAN: //Slow, but hard to take out and super heavy hitting
                strength += 7;
                wisdom += 1;
                constitution += 6;
                initiative += 2;
                //Inventory.add();
                break;
            case FIGHTER: //Well rounded, and hard to take out
                strength += 4;
                wisdom += 2;
                constitution += 6;
                initiative += 4;
                //Inventory.add();
                break;
            case MAGE: //High DMG and quick, but glass cannon types
                strength += 1;
                wisdom += 7;
                constitution += 2;
                initiative += 5;
                //Inventory.add();
                break;
            case RANGER: //Super quick ranged attacks but can't take many hits
                strength += 3;
                wisdom += 2;
                constitution += 4;
                initiative += 7;
                //Inventory.add();
                break;
            default:
                //Error **IMPLEMENT LATER**
                break;
        }

        //**RULES** each race's attributes must sum to 5;
        switch(race){
            case HUMAN: //Good all rounder, a fine pick for all classes
                strength += 1;
                wisdom += 1;
                constitution += 1;
                initiative += 2;
                //Inventory.add();
                break;
            case ELF: //Weak and light but better spell casters and slightly quicker
                strength += 0;
                wisdom += 3;
                constitution += 0;
                initiative += 2;
                //Inventory.add();
                break;
            case ORC: //Super strong, but dumb and slow. Best suited to fighters or barbarians
                strength += 3;
                wisdom += 0;
                constitution += 2;
                initiative += 0;
                //Inventory.add();
                break;
            case DWARF: //Meant to be a decent pick for either rangers or mages who want more health
                strength += 0;
                wisdom += 2;
                constitution += 2;
                initiative += 1;
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
            drawCol = new Color(5, 8, 90);
        }
        else{
            drawCol = new Color(90, 0, 0);
        }
    }



    Character(String name, RoleType role, RaceType race, boolean isPlayer){ //Character constructor from preset (made by programmer in Main, not user made)
        //Set up the attributes based on the constructor arguments
        this.name = name;
        this.role = role;
        this.race = race;
        this.player = isPlayer;

        setStats();

    }
    Character(){
        //**IMPLEMENT LATER** This should be a character creator that lets users make their own characters. Low priority though
    }
    
    public void draw(){ //Function to draw each Character based on Race/Role/Player
        System.out.println("X = " + loc.x);
        System.out.println("Y = " + loc.y);

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