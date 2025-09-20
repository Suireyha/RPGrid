import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.swing.border.*;
import javax.swing.BorderFactory;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PopUp <T extends MapEntity> extends JFrame{
    int winWidth = 350;
    int winHeight = 400;
    JPanel container;
    JPanel infoPanel;
    JPanel btnPanel;
    T evoker;

    private void SetUp(){
        container = new JPanel();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Close THIS popup, but don't destroy the whole program
        this.setContentPane(container);
        this.setVisible(true);
        this.setBounds(1000, 400, winWidth, winHeight); //Window is being drawn at x=350 y=50, dimensions are 1000^2
        container.setBackground(new Color(47, 48, 49));
        //popupContainer.setLayout(getLayout()); //Give me like a flex box kinda thing



        infoPanel = new JPanel(); //Div that will hold stats and stuff
        btnPanel = new JPanel(); //Div for the buttons

        infoPanel.setBackground(new Color(47, 48, 49));
        infoPanel.setBorder(new EmptyBorder(0, 0, 80, 0)); //Padding on the bottom
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        btnPanel.setBackground(new Color(47, 48, 49));
        

        container.add(infoPanel, BorderLayout.CENTER); //Justify infoPanel to fill
        container.add(btnPanel, BorderLayout.SOUTH); //Justify btnPanel to stick to the bottom

    }
    
    PopUp(T evoker){
        SetUp();

        if(evoker instanceof Character){
            //Character specific functionality
        }
        if(evoker instanceof Item){
            //Item specific functionality
        }   

        JLabel lbl = new JLabel("Example text :fire:");
        lbl.setFont(new Font("Serif", Font.PLAIN, 14));
        lbl.setForeground(Color.RED);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        //lbl.getGraphics().RenderingHints = RenderingHints.KEY_ANTIALIASING;
        infoPanel.add(lbl);

        Btn btn1 = new Btn(true, "XX", 0);
        btnPanel.add(btn1);

        Btn btn2 = new Btn(true, "XO", 0);
        btnPanel.add(btn2);
        //Add Labels and stuff here
    }
    
}