package edu.iup.cosc310.graph;

/**
 * City object for an A* search
 * 
 * @author David Kornish
 *
 */
public class City {
	private String name;
	private int xCoord;
	private int yCoord;

	/**
	 * Constructor method for a city. Initializes the name and coordinates
	 * 
	 * @param name
	 * @param xCoord
	 * @param yCoord
	 */
	public City(String name, int xCoord, int yCoord) {
		this.name = name;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
	}

	/**
	 * Method to calculate the distance between this city and another city.
	 * 
	 * @param to
	 * @return
	 */
	public double calculateDistance(City to) {
		int deltaX = to.getxCoord() - this.getxCoord();
		int deltaY = to.getyCoord() - this.getyCoord();
		double distance = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
		return distance;
	}

	/**
	 * Getter method for the X coordinate
	 * 
	 * @return
	 */
	public int getxCoord() {
		return xCoord;
	}

	/**
	 * Getter method for the Y coordinate
	 * 
	 * @return
	 */
	public int getyCoord() {
		return yCoord;
	}

}
