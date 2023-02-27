package ca.cyberscientist.toystoredb.view;

public class RecommendationMenu extends View {
    public RecommendationMenu() {
        final String[] VALID_TOY_TYPES = {"A", "B", "F", "P", "Animal", "Board Game", "Figure", "Puzzle"};

        boolean searchAgainFlag = true;
        while(searchAgainFlag) {
            System.out.println("You are in the recommendation menu.\n");

            System.out.println("\tA) Age");
            System.out.println("\tT) Type (animal, board game, figure, or puzzle)");
            System.out.println("\tP) Price range ($0.00 - $XX.XX)");
            System.out.println("\tS) Search using criteria");
            System.out.println("\tC) Cancel search\n");

            char userInput = getValidatedCharInput("Enter your choice: ", {'S', 'N', 'T', 'Q'});

            switch(userInput) {
                case 'A': getValidatedIntInput("Enter an age (minimum age is zero): "); break;
                case 'T': getValidatedToyTypeInput("Enter a toy type: ", VALID_TOY_TYPES); break;
                case 'P': getValidatedPriceRangeInput("Enter a price range (0.00 XX.XX): "); break;
                case 'S': getRecommendations()
                    break;
                case 'C':
                    break;
            }
        }

    }
}