import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Graphics2D;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PopUp extends JFrame{
    int winWidth = 350;
    int winHeight = 200;
    JPanel container;

    private void SetUp(){
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        container = new JPanel();
        this.setContentPane(container);
        this.setVisible(true);
        this.setBounds(1000, 700, winWidth, winHeight); //Window is being drawn at x=350 y=50, dimensions are 1000^2
        container.setBackground(new Color(47, 48, 49));
    }
    
    PopUp(Item item){
        SetUp();
        Btn btn1 = new Btn(true, "EX", 0);
        container.add(btn1);

        //Add Labels and stuff here
    }

    PopUp(Character character){
        SetUp();
        
    }
    
}