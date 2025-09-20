import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class ContentRow extends JPanel{
    ContentRow(){
        super();
        this.setBackground(new Color(47, 48, 49));
        this.setBorder(new EmptyBorder(0,30,0,15));
        this.setLayout(new BorderLayout());
    }
}