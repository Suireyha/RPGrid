import java.awt.Graphics;

interface MapEntity{
    public enum mapEntityType{
        PLAYER,
        ENEMIE,
        ITEM,
        EMPTY
    }
    public mapEntityType getEntityType(); //Children must implement this. Mainly for setRandomSpawn() so i can make entityType non-static
    public void paint(Graphics g); //Children MUST include paint, since otherwise we'd be putting stuff in the game without visualising it to the player
}

