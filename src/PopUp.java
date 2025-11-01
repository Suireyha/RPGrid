import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

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
    Color white = new Color(240, 240, 220);
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

    

    /* I'm gonna leave an explination for how this actually works here in case I forget when I write the README. Sorry if this is verbose :(
        Basically, Consumer<T> is an interface that only has the signature for ONE method (void accept(T t)). So, we can give it a lambda
        to do any operation that takes one argument and returns nothing pretty much, and since we're going to be using this to just make function 
        calls to MakeNewRow() and what not, it's perfect. To be clear, the line custom.accept(this) is just running the lambdas we gave it.

        This now means that if we want to create a unique popup that will only ever exist once (like the loading screen), we can just call this 
        constructor instead and give it a lambda that will make the PopUp how we want. Which is handy for really unique use cases like the loading screen

        This should NOT be used for something that you'd want to repeat (like the other popups, they should have their own solutions in this class 
        since they are called over and over, we want everything to be as dynamic as possible)
    */
    //Alternative constructor (for unique popups, uses lambdas!!)
    PopUp(String title, Color titleColor, Consumer<PopUp> custom) {
        SetUp(); //Regular setup doesn't have any mapentity functions
        TextHeaders titleHeader = new TextHeaders(title, 
            TextHeaders.Header.HEADER2, titleColor);
        titleHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleHeader.setBorder(new EmptyBorder(0,0,10,0));
        infoPanel.add(titleHeader);
        custom.accept(this);
    }

    PopUp(T entity){
        SetUp();
        int[] entityStats = entity.getStats();
        String titleText = entity.getName();
        TextHeaders title = new TextHeaders("~~~~~~~ " + titleText + " ~~~~~~~", TextHeaders.Header.HEADER1, entity.getNameTextCol());
        
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(new EmptyBorder(0,0,10,0)); //Spacer for next line
        infoPanel.add(title);

        
        switch(entity.getEntityType()){
            case PLAYER:
            case ENEMIE:
                makeNewContentRow("ABOUT");
                makeNewContentRow("", entity.getDescription());
                makeNewContentRow("STATS");
                makeNewContentRow("Strength:", "" + entityStats[0]);
                makeNewContentRow("Wisdom:", "" + entityStats[1]);
                makeNewContentRow("Constitution:", "" + entityStats[2]);
                makeNewContentRow("Initiative:", "" + entityStats[3]);
                makeNewContentRow("Health:", "" + entityStats[4] + "/" + entityStats[5]);
                makeNewContentRow("INVENTORY");
                for(int i = 0; i < ((Character)entity).inventory.size(); i++){
                    makeNewContentRow(((Character)entity).inventory.get(i).getName(), ((Character)entity).inventory.get(i).getStatDescription());
                }
                break;
            case ITEM: 
                makeNewContentRow("ABOUT");
                makeNewContentRow("", entity.getDescription());
                makeNewContentRow("STATS");
                makeNewContentRow("Strength:", "+" + entityStats[0]);
                makeNewContentRow("Wisdom:", "+" + entityStats[1]);
                makeNewContentRow("Constitution:", "+" + entityStats[2]);
                makeNewContentRow("Initiative:", "+" + entityStats[3]);
                makeNewContentRow("Health:", "+" + entityStats[4]);
                break;
            default:
                System.out.println("ERROR! PopUp called by something that isn't an entity? Check PopUp constructor!");
        }
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