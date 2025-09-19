import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PopUp extends JFrame{
    int winWidth = 350;
    int winHeight = 200;

    PopUp(){
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel container = new JPanel();
        this.setContentPane(container);
        this.setVisible(true);
        this.setBounds(1000, 700, winWidth, winHeight); //Window is being drawn at x=350 y=50, dimensions are 1000^2
        container.setBackground(new Color(47, 48, 49));
    }
    
}