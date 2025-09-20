import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.swing.border.*;

//import javax.swing.BorderFactory;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;

public class PopUp <T extends MapEntity> extends JFrame{
    int winWidth = 350;
    int winHeight = 400;
    JPanel container;
    JPanel infoPanel;
    JPanel btnPanel;
    Color titleCol;
    Color textCol = new Color(255, 255, 255); //White by default
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
    
    PopUp(T entity){
        SetUp();

        Btn atkBtn = new Btn(true, "ATK", 0);
        Btn invBtn = new Btn(true, "INV", 1);
        String titleText = entity.getName();
        TextHeaders title = new TextHeaders("~~~~~~ " + titleText + " ~~~~~~", TextHeaders.Header.HEADER1, entity.getNameTextCol());
        
        
        switch(entity.getEntityType()){
            case PLAYER:
                break;
            case ENEMIE:
                break;
            case ITEM:
                break;
            default:
                System.out.println("ERROR! PopUp called by something that isn't an entity? Check PopUp constructor!");
        }
        

        
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(title);
        
        btnPanel.add(atkBtn);        
        btnPanel.add(invBtn);
    }
    
}