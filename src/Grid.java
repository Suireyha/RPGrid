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

  String message = "RPGrid by Marvin Kelly";

  double gridOffset = (1000/2) - (40*20)/2; //Currently the window is Window = 900px, Cells = 35px, #OfCells = 20;
  Color lime = new Color(0, 255, 50);

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
          System.out.println(selectedEntity.getName() + " moved to x=" + getCellColRow(clickedCell)[0] + " y=" + getCellColRow(clickedCell)[1]);
        }
        else{
          //TextHeaders errorMsg = new TextHeaders(selectedEntity.getName() + " can only move " + selectedEntity.getStats()[3] + " spaces per turn!", TextHeaders.Header.HEADER2, Color.RED);
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
        if(clickedCell.contentsChar != null){
            selectedEntity = clickedCell.contentsChar;
        }
        else if(clickedCell.contentsItem != null){
            selectedEntity = clickedCell.contentsItem;
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
    Optional<Cell> cellFound = cellAtPoint(mousePos);
    //String message = "Not in a cell";
    //if(cellFound.isPresent()){
      //message = "Column: " + ((cellFound.get().x-10)/40) + " Row: " + ((cellFound.get().y-10)/40);
    //}
    
    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    g2d.setColor(lime);
    g2d.setFont(new Font("Arial", Font.BOLD, 17)); //Changes the font to Arial, bold, 15px
    g2d.drawString(message, 310, 1);
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
    if(getCellColRow(a)[0] > getCellColRow(b)[0]) absoluteX = getCellColRow(b)[0] - getCellColRow(a)[0];
    else absoluteX = getCellColRow(a)[0] - getCellColRow(b)[0];

    int absoluteY;
    if(getCellColRow(a)[1] > getCellColRow(b)[1]) absoluteY = getCellColRow(b)[1] - getCellColRow(a)[1];
    else absoluteY = getCellColRow(a)[1] - getCellColRow(b)[1];

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
}
