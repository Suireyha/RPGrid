public class Character extends Actor{ //Child of Actor since we'll draw these
    String name; //Characters name
    RoleType role; //Characters role/class
    RaceType race; //Characters race
    Inventory inventory = new Inventory(); //Characters inventory
    boolean player = false; //If this character is a user controlled player, make this true

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
}