import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;


public class Main extends JFrame {
    int winWidth = 1000;
    int winHeight = 1000;
    Stage stage = new Stage();
    public static void main(String[] args) throws Exception {
      Main window = new Main();
      window.setBounds(350, 50, 1000, 1000); //Window is being drawn at x=350 y=50, dimensions are 1000^2
      window.setBackground(new Color(47, 48, 49));
      window.run();
    }

    class Canvas extends JPanel implements MouseListener{ //!! NOte to self, put this in justifications.md
      
      public Canvas() {
        setPreferredSize(new Dimension(winWidth, winHeight));
        addMouseListener(this); //Actually adds the mouse listeners to the canvas instance
      }

      @Override
      public void mouseClicked(MouseEvent e){ //Mouse clicked event listener inside the grid(IMPORTANT!!)
        if(SwingUtilities.isLeftMouseButton(e)){
          //Left click
          stage.grid.cellLeftClicked(e.getPoint());
        }
        if(SwingUtilities.isRightMouseButton(e)){
          //Right click
          stage.grid.cellRightClicked(e.getPoint());
        }
        
      }

      @Override
      public void paint(Graphics g) {
        stage.paint(g, getMousePosition());
      }

      //I have to implement these, event as empty methods, as per the interface or it'll give me a syntax error :(
      @Override
      public void mousePressed(MouseEvent e) {}
      @Override
      public void mouseReleased(MouseEvent e) {}
      @Override
      public void mouseEntered(MouseEvent e) {}
      @Override
      public void mouseExited(MouseEvent e) {}
    }

    private Main() {
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      Canvas canvas = new Canvas();
      this.setContentPane(canvas);
      this.pack();
      this.setVisible(true);

      //Create items
      Weapon sword = new Weapon("Iron Sword", "A simple sword. Made from Iron.", "+2 STR", 2, 0, 0, 0, Weapon.Type.SWORD);
      Armour leatherVest = new Armour("Leather Vest", "A vest crafted from cow hide.", "+1 CONS, +1 INIT", 0, 0, 1, 1);
      Potion healthPotion = new Potion("Health Potion", "A jar of red liquid- looks tasty!", "+10 HP", 0, 0, 0, 0, 10f);

      stage.addItem(sword);
      stage.addItem(leatherVest);
      stage.addItem(healthPotion);
      
      //Create characters
      Character p1 = new Character("John Snow", Character.RoleType.FIGHTER, Character.RaceType.HUMAN, true);
      Character p2 = new Character("Glimbo", Character.RoleType.BARBARIAN, Character.RaceType.ORC, true);
      Character p3 = new Character("Emily", Character.RoleType.RANGER, Character.RaceType.DWARF, true);
      stage.addCharacter(p1);
      stage.addCharacter(p2);
      stage.addCharacter(p3);

      Character en1 = new Character("Wabbajack", Character.RoleType.MAGE, Character.RaceType.ELF, false);
      Character en2 = new Character("Aloy", Character.RoleType.RANGER, Character.RaceType.HUMAN, false);
      Character en3 = new Character("The Hound", Character.RoleType.FIGHTER, Character.RaceType.HUMAN, false);
      stage.addCharacter(en1);
      stage.addCharacter(en2);
      stage.addCharacter(en3);

    }

    public void run() {
      while(true) {
        repaint();
      }
    }
}
