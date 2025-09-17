import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;


public class Main extends JFrame {
  int winWidth = 900;
  int winHeight = 900;
    public static void main(String[] args) throws Exception {
      Main window = new Main();
      window.setBounds(350, 100, 900, 900); //Window is being drawn at x=350 y=100, dimensions are 900^2
      window.setBackground(new Color(47, 48, 49));
      window.run();
    }

    class Canvas extends JPanel {
      Stage stage = new Stage();
      
      public Canvas() {
        setPreferredSize(new Dimension(winWidth, winHeight));
      }

      @Override
      public void paint(Graphics g) {
        stage.paint(g, getMousePosition());
      }
    }

    private Main() {
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      Canvas canvas = new Canvas();
      this.setContentPane(canvas);
      this.pack();
      this.setVisible(true);

      //Create characters
      Character p1 = new Character("John Snow", Character.RoleType.FIGHTER, Character.RaceType.HUMAN, true);



      /*
      Character p2 = new Character();
      Character p3 = new Character();
       */
      Character en1 = new Character("Wabbajack", Character.RoleType.MAGE, Character.RaceType.ELF, false);


      /*
      Character en2 = new Character();
      Character en3 = new Character();
      */


    }

    public void run() {
      while(true) {
        repaint();
      }
    }
}
