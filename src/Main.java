import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.SwingUtilities;

import java.util.*;
import java.util.stream.Collectors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import java.lang.Thread;


public class Main extends JFrame {
  int winWidth = 1000;
  int winHeight = 1000;
  Stage stage = new Stage();

  public ArrayList<Character> turnQueue = new ArrayList<>(); //We'll store all characters (enemies and players) in here to manage turns

  public static void main(String[] args) throws Exception {

    PopUp<?> loadingScreen = new PopUp<>("LOADING", Color.CYAN, popup -> {
      popup.makeNewContentRow("         (This may take a while)"); //Lambda for Consumer.accept (However you want to build the loading screen goes in this function)
    });

    Client downstream = new Client(); //Open the HTTP client thingy to get the weather :fire:


    System.out.println("Test 1");
    //Print the weather data to console
    List<String> data = Client.getWeatherData();
    for (String line : data) {
        System.out.println("Weather info: " + line);
    }

    loadingScreen.dispose(); //Delete once the weather data has been retrieved

    Main window = new Main();
    window.setBounds(350, 50, 1000, 1000); //Window is being drawn at x=350 y=50, dimensions are 1000^2
    window.setBackground(new Color(47, 48, 49));


    window.run();
  }

  class Canvas extends JPanel implements MouseListener{ //!! NOte to self, put this in justifications.md
    
    public Canvas() {
      setPreferredSize(new Dimension(winWidth, winHeight));
      addMouseListener(this); //Actually adds the mouse listeners to the canvas instance
    }

    @Override
    public void mouseClicked(MouseEvent e){ //Mouse clicked event listener inside the grid(IMPORTANT!!)
      if(SwingUtilities.isLeftMouseButton(e)){
        //Left click
        stage.grid.cellLeftClicked(e.getPoint());
      }
      if(SwingUtilities.isRightMouseButton(e)){
        //Right click
        stage.grid.cellRightClicked(e.getPoint());
      }
      
    }

    @Override
    public void paint(Graphics g) {
      stage.paint(g, getMousePosition());
    }

    //I have to implement these, event as empty methods, as per the interface or it'll give me a syntax error :(
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
  }

  private Main() {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Canvas canvas = new Canvas();
    this.setContentPane(canvas);
    this.pack();
    this.setVisible(true);

    //Create items
    Weapon sword = new Weapon("Iron Sword", "A simple sword. Made from Iron.", "+2 STR", 2, 0, 0, 0, Weapon.Type.SWORD);
    Armour leatherVest = new Armour("Leather Vest", "A vest crafted from cow hide.", "+1 CONS, +1 INIT", 0, 0, 1, 1);
    Potion healthPotion = new Potion("Health Potion", "A jar of red liquid- looks tasty!", "+10 HP", 0, 0, 0, 0, 10f);

    stage.addItem(sword);
    stage.addItem(leatherVest);
    stage.addItem(healthPotion);
    
    //Create characters
    Character p1 = new Character("John Snow", Character.RoleType.FIGHTER, Character.RaceType.HUMAN, true);
    Character p2 = new Character("Glimbo", Character.RoleType.BARBARIAN, Character.RaceType.ORC, true);
    Character p3 = new Character("Emily", Character.RoleType.RANGER, Character.RaceType.DWARF, true);
    stage.addCharacter(p1);
    stage.addCharacter(p2);
    stage.addCharacter(p3);

    Character en1 = new Character("Wabbajack", Character.RoleType.MAGE, Character.RaceType.ELF, false);
    Character en2 = new Character("Aloy", Character.RoleType.RANGER, Character.RaceType.HUMAN, false);
    Character en3 = new Character("The Hound", Character.RoleType.FIGHTER, Character.RaceType.HUMAN, false);
    stage.addCharacter(en1);
    stage.addCharacter(en2);
    stage.addCharacter(en3);

    turnQueue.add(p1);
    turnQueue.add(p2);
    turnQueue.add(p3);
    turnQueue.add(en1);
    turnQueue.add(en2);
    turnQueue.add(en3);

    //STREAM USED HERE!!!!!!
    turnQueue = turnQueue.stream()
      .sorted(Comparator.comparing((Character x) -> x.initiative).reversed()) //Sorts characters based on their initiative (Highest to lowest)
      .collect(Collectors.toCollection(ArrayList::new)); //All that to make it an Array list.... :sob:
    printQueue();

    //cycleQueue(); //These lines are for testing
    //cycleQueue();
    //printQueue();

  }

  public void run() {
    while(true) {
      repaint();
    }
  }

  public Character getInTurn(){
    return turnQueue.get(0);
  }

  public void cycleQueue() {
    Character temp = turnQueue.get(0); //Save the first one for now
    for (int i = 0; i < turnQueue.size() - 1; i++) {
        turnQueue.set(i, turnQueue.get(i + 1)); //Move everyone closer to the front of the queue (down in index)
    }
    turnQueue.set(turnQueue.size() - 1, temp); //Put the character who just had their turn on the back
    printQueue();
  }

  public void printQueue(){
    turnQueue.forEach(Character -> System.out.println(Character.name + ":\t" + Character.initiative)); //Print the queue
    System.out.println("~~~~~~~");
  }

  public void processTurn() {
    Character current = getInTurn();
    
    // If it's an enemy, automatically cycle after a delay
    if (!current.player) {
      //ENEMY AI WILL GO HERE!!!
      //current.attack();

      try {Thread.sleep(1000);} //Wait a second so that the user can actually see what happened
      catch (Exception e) { System.out.println(e); }

      cycleQueue();
    }
  
    //If it's a player's turn, do nothing. The cycleQueue() call will eventually come from Grid.java
  }


}
