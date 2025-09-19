import java.awt.Graphics;
import java.awt.Polygon;

public class Armour extends Item{

    Armour(String name, String description, int strength, int wisdom, int constitution, int initiative){
        super(name, description, strength, wisdom, constitution, initiative);
    }

    @Override
    public void draw(Graphics g){ //All items should have draw function for when they're on the map
        //!!!The following polygon code was written by AI!
        Polygon part1 = new Polygon();
        Polygon part2 = new Polygon();
        Polygon part3 = new Polygon();
        Polygon part4 = new Polygon();

        // Main chestplate
        part1.addPoint(loc.x + 12, loc.y + 10);
        part1.addPoint(loc.x + 28, loc.y + 10);
        part1.addPoint(loc.x + 26, loc.y + 28);
        part1.addPoint(loc.x + 14, loc.y + 28);

        // Shoulder guards
        part2.addPoint(loc.x + 8, loc.y + 8);
        part2.addPoint(loc.x + 14, loc.y + 8);
        part2.addPoint(loc.x + 16, loc.y + 14);
        part2.addPoint(loc.x + 10, loc.y + 14);

        part3.addPoint(loc.x + 26, loc.y + 8);
        part3.addPoint(loc.x + 32, loc.y + 8);
        part3.addPoint(loc.x + 30, loc.y + 14);
        part3.addPoint(loc.x + 24, loc.y + 14);

        // Belt/Lower armor
        part4.addPoint(loc.x + 14, loc.y + 28);
        part4.addPoint(loc.x + 26, loc.y + 28);
        part4.addPoint(loc.x + 24, loc.y + 34);
        part4.addPoint(loc.x + 16, loc.y + 34);

        shape.add(part1);
        shape.add(part2);
        shape.add(part3);
        shape.add(part4);
    }
}