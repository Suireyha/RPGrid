import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;
import java.awt.Polygon;
import java.util.ArrayList;

public abstract class Actor {
  public Actor(){}
  List<Polygon> shape = new ArrayList<>();
  Cell loc;
  Color drawCol = Color.BLACK; //Actors are drawn in black by default

  public void paint(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g.setColor(drawCol);
    for(Polygon feature: shape){
      g.drawPolygon(feature);
    }
  }

}
