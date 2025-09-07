public class Character extends Actor{ //Child of Actor since we'll draw these
    String name; //Characters name
    Role role; //Characters role/class
    Race race; //Characters race
    Inventory inventory; //Characters inventory
    boolean player = false; //If this character is a user controlled player, make this true

    //Attributes
    public int strength = 0; //Used for non-magical combat rolls
    public int wisdom = 0; //Used for magical combat rolls
    public int constitution = 0; //Used to determine HP
    public int initiative = 0; //Used to determine turns

    public double maxHealth = 0;
    public double health = 0;

    


    Character(){
        //Character constructor
    }
}