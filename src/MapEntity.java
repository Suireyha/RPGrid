import java.awt.Graphics;
import java.awt.Color;

interface MapEntity{
    public enum mapEntityType{
        PLAYER,
        ENEMIE,
        ITEM,
        EMPTY
    }

    public void draw();
    public void setLocation(Cell location);
    public void addItem(Item item); //Actually only needed for Character
    public Cell getCurrentCell();
    public int[] getStats();
    public String getDescription();
    public String getName();
    public Color getNameTextCol();
    public void displayWin(); //Children must implement a display window for right clicks
    public void setCellContentsToThisInstance();
    public mapEntityType getEntityType(); //Children must implement this. Mainly for setRandomSpawn() so i can make entityType non-static
    public void paint(Graphics g); //Children MUST include paint, since otherwise we'd be putting stuff in the game without visualising it to the player
}

