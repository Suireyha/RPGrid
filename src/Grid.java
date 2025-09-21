import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Point;

import java.util.Optional;

import java.awt.Color;
import java.awt.Font;
/*!!IMPORTANT NOTE FROM MARVIN:
* Wherever we handle mouse position to meet some end, we need to remove the offset I've made
* Take a look at the second variable in the class gridOffset- that's how the offset is calculated.
* If we change the window size, pixel size, or number of pixels we need to change that variable here and
* in Stage.java.
* To remove the offset so we can get the actual mousePos, take a look at what's happening in paint();
*/
public class Grid {
  Cell[][] cells = new Cell[20][20];

  Cell selectedCell = null;
  MapEntity selectedEntity;

  double gridOffset = (1000/2) - (40*20)/2; //Currently the window is Window = 900px, Cells = 35px, #OfCells = 20;
  Color lime = new Color(0, 255, 50);
  Color alert = new Color(232, 23, 72);
  Color heroBlue = new Color(137, 207, 240);

  String message = "Right click characters/items to display, left click to move!";
  Color messageCol = lime; //Green by default


  public Grid() {
    int idCounter = 0;
    for(int i=0; i<cells.length; i++) {
      for(int j=0; j<cells[i].length; j++) { 
        cells[i][j] = new Cell(10+Cell.size*i, 10+Cell.size*j);
        cells[i][j].id = idCounter;
        idCounter++;
      }
    }
  }

  public void cellLeftClicked(Point mousePos){ //This may genuinely be the least readable method I've ever written. Buckle in and rely on the comments :sob:

    Optional<Cell> activeCell = cellAtPoint(mousePos);
    if (activeCell.isPresent()){
      Cell clickedCell = activeCell.get();

      //Cclicking the same cell that's already selected deselects it
      if(selectedCell == clickedCell) {
        selectedCell.isSelected = false;
        selectedCell = null;
        selectedEntity = null;
        return;
      }
            
      //Clear previous selection
      if(selectedCell != null){
        selectedCell.isSelected = false;
      }

      //A bit of a verbose if statement, but it makes sure not to move a character if there's an entity inside a cell
      if(selectedEntity != null && selectedEntity.getEntityType() == MapEntity.mapEntityType.PLAYER && clickedCell.contentsChar == null && clickedCell.contentsItem == null){
        System.out.println("");
        System.out.println("Distance between cells: " + getCellDistance(selectedEntity.getCurrentCell(), clickedCell));
        System.out.println(selectedEntity.getName() + "'s Initaiative:" + selectedEntity.getStats()[3]);
        if(getCellDistance(selectedEntity.getCurrentCell(), clickedCell) < selectedEntity.getStats()[3]){
          selectedEntity.getCurrentCell().contentsChar = null;
          clickedCell.contentsChar = (Character)selectedEntity;
          selectedEntity.setLocation(clickedCell);
          messageCol = lime;
          message = selectedEntity.getName() + " moved to x=" + getCellColRow(clickedCell)[0] + " y=" + getCellColRow(clickedCell)[1];
        }
        else{
          //TextHeaders errorMsg = new TextHeaders(selectedEntity.getName() + " can only move " + selectedEntity.getStats()[3] + " spaces per turn!", TextHeaders.Header.HEADER2, Color.RED);
          messageCol = alert;
          message = selectedEntity.getName() + " can only move " + selectedEntity.getStats()[3] + " spaces per turn!";
        }

        //After moving, deselect the entity and the cell
        selectedEntity = null;
        selectedCell = null;
        clickedCell.isSelected = false; //Deslect the cell after moving
      }
            
      //If the character can't go there, select the contents of the cell instead or deselect
      else {
        selectedCell = clickedCell;
        clickedCell.isSelected = true;
        if(clickedCell.contentsChar != null && selectedEntity instanceof Character){

          if(getCellDistance(selectedEntity.getCurrentCell(), clickedCell) <= ((Character)selectedEntity).equipedWeapon.range){
            selectedEntity.attack(clickedCell.contentsChar, this); //Attack!!
          }
          else{
            messageCol = alert;
            message = selectedEntity.getName() + "'s range is " + ((Character)selectedEntity).equipedWeapon.range + "!";
          }
          selectedEntity = null;
          selectedCell = null;
          clickedCell.isSelected = false; //Deslect the cell after moving
        }

        else if(clickedCell.contentsItem != null && selectedEntity instanceof Character){
          if(getCellDistance(selectedEntity.getCurrentCell(), clickedCell) <= 2){ //If a character is in range of an item, pick it up and add it to their inventory
            messageCol = heroBlue;
            message = selectedEntity.getName() + " picked up " + clickedCell.contentsItem.getName() + "!";
            selectedEntity.addItem(clickedCell.contentsItem); //Add item to inventory
            selectedEntity = null; //Deselect
          }
          else{
            messageCol = alert;
            message = selectedEntity.getName() + " is out of range of " + clickedCell.contentsItem.getName() + "!";
            selectedEntity = null;
          }
          
        }

        else if(clickedCell.contentsChar != null){
          selectedEntity = clickedCell.contentsChar;
        }

        else {
          selectedEntity = null;
        }
      }
    }
  }

