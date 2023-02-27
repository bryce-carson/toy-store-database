package ca.cyberscientist.toystoredb.view;

public class MainMenu extends View {
    public MainMenu() {
        boolean mainLoopFlag = true;
        while(mainLoopFlag) {
            System.out.println("You are in the main menu.");
            System.out.println("S) Search and purchase a toy.");
            System.out.println("A) Add a toy to the database.");
            System.out.println("R) Remove a toy from the database.");
            System.out.println("G) Generate a toy suggestion for a customer.");
            System.out.println("Q) Save the database to disk and quit. [End of shift]");

            char userInput = getValidatedCharInput("Enter your choice: ", {'S', 'A', 'R', 'G', 'Q'});

            switch(userInput) {
                case 'S': SearchMenu(); break;
                case 'A': AddToyMenu(); break;
                case 'R': RemoveToyMenu(); break;
                case 'G': RecommendationMenu(); break;
                case 'Q': mainLoopFlag = false; break;
            }
        }
    }
}