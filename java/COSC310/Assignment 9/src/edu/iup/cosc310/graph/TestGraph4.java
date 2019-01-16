package edu.iup.cosc310.graph;

import java.util.Iterator;
import java.util.List;

/**
 * Test program to test given graph cases.
 * 
 * @author David Kornish
 *
 */
public class TestGraph4 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		City c1 = new City("Pittsburgh", 360, 50);
		City c2 = new City("Columbus", 240, 20);
		City c3 = new City("Indianapolis", 60, 0);
		City c4 = new City("Fort Wayne", 110, 80);
		City c5 = new City("Chicago", 0, 126);
		City c6 = new City("Ann Arbor", 185, 160);
		City c7 = new City("Detroit", 225, 160);
		City c8 = new City("Toledo", 205, 110);
		City c9 = new City("Cleveland", 290, 100);
		
		
		ALGraph<String, Integer> cities = new ALGraph<String, Integer>(false, true);
		cities.addVertex("Chicago");
		cities.addVertex("Ann Arbor");
		cities.addVertex("Detroit");
		cities.addVertex("Toledo");
		cities.addVertex("Cleveland");
		cities.addVertex("Pittsburgh");
		cities.addVertex("Philadelphia");
		cities.addVertex("Columbus");
		cities.addVertex("Indianapolis");
		cities.addVertex("Fort Wayne");
		
		cities.addEdge("Chicago", "Ann Arbor", 260);
		cities.addEdge("Chicago", "Fort Wayne", 148);
		cities.addEdge("Chicago", "Indianapolis", 180);
		cities.addEdge("Indianapolis", "Fort Wayne", 120);
		cities.addEdge("Detroit", "Ann Arbor", 50);
		cities.addEdge("Toledo", "Ann Arbor", 40);
		cities.addEdge("Detroit", "Toledo", 60);
		cities.addEdge("Cleveland", "Toledo", 120);
		cities.addEdge("Columbus", "Toledo", 155);
		cities.addEdge("Indianapolis", "Columbus", 180);
		cities.addEdge("Cleveland", "Columbus", 150);
		cities.addEdge("Cleveland", "Pittsburgh", 130);
		cities.addEdge("Columbus", "Pittsburgh", 180);
		cities.addEdge("Philadelphia", "Pittsburgh", 320);

		for (Iterator<String> iter = cities.adjacentTo("Columbus"); iter.hasNext();) {
			String city = iter.next();
			Integer distance = cities.getWeight("Columbus", city);
			System.out.printf("%s %d\n", city, distance);
		}

		System.out.println();

		// A-Star search from Toledo to Fort Wayne
		List<String> cityPath = cities.aStarSearch("Toledo", "Fort Wayne");

		if (cityPath == null) {
			System.out.println("A Star: No path found");
		} else {
			System.out.print("A Star: Path found:");
			for (String city : cityPath) {
				System.out.print(" " + city);

			}
			System.out.println();
		}

		
	}
}
