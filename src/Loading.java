import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.*;
import java.awt.*;

import javax.swing.border.*;
import javax.swing.border.EmptyBorder;

public class Loading extends JFrame{
    int winWidth = 350;
    int winHeight = 400;
    JPanel container;
    JPanel infoPanel;
    JPanel btnPanel;
    Color uiHeader = new Color(255, 255, 201);
    Color white = new Color(240, 240, 220);

    Loading(){

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

        TextHeaders title = new TextHeaders("LOADING...", TextHeaders.Header.HEADER2, Color.CYAN);
        
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(new EmptyBorder(0,0,10,0)); //Spacer for next line
        infoPanel.add(title);

        makeNewContentRow("         (This may take a while)");

    }

    public void makeNewContentRow(String left){
        ContentRow row = new ContentRow();
        TextHeaders header = new TextHeaders(left, TextHeaders.Header.HEADER2, uiHeader);
        row.add(header);
        infoPanel.add(row);
    }

    public void makeNewContentRow(String left, String right){
        ContentRow row = new ContentRow();
        TextHeaders textLeft = new TextHeaders(left, TextHeaders.Header.TEXTB, white);
        
        if (left.equals("")) { //Can add other lhs that have large text blobs here later if I need
            row.setLayout(new BorderLayout());
            
            JTextArea textRight = new JTextArea(right);
            //textRight.setSize(14, 14);
            textRight.setWrapStyleWord(true);
            textRight.setLineWrap(true);
            textRight.setEditable(false);
            textRight.setOpaque(false);
            textRight.setForeground(white);
            //textRight.setBorder(new EmptyBorder(0, 10, 0, 0));
            
            row.add(textLeft, BorderLayout.NORTH);
            row.add(textRight, BorderLayout.CENTER);
        } 
        else {
            //Normal stats
            TextHeaders textRight = new TextHeaders(right, TextHeaders.Header.TEXTB, white);
            row.add(textLeft, BorderLayout.WEST);
            row.add(textRight, BorderLayout.EAST);
        }
        
        infoPanel.add(row);
    }

}