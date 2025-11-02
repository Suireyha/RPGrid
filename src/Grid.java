import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Point;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import java.awt.Color;
import java.awt.Font;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*!!IMPORTANT NOTE FROM MARVIN:
* Wherever we handle mouse position to meet some end, we need to remove the offset I've made
* Take a look at the second variable in the class gridOffset- that's how the offset is calculated.
* If we change the window size, pixel size, or number of pixels we need to change that variable here and
* in Stage.java.
* To remove the offset so we can get the actual mousePos, take a look at what's happening in paint();
*/
public class Grid {
  Cell[][] cells = new Cell[20][20];

  Graphics lastGraphics;

  Main mainInstance;

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

    if(!mainInstance.getInTurn().player){ //If it's not a player then:
      messageCol = alert;
      message = "It is " + mainInstance.getInTurn().getName() + "'s turn!!";
      mainInstance.processTurn();
    }

    else{
      Optional<Cell> activeCell = cellAtPoint(mousePos);
      boolean turnSuccess = false;
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
          //System.out.println("");
          //System.out.println("Distance between cells: " + getCellDistance(selectedEntity.getCurrentCell(), clickedCell));
          //System.out.println(selectedEntity.getName() + "'s Initaiative:" + selectedEntity.getStats()[3]);
          if(getCellDistance(selectedEntity.getCurrentCell(), clickedCell) < selectedEntity.getStats()[3]){
            selectedEntity.getCurrentCell().contentsChar = null;
            clickedCell.contentsChar = (Character)selectedEntity;
            selectedEntity.setLocation(clickedCell);
            messageCol = lime;
            message = selectedEntity.getName() + " moved to x=" + getCellColRow(clickedCell)[0] + " y=" + getCellColRow(clickedCell)[1];

            turnSuccess = true;
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

              turnSuccess = true;
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

              turnSuccess = true;
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

      if(turnSuccess){
        paint(lastGraphics, null);
        mainInstance.cycleQueue(); //THIS NEEDS TO GO AT THE END OF A VALID PLAYER TURN!!!
        mainInstance.processTurn();  
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

    lastGraphics = g;
    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    g2d.setColor(messageCol);
    g2d.setFont(new Font("Tahoma", Font.BOLD, 18)); //Changes the font to Tahoma, bold, 18px
    g2d.drawString(message, 400 - (5*message.length()), 1); //Draws the string further to the left the longer it is

    //DISCLOSURE!!! THE FOLLOWING IF STATEMENT WRITTEN BY GENERATIVE AI (Anthropic's Claude.ai)
    //Seeing as it's all unrelated to the assessment task and only helps me get through some display grunt work I don't imagine there will be any issues.
    //I will dispute this if I am marked down for it :fire: 
    if(mainInstance != null && mainInstance.getInTurn() != null)
    {
      Character currentTurn = mainInstance.getInTurn();
      
      // Save the original stroke
      java.awt.Stroke originalStroke = g2d.getStroke();
      
      // Draw turn indicator box below the grid - smaller version
      g2d.setColor(new Color(20, 20, 20, 200));
      g2d.fillRoundRect(280, 820, 240, 60, 12, 12);
      
      // Draw border
      g2d.setColor(currentTurn.player ? heroBlue : alert);
      g2d.setStroke(new java.awt.BasicStroke(2));
      g2d.drawRoundRect(280, 820, 240, 60, 12, 12);
      
      // Restore original stroke
      g2d.setStroke(originalStroke);
      
      // Draw character name
      g2d.setFont(new Font("Tahoma", Font.BOLD, 18));
      g2d.setColor(currentTurn.player ? heroBlue : alert);
      String turnText = currentTurn.player ? "YOUR TURN" : "ENEMY TURN";
      g2d.drawString(turnText, 320, 840);
      
      // Draw character details
      g2d.setFont(new Font("Tahoma", Font.PLAIN, 13));
      g2d.setColor(Color.WHITE);
      g2d.drawString(currentTurn.name + " the " + currentTurn.roleAsText, 290, 858);
      
      // Draw HP bar
      g2d.setFont(new Font("Tahoma", Font.PLAIN, 11));
      g2d.drawString("HP:", 290, 873);
      
      // HP bar background
      g2d.setColor(new Color(60, 60, 60));
      g2d.fillRect(315, 863, 190, 12);
      
      // HP bar fill
      double hpPercent = currentTurn.health / currentTurn.maxHealth;
      Color hpColor;
      if(hpPercent > 0.6) hpColor = new Color(0, 255, 50);
      else if(hpPercent > 0.3) hpColor = new Color(255, 200, 0);
      else hpColor = new Color(255, 50, 50);
      
      g2d.setColor(hpColor);
      g2d.fillRect(315, 863, (int)(190 * hpPercent), 12);
      
      // HP text
      g2d.setColor(Color.WHITE);
      String hpText = String.format("%.0f / %.0f", currentTurn.health, currentTurn.maxHealth);
      g2d.drawString(hpText, 355, 873);
    }

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


  //Enemy AI calculation stuff- LOADS OF STREAMS IN THIS SECTION!! :D

  public List<Cell> getCellsInRange(Cell origin, int range) { //Range will usually be initiative
    return IntStream.range(0, cells.length)
        .boxed()
        .flatMap(x -> IntStream.range(0, cells[x].length)
            .mapToObj(y -> cells[x][y]))
        .filter(cell -> getCellDistance(origin, cell) <= range && getCellDistance(origin, cell) > 0) //Filter for the ones actually in range but exclude the cell the character is already in
        .collect(Collectors.toList());
  }

  //Returns a list of valid moves character can make (all inclusive)
  public List<Cell> getValidMoves(Character character) {
      return getCellsInRange(character.loc, character.initiative)
          .stream()
          .filter(cell -> cell.contentsChar == null && cell.contentsItem == null)
          .collect(Collectors.toList());
  }

  //Get all attackable targets (players within weapon reach)
  public List<Character> getAttackableTargets(Character attacker) {
      return getCellsInRange(attacker.loc, attacker.equipedWeapon.range)
          .stream()
          .filter(cell -> cell.contentsChar != null) //Filter for cells with characters in them
          .map(cell -> cell.contentsChar)
          .filter(target -> target.player) //Only target players (so obv this function can't be used for Character logic)
          .collect(Collectors.toList());
  }

  //Get the closest player to a given character
  public Optional<Character> getClosestPlayer(Character enemy) {
      return IntStream.range(0, cells.length)
          .boxed()
          .flatMap(x -> IntStream.range(0, cells[x].length)
              .mapToObj(y -> cells[x][y]))
          .filter(cell -> cell.contentsChar != null && cell.contentsChar.player)
          .map(cell -> cell.contentsChar)
          .min(Comparator.comparingInt(player -> getCellDistance(enemy.loc, player.loc)));
  }

  //Find the best cell to move towards a target
  public Optional<Cell> getBestMoveTowards(Character mover, Cell target) {
      return getValidMoves(mover)
          .stream()
          .min(Comparator.comparingInt(cell -> getCellDistance(cell, target)));
  }

  public void callRemoveFromTQ(Character deadCharacter) {//This gets called from Character, and Grid passes the call up to Main. I know how fucked this is :sob:
      mainInstance.removeFromTQ(deadCharacter);
  }

}
