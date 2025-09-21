import java.awt.Polygon;

public class Armour extends Item{

    Armour(String name, String description, int strength, int wisdom, int constitution, int initiative){
        super(name, description, strength, wisdom, constitution, initiative);
    }

    @Override
    public void draw(){ //All items should have draw function for when they're on the map
        //!!!The following polygon code was written by AI!
        shape.clear(); //So that it doesn't draw duplicates if moved (not that we're moving this atm)
        Polygon part1 = new Polygon();
        Polygon part2 = new Polygon();
        Polygon part3 = new Polygon();

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

        shape.add(part1);
        shape.add(part2);
        shape.add(part3);
    }
}