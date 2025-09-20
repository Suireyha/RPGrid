import java.util.ArrayList;

public class Inventory{
    ArrayList<Item> contents = new ArrayList<>(); //Will actually contain the items
    
    public void add(Item item){ //Still undecided where items will actually be made may need to make a package of item presets?
        contents.add(item); 
    }

    public void display(){
        //Open new pop frame
        //Display items + stats
        //Click event listeners
    }

    //private void use(){
        //Call from click event listeners in display()
        //Apply item effect
        //Remove item from contents
    //}

    Inventory(){
        //Inventory constructor
    }
}