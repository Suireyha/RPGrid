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
    String statDescription;

    PopUp<Item> popup; //Temporary, this should be moved to inventory later

    boolean onMap = true; //Variable that tracks if the item is on the map or nor (if not, will exist in a players inventory ig)
    Cell loc; //It's location, if any
    //Stat effects mostly for weapons/armour
    int strength;
    int wisdom;
    int constitution;
    int initiative;

    List<Polygon> shape = new ArrayList<>();
    Color drawCol = Color.BLACK; //Items are drawn in black by default
    Color nameTextCol = Color.WHITE;

    //For health potions mainly, but some other items may need this
    float healMod;

    Item(String name, String description, String statDescription, int strength, int wisdom, int constitution, int initiative){ //Constructor to be used by basic Weapons and Armours
        this.name = name;
        this.description = description;
        this.strength = strength;
        this.wisdom = wisdom;
        this.constitution = constitution;
        this.initiative = initiative;
        this.statDescription = statDescription;
        nameTextCol = new Color(255, 249, 121); //A nice gold colour for items
    }
    Item(String name, String description, String statDescription, int strength, int wisdom, int constitution, int initiative, float healMod){ //Constructor to be used by advanced Weapons & Armours
        this.name = name;
        this.description = description;
        this.strength = strength;
        this.wisdom = wisdom;
        this.constitution = constitution;
        this.initiative = initiative;
        this.healMod = healMod;
        this.statDescription = statDescription;
        nameTextCol = new Color(255, 249, 121); //A nice gold colour for items
    }

    public void setCellContentsToThisInstance(){ //The best variable name of all time
        loc.contentsItem = this;
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(drawCol);
        for(Polygon feature: shape){
          g.drawPolygon(feature);
        }
    }

    public void displayWin(){ //Temporary, this should be moved to inventory later
        if(onMap) popup = new PopUp<Item>(this);
    }

    public mapEntityType getEntityType(){
        return MapEntity.mapEntityType.ITEM;
    }


    public String getName(){
        return name;
    }

    public int[] getStats(){
        int[] stats = {strength, wisdom, constitution, initiative, (int)healMod};
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

    public void setLocation(Cell location){
        this.loc = location;
    }

    public void addItem(Item item){
        System.out.println("Does nothing");
    }

    public void removeFromMap(){
        this.onMap = false;
        loc.contentsItem = null;
        this.loc = null;
        shape.clear();
    }

    public void attack(Character defender, Grid caller){
        //Do nothing, this ones just for characters
    }

    public int getRange(){
        //Yet another Character only thing
        return 0;
    }

    public void use(Character caller){
        caller.health += healMod;
    }

    public void equip(Character caller){
        if(this instanceof Weapon){
            caller.strength += this.strength - caller.equipedWeapon.strength;
            caller.wisdom += this.wisdom - caller.equipedWeapon.wisdom;
            caller.constitution += this.constitution - caller.equipedWeapon.constitution;
            caller.initiative += this.initiative - caller.equipedWeapon.initiative;
            caller.equipedWeapon = (Weapon)this;
        }
        else{
            caller.strength += this.strength - caller.equipedArmour.strength;
            caller.wisdom += this.wisdom - caller.equipedArmour.wisdom;
            caller.constitution += this.constitution - caller.equipedArmour.constitution;
            caller.initiative += this.initiative - caller.equipedArmour.initiative;
            caller.equipedArmour = (Armour)this;
        }
    }

    public void draw(){ //All items should have draw function for when they're on the map
        Polygon part1 = new Polygon();
        shape.clear();
        part1.addPoint(loc.x + 20, loc.y + 8);
        part1.addPoint(loc.x + 28, loc.y + 20);
        part1.addPoint(loc.x + 20, loc.y + 32);
        part1.addPoint(loc.x + 12, loc.y + 20);
        
        shape.add(part1);
    }
}