  public void cellRightClicked(Point mousePos){
    Optional<Cell> activeCell = cellAtPoint(mousePos);
    if (activeCell.isPresent()){
      if(activeCell.get().contentsChar != null){
        activeCell.get().contentsChar.displayWin();
      }
      else if(activeCell.get().contentsItem != null){
        activeCell.get().contentsItem.displayWin();
      }
      if(selectedCell != null){
        selectedCell.isSelected = false;
        selectedCell = null;
      }
    }
  }

  public void paint(Graphics g, Point mousePos) {
    // If mousepos is null, make it = -1000, 1000 so we don't get null pointer exceptions. FOR WEEK 6 WE CAN TOTALLY MAKE THIS A TRY{}CATCH{} INSTEAD!!!
    mousePos = (mousePos != null) ? mousePos : new Point(100, 100);

    Point offsetMousePos = new Point(mousePos); //This is the ACTUAL mouse position obtained by removing the offset.
    offsetMousePos.x -= gridOffset;
    offsetMousePos.y -= gridOffset;
    for(int i=0; i<cells.length; i++) {
      for(int j=0; j<cells[i].length; j++) {
        cells[i][j].paint(g, mousePos);
        if(cells[i][j].contentsChar != null){
          cells[i][j].contentsChar.draw();
        }
      }
    }
    //Optional<Cell> cellFound = cellAtPoint(mousePos);
    //String message = "Not in a cell";
    //if(cellFound.isPresent()){
      //message = "Column: " + ((cellFound.get().x-10)/40) + " Row: " + ((cellFound.get().y-10)/40);
    //}
    
    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    g2d.setColor(messageCol);
    g2d.setFont(new Font("Tahoma", Font.BOLD, 18)); //Changes the font to Tahoma, bold, 18px
    g2d.drawString(message, 400 - (5*message.length()), 1); //Draws the string further to the left the longer it is

  }

  public Cell cellAtColRow(int c, int r) {
    return cells[c][r];
  }

  public int[] getCellColRow(Cell cell){ //Gets the coordinates of a cell
    for(int x = 0; x < cells.length; x++){
      for(int y = 0; y < cells[x].length; y++){
        if(cells[x][y] == cell){
          int[] rowCol = {x, y};
          return rowCol;
        }
      }
    }
    System.out.println("ERROR!! getCellColRow() in Grid.java couldn't find passed cell!");
    int[] rowCol = {0, 0};
    return rowCol;
  }

  public int getCellDistance(Cell a, Cell b){ //Gets the distance between cells

    int absoluteX; //Subtract the lower number from the higher number
    if(getCellColRow(a)[0] > getCellColRow(b)[0]) absoluteX = getCellColRow(a)[0] - getCellColRow(b)[0];
    else absoluteX = getCellColRow(b)[0] - getCellColRow(a)[0];

    int absoluteY;
    if(getCellColRow(a)[1] > getCellColRow(b)[1]) absoluteY = getCellColRow(a)[1] - getCellColRow(b)[1];
    else absoluteY = getCellColRow(b)[1] - getCellColRow(a)[1];

    return absoluteX + absoluteY;
  }

  public Optional<Cell> cellAtPoint(Point p){
    Point offsetP = new Point(p); //This is the ACTUAL mouse position obtained by removing the offset.
    offsetP.x -= gridOffset;
    offsetP.y -= gridOffset;
    Optional<Cell> location = Optional.empty();
    if(offsetP != null){
      if(offsetP.getX() >= 10 && offsetP.getX() <= 808){
        if(offsetP.getY() >= 10 && offsetP.getY() <= 808){
          int cellX = (int)((offsetP.getX()-10) / 40);
          int cellY = (int)((offsetP.getY()-10) / 40);
          location = Optional.of(cellAtColRow(cellX, cellY));
        }
      }
    }
    return location;
  }

  public void changeMessage(String text, Color col){
    messageCol = col;
    message = text;
  }

}
