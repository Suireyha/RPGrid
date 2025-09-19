import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.Point;


/*!!IMPORTANT NOTE FROM MARVIN:
* Wherever we handle mouse position to meet some end, we need to remove the offset I've made
* Take a look at the third and fourth variable in the class gridOffset- that's how the offset is calculated.
* If we change the window size, pixel size, or number of pixels we need to change that variable here and
* in Cell.java.
* You can see me adding the offset in this file in the paint function.
*/
public class Stage {
  Grid grid;
  ArrayList<Actor> actors;
  double gridOffsetX = (1000/2) - (40*20)/2; //Currently the window is Window = 900px, Cells = 35px, #OfCells = 20; 
  double gridOffsetY = gridOffsetX; //We'll use these two variables for drawing the grid at the correct offset to center it. Just make sure to update this if we ever change the number of cells, cell size or window size!!!
  
  public Stage() {
    grid = new Grid();
    actors = new ArrayList<Actor>();
  }

  private <T extends MapEntity> Cell setRandomSpawn(T entity){
    boolean badCell = true; 
    if(entity instanceof Character){ 
      if(entity.getEntityType() == MapEntity.mapEntityType.ENEMIE){ //If it's an enemy, spawn them at the top
        while(badCell){
          int x = (int)(Math.random() * (19 - 0 + 1));
          int y = (int)(Math.random() * (8 - 0 + 1)) + 0; //Enemies spawn between y=0 and y=8
          Cell spawn = grid.cellAtColRow(x, y); //Random within top of map
          if(checkCellEmpty(spawn)){
            System.out.println("X = " + x);
            System.out.println("Y = " + y);
            return spawn;
          }
        }
      }
      else{
      while(badCell){//If it's a player, spawn them on the bottom half of the screen
        //(Math.random() * (max - min + 1)) + min;
        int x = (int)(Math.random() * (19 - 0 + 1)); //We don't care where players spawn along the X axis, only Y is dependant on isPlayer
        int y = (int)(Math.random() * (19 - 12 + 1)) + 12; //Players spawn between y= 20 and 12 
        Cell spawn = grid.cellAtColRow(x, y); //Random within bottom of map
        if(checkCellEmpty(spawn)){ //If the cell is empty, we can spawn our guy there
          System.out.println("X = " + x);
          System.out.println("Y = " + y);
          return spawn;
        }
      }
      }
    }
    else{ //Anything other than players spawn randomly. Anywhere.
      while(badCell){
        int x = (int)(Math.random() * (19 - 0 + 1));
        int y = (int)(Math.random() * (19 - 0 + 1));
        Cell spawn = grid.cellAtColRow(x, y);
        if(checkCellEmpty(spawn)){
          System.out.println("X = " + x);
          System.out.println("Y = " + y);
          return spawn;
        }
      }
    }
    return new Cell(10,10); //Just so compiler shuts the fuck up. If something is here, the above code is bad
  }

  public boolean checkCellEmpty(Cell cell){
    //Checks to see if cell is empty- if it has something in it return false
    return (cell.contentsChar == null && cell.contentsItem == null);
  }

  public void addCharacter(Character charPreset){ //Adds a character to the grid
    charPreset.loc = setRandomSpawn(charPreset);
    actors.add(charPreset);
    charPreset.setCellContentsToThisInstance();
    charPreset.draw();
  }

  public void addItem(Item itemPreset){ //Adds an item to the grid
    itemPreset.loc = setRandomSpawn(itemPreset);
    itemPreset.setCellContentsToThisInstance();
    itemPreset.draw();
  }

  public void paint(Graphics g, Point mouseLoc) {
    Graphics2D g2d = (Graphics2D) g; //Casting the graphics object to graphics2d so I can offset everything
    g2d.translate(gridOffsetX, gridOffsetY); //Offset everything
    grid.paint(g, mouseLoc);
    for(int i = 0; i < actors.size(); i++){
      actors.get(i).paint(g2d); //I changed this to a g2d
    }
  }
}
