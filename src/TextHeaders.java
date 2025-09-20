import javax.swing.*;
import java.awt.*;

import java.awt.Graphics2D;

import java.awt.Color;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TextHeaders extends JLabel{ //I don't want to clutter my code with creating new labels for everything so yeah
    Color mainTextCol = new Color(255, 255, 255);
    Color glowTextCol = new Color(255, 255, 255, 0); //By default it's invisible so that H2 and Text can draw without a glow

    enum Header{
        HEADER1,
        HEADER2,
        TEXTB,
        TEXT
    }

    public void setUp(Header type){
        switch(type){
            case HEADER1:
                glowTextCol = new Color(mainTextCol.getRed(), mainTextCol.getGreen(), mainTextCol.getBlue(), 30); //The same colour, just with a MUCH lower opacity for the glow
                this.setFont(new Font("Serif", Font.BOLD, 18));
                break;
            case HEADER2:
                this.setFont(new Font("Serif", Font.BOLD, 17));
                break;
            case TEXTB:
                this.setFont(new Font("Serif", Font.BOLD, 15));
                break;
            case TEXT:
                this.setFont(new Font("Serif", Font.PLAIN, 15));
                break;
            default:

        }

    }

    TextHeaders(String text, TextHeaders.Header type, Color colour){
        super(text);
        mainTextCol = colour;
        setUp(type);
    }

    TextHeaders(){
        super("Default text");
        setUp(Header.TEXT);
    }
    
    @Override
    protected void paintComponent(Graphics g) { //Always draw labels with Anti-aliasing so it looks nice :)
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    
        String text = getText();
        g2.setFont(getFont());
        FontMetrics fm = g2.getFontMetrics();
        int x = 0;
        int y = fm.getAscent();
    
        // Draw blurred text
        for (int i = 0; i < 5; i++) {
            g2.setColor(glowTextCol); // !!DO NOT!! let this be completely opaque
            g2.drawString(text, x - i, y);
            g2.drawString(text, x + i, y);
            g2.drawString(text, x, y - i);
            g2.drawString(text, x, y + i);
        }
    
        // Draw main text
        g2.setColor(mainTextCol);
        g2.drawString(text, x, y);
        g2.dispose();
    }

}

