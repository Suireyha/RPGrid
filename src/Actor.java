import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.awt.Polygon;
import java.util.ArrayList;

public abstract class Actor {
  public Actor(){}
  List<Polygon> shape = new ArrayList<>();
  Cell loc;
  Color drawCol = Color.BLACK; //Actors are drawn in black by default

  public void paint(Graphics g) {
    g.setColor(drawCol);
    for(Polygon feature: shape){
      g.drawPolygon(feature);
    }
  }

}
