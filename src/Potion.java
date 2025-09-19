import java.awt.Graphics;
import java.awt.Polygon;

public class Potion extends Item{ //Child of Item
    Potion(String name, String description, int strength, int wisdom, int constitution, int initiative, float healMod){
        super(name, description, strength, wisdom, constitution, initiative, healMod);
    }
}