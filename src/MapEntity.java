import java.awt.Graphics;

interface MapEntity{
    enum mapEntityType{
        PLAYER,
        ENEMIE,
        ITEM,
        EMPTY
    }
    String name = "";
    public void paint(Graphics g); //Children MUST include paint, since otherwise we'd be putting stuff in the game without visualising it to the player
}

