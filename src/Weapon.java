import java.awt.Polygon;

public class Weapon extends Item{ //Child of item

    Type type;
    int range;

    enum Type{
        SWORD,
        BOW,
        STAFF,
        AXE,
    }

    Weapon(String name, String description, int strength, int wisdom, int constitution, int initiative, Type type){
        super(name, description, strength, wisdom, constitution, initiative);
        this.type = type;
        switch(type){
            case SWORD:
                range = 2;
                break;
            case BOW:
                range = 10;
                break;
            case STAFF:
                range = 6;
                break;
            case AXE:
                range = 3;
                break;
        }
    }

    @Override
    public void draw(){ //Weapons draw based on type of weapon!
        //!!The polygons were written by AI!!
        shape.clear(); //So that it doesn't draw duplicates if moved (not that we're moving this atm)
        Polygon part1 = new Polygon();
        Polygon part2 = new Polygon();
        Polygon part3 = new Polygon();
        Polygon part4 = new Polygon();
        Polygon part5 = new Polygon();

        switch(type){
            case SWORD:
                // Blade
                part1.addPoint(loc.x + 19, loc.y + 4);
                part1.addPoint(loc.x + 21, loc.y + 4);
                part1.addPoint(loc.x + 23, loc.y + 28);
                part1.addPoint(loc.x + 17, loc.y + 28);
                
                // Crossguard
                part2.addPoint(loc.x + 12, loc.y + 28);
                part2.addPoint(loc.x + 28, loc.y + 28);
                part2.addPoint(loc.x + 28, loc.y + 30);
                part2.addPoint(loc.x + 12, loc.y + 30);
                
                // Handle
                part3.addPoint(loc.x + 18, loc.y + 30);
                part3.addPoint(loc.x + 22, loc.y + 30);
                part3.addPoint(loc.x + 22, loc.y + 36);
                part3.addPoint(loc.x + 18, loc.y + 36);
                
                // Pommel
                part4.addPoint(loc.x + 16, loc.y + 36);
                part4.addPoint(loc.x + 24, loc.y + 36);
                part4.addPoint(loc.x + 22, loc.y + 38);
                part4.addPoint(loc.x + 18, loc.y + 38);
                
                shape.add(part1);
                shape.add(part2);
                shape.add(part3);
                shape.add(part4);
                break;
            case BOW:
                // Bow string (left curve)
                part1.addPoint(loc.x + 10, loc.y + 8);
                part1.addPoint(loc.x + 12, loc.y + 8);
                part1.addPoint(loc.x + 18, loc.y + 20);
                part1.addPoint(loc.x + 12, loc.y + 32);
                part1.addPoint(loc.x + 10, loc.y + 32);
                part1.addPoint(loc.x + 16, loc.y + 20);
                
                // Bow string (right curve)
                part2.addPoint(loc.x + 28, loc.y + 8);
                part2.addPoint(loc.x + 30, loc.y + 8);
                part2.addPoint(loc.x + 24, loc.y + 20);
                part2.addPoint(loc.x + 30, loc.y + 32);
                part2.addPoint(loc.x + 28, loc.y + 32);
                part2.addPoint(loc.x + 22, loc.y + 20);
                
                // Bowstring
                part3.addPoint(loc.x + 16, loc.y + 10);
                part3.addPoint(loc.x + 17, loc.y + 10);
                part3.addPoint(loc.x + 23, loc.y + 30);
                part3.addPoint(loc.x + 22, loc.y + 30);
                
                // Arrow nocked
                part4.addPoint(loc.x + 6, loc.y + 19);
                part4.addPoint(loc.x + 18, loc.y + 19);
                part4.addPoint(loc.x + 18, loc.y + 21);
                part4.addPoint(loc.x + 6, loc.y + 21);
                
                // Arrowhead
                part5.addPoint(loc.x + 4, loc.y + 18);
                part5.addPoint(loc.x + 8, loc.y + 20);
                part5.addPoint(loc.x + 4, loc.y + 22);
                
                shape.add(part1);
                shape.add(part2);
                shape.add(part3);
                shape.add(part4);
                shape.add(part5);
                break;
            case STAFF:
            case AXE:
            default:
                super.draw();
                break;
        }
    }

}