package edu.iup.cosc310.graph;

/**
 * Class for a road object
 * 
 * @author David Kornish
 *
 */
public class Road {
	private int speedLimit;
	private int distance;
	private int time;
	private String to;
	private String from;

	/**
	 * constructor for a road, initializes variables and calculates time
	 * 
	 * @param to
	 * @param from
	 * @param speedLim
	 * @param dist
	 */
	public Road(String to, String from, int speedLim, int dist) {
		this.speedLimit = speedLim;
		this.distance = dist;
		this.time = this.distance / this.speedLimit;
		this.to = to;
		this.from = from;
	}

	/**
	 * Getter method for the distance of a road
	 * 
	 * @return
	 */
	public int getDistance() {
		return this.distance;
	}

	/**
	 * Getter method for the speed limit of a road
	 * 
	 * @return
	 */
	public int getSpeedLimit() {
		return this.speedLimit;
	}

	/**
	 * Getter method for the time to travel a road at speed limit
	 * 
	 * @return
	 */
	public int getTime() {
		return this.time;
	}

	/**
	 * Getter method for the destination
	 * 
	 * @return
	 */
	public String getTo() {
		return to;
	}

	/**
	 * Getter method for the starting point
	 * 
	 * @return
	 */
	public String getFrom() {
		return from;
	}
}
