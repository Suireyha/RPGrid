@FunctionalInterface
public interface Behaviour{ //We'll use lambdas to dictate enemy AI with this interface
    void execute(Character evoker, MapEntity thing); //Change what this does with lambdas in Character.java (or elsewhere idk)
}