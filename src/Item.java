// Drawing stuff 
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Polygon;

import java.util.List;
import java.util.ArrayList;

public abstract class Item implements MapEntity{ //Making an Item that isn't something more specific doesn't make much sense, so it's abstract
    //All items need a name and description
    String name;
    String description;

    boolean onMap; //Variable that tracks if the item is on the map or nor (if not, will exist in a players inventory ig)
    Cell loc; //It's location, if any
    //Stat effects mostly for weapons/armour
    int strength;
    int wisdom;
    int constitution;
    int initiative;

    List<Polygon> shape = new ArrayList<>();
    Color drawCol = Color.BLACK; //Items are drawn in black by default

    //For health potions mainly, but some other items may need this
    float healMod;

    Item(String name, String description, int strength, int wisdom, int constitution, int initiative){ //Constructor to be used by basic Weapons and Armours
        this.name = name;
        this.description = description;
        this.strength = strength;
        this.wisdom = wisdom;
        this.constitution = constitution;
        this.initiative = initiative;
    }
    Item(String name, String description, int strength, int wisdom, int constitution, int initiative, float healMod){ //Constructor to be used by advanced Weapons & Armours
        this.name = name;
        this.description = description;
        this.strength = strength;
        this.wisdom = wisdom;
        this.constitution = constitution;
        this.initiative = initiative;
        this.healMod = healMod;
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(drawCol);
        for(Polygon feature: shape){
          g.drawPolygon(feature);
        }
    }

    public void draw(Graphics g){ //All items should have draw function for when they're on the map
        Polygon part1 = new Polygon();

        part1.addPoint(loc.x + 20, loc.y + 8);
        part1.addPoint(loc.x + 28, loc.y + 20);
        part1.addPoint(loc.x + 20, loc.y + 32);
        part1.addPoint(loc.x + 12, loc.y + 20);
        
        shape.add(part1);
    }
}