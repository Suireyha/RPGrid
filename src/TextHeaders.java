import javax.swing.*;
import java.awt.*;

import java.awt.Graphics2D;

import java.awt.Color;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TextHeaders extends JLabel{ //I don't want to clutter my code with creating new labels for everything so yeah

    enum Header{
        HEADER1,
        HEADER2,
        TEXT
    }

    public void setUp(Header type){
        switch(type){
            case HEADER1:
                this.setFont(new Font("Serif", Font.BOLD, 18));
                break;
            case HEADER2:
                this.setFont(new Font("Serif", Font.PLAIN, 14));
                break;
            case TEXT:
                this.setFont(new Font("Serif", Font.PLAIN, 12));
                break;
            default:

        }

    }

    TextHeaders(String text, TextHeaders.Header type){
        super(text);
        setUp(type);
    }

    TextHeaders(){
        super("Default text");
        setUp(Header.TEXT);
    }

    @Override
    protected void paintComponent(Graphics g) { //Always draw labels with Anti-aliasing so it looks nice :)
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        super.paintComponent(g2);
    }

}