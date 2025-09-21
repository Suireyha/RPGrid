public class Potion extends Item{ //Child of Item
    Potion(String name, String description, String statDescription, int strength, int wisdom, int constitution, int initiative, float healMod){
        super(name, description, statDescription, strength, wisdom, constitution, initiative, healMod);
    } 
}