import java.util.ArrayList;

public class Inventory{
    ArrayList<Item> contents = new ArrayList<>(); //Will actually contain the items
    
    public void add(Item item){ //Still undecided where items will actually be made may need to make a package of item presets?
        contents.add(item); 
    }

    Inventory(){
        //Inventory constructor
    }
}