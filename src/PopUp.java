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
    Color uiHeader = new Color(255, 255, 201);
    T evoker;

    private void SetUp(){
        container = new JPanel();
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Close THIS popup, but don't destroy the whole program
        this.setContentPane(container);
        this.setVisible(true);
        this.setBounds(1000, 400, winWidth, winHeight); //Window is being drawn at x=350 y=50, dimensions are 1000^2
        container.setLayout(new BorderLayout());
        container.setBackground(new Color(47, 48, 49));
        //popupContainer.setLayout(getLayout()); //Give me like a flex box kinda thing

        infoPanel = new JPanel(); //Div that will hold stats and stuff
        btnPanel = new JPanel(); //Div for the buttons

        infoPanel.setBackground(new Color(47, 48, 49));
        infoPanel.setBorder(new EmptyBorder(0, 0, 80, 0)); //Padding on the bottom
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS)); //Should let things flow onto new lines
        
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
        TextHeaders title = new TextHeaders("~~~~~~~ " + titleText + " ~~~~~~~", TextHeaders.Header.HEADER1, entity.getNameTextCol());
        
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(new EmptyBorder(0,0,10,0)); //Spacer for next line
        infoPanel.add(title);

        
        switch(entity.getEntityType()){
            case PLAYER:
                //ContentRow first = new ContentRow();
                //TextHeaders statHeader = new TextHeaders("STATS", TextHeaders.Header.HEADER2, uiHeader);
                //first.add(statHeader);
                //infoPanel.add(first);
                makeNewContentRow("STATS");

                //ContentRow second = new ContentRow();
                //TextHeaders strengthHeader = new TextHeaders("Srength:", TextHeaders.Header.TEXTB, Color.WHITE);
                //TextHeaders strengthStat = new TextHeaders("13", TextHeaders.Header.TEXT, Color.WHITE);
                //second.add(strengthHeader, BorderLayout.WEST);
                //second.add(strengthStat, BorderLayout.EAST);
                makeNewContentRow("Strength", "13");
                
                
                //infoPanel.add(second);
                break;
            case ENEMIE:
                break;
            case ITEM:
                break;
            default:
                System.out.println("ERROR! PopUp called by something that isn't an entity? Check PopUp constructor!");
        }
        

        


        btnPanel.add(atkBtn);        
        btnPanel.add(invBtn);
    }

    public void makeNewContentRow(String left){
        ContentRow row = new ContentRow();
        TextHeaders header = new TextHeaders(left, TextHeaders.Header.HEADER2, uiHeader);
        row.add(header);
        infoPanel.add(row);

    }
    public void makeNewContentRow(String left, String right){
        ContentRow row = new ContentRow();

        TextHeaders textLeft = new TextHeaders(left, TextHeaders.Header.TEXTB, Color.WHITE);
        TextHeaders textRight = new TextHeaders(right, TextHeaders.Header.TEXT, Color.WHITE);

        row.add(textLeft, BorderLayout.WEST);
        row.add(textRight, BorderLayout.EAST);
        infoPanel.add(row);
        
    }
    
}