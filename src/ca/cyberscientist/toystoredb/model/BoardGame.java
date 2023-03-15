/**
 * 
 */
package ca.cyberscientist.toystoredb.model;

/**
 * Class dealing with the subclass BoardGame
 * @author Bryce Carson
 *
 */
public class BoardGame extends Toy {

	private String designer;
	private int minNumOfPlayers;
	private int maxNumOfPlayers;

	/**
	 * The text description of the designer(s) who created the board game.
	 * 
	 * @return the text description of the designer(s).
	 */
	public String getDesigner() {
		return this.designer;
	}

	/**
	 * Acquire the number of players for the board game, at maximum.
	 * 
	 * @return the greatest number of people who can play the game.
	 */
	public int getMaxNumPlayers() {
		return this.maxNumOfPlayers;
	}

	/**
	 * Acquire the number of players for required for the board game, at minimum.
	 * 
	 * @return the least number of people who can play the game.
	 */
	public int getMinNumPlayers() {
		return this.minNumOfPlayers;
	}

	/**
	 * @param serialNumber   The serial number to associate with the board game.
	 * @param name           The name to associate with the board game.
	 * @param brand          The brand name (manufacturer) to associate with the
	 *                       board game.
	 * @param price          The cost of the board game in dollars and cents.
	 * @param availableCount The number of this board game in inventory.
	 * @param appropriateAge The minimum recommended age for this board game.
	 * @param numOfPlayers   The range of players that the board game can seat.
	 * @param designer       The text description of the designer(s) of the board
	 *                       game.
	 */
	public BoardGame(String serialNumber, String name, String brand, double price, int availableCount,
			int appropriateAge, String numOfPlayers, String designer) {
		super(serialNumber, name, brand, price, availableCount, appropriateAge);

		String[] numOfPlayersStrArray = numOfPlayers.split("-", 2); // "Parse-out" no more than two strings.
		this.minNumOfPlayers = Integer.parseInt(numOfPlayersStrArray[0]);
		this.maxNumOfPlayers = Integer.parseInt(numOfPlayersStrArray[1]);

		this.designer = designer;
	}

	/**
	 * Create a board game from a string array. Wraps the more complex constructor.
	 * 
	 * @param fields The record from the on-disk "database" to create a board game
	 *               from.
	 */
	public BoardGame(String[] fields) {
		this(fields[0], fields[1], fields[2], Double.parseDouble(fields[3]), Integer.parseInt(fields[4]),
				Integer.parseInt(fields[5]), fields[6], fields[7]);
	}

	/**
	 * @return the string format of the puzzle suitable for logic purposes
	 */
	@Override
	public String toString() {
		return super.toString() + getDesigner() + ";" + getMinNumPlayers() + ";" + getMaxNumPlayers();
	}

	/**
	 * @return the string format of the puzzle suitable for on-disk storage.
	 */
	@Override
	public String format() {
		return super.toString() + getMinNumPlayers() + "-" + getMaxNumPlayers() + ";" + getDesigner();
	}
}
