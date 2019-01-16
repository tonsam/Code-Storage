package edu.iup.cosc310.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Test program to test given graph cases.
 * 
 * @author David Kornish
 *
 */
public class TestGraph2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
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

		ArrayList<Road> roads = new ArrayList<Road>();
		roads.add(new Road("Chicago", "Ann Arbor", 25, 260));
		roads.add(new Road("Chicago", "Fort Wayne", 80, 148));
		roads.add(new Road("Chicago", "Indianapolis", 25, 180));
		roads.add(new Road("Indianapolis", "Fort Wayne", 80, 120));
		roads.add(new Road("Detroit", "Ann Arbor", 25, 50));
		roads.add(new Road("Toledo", "Ann Arbor", 25, 40));
		roads.add(new Road("Detroit", "Toledo", 25, 60));
		roads.add(new Road("Cleveland", "Toledo", 25, 120));
		roads.add(new Road("Columbus", "Toledo", 25, 155));
		roads.add(new Road("Indianapolis", "Columbus", 80, 180));
		roads.add(new Road("Cleveland", "Columbus", 80, 150));
		roads.add(new Road("Cleveland", "Pittsburgh", 80, 130));
		roads.add(new Road("Columbus", "Pittsburgh", 25, 180));
		roads.add(new Road("Philadelphia", "Pittsburgh", 25, 320));
		roads.add(new Road("Detroit", "Pittsburgh", 25, 400));

		// add roads to cities as edges, with useTime as false initially
		boolean useTime = false;

		for (Road r : roads) {
			cities.addEdge(r, useTime);
		}

		System.out.println();

		// breadth first search from Philadelphia to Detroit
		List<String> cityPath = cities.breadthFirstSearch("Philadelphia", "Detroit");

		if (cityPath == null) {
			System.out.println("Breadth First: No path found");
		} else {
			System.out.print("Breadth First: Path found:");
			for (String city : cityPath) {
				System.out.print(" " + city);

			}
			System.out.println();
		}

		// breadth first search from Fort Wayne to Toledo
		List<String> cityPath2 = cities.breadthFirstSearch("Fort Wayne", "Toledo");

		if (cityPath2 == null) {
			System.out.println("Breadth First: No path found");
		} else {
			System.out.print("Breadth First: Path found:");
			for (String city : cityPath2) {
				System.out.print(" " + city);

			}
			System.out.println();
		}

		System.out.println();

		// depth first search from Philadelphia to Detroit
		List<String> cityPath3 = cities.depthFirstSearch("Philadelphia", "Detroit");

		if (cityPath3 == null) {
			System.out.println("Depth First: No path found");
		} else {
			System.out.print("Depth First: Path found:");
			for (int i = cityPath3.size() - 1; i >= 0; i--) {
				// depthFirst returns path in reverse, so print starting from end
				System.out.print(" " + cityPath3.get(i));
			}
			System.out.println();
		}

		// depth first search from Fort Wayne to Toledo
		List<String> cityPath4 = cities.depthFirstSearch("Fort Wayne", "Toledo");

		if (cityPath4 == null) {
			System.out.println("Depth First: No path found");
		} else {
			System.out.print("Depth First: Path found:");
			for (int i = cityPath3.size() - 1; i >= 0; i--) {
				// depthFirst returns path in reverse, so print starting from end
				System.out.print(" " + cityPath4.get(i));
			}
			System.out.println();
		}

		System.out.println();

		// best search from Philadelphia to Detroit
		List<String> cityPath5 = cities.bestSearch("Philadelphia", "Detroit", cities.getCoster());

		if (cityPath5 == null) {
			System.out.println("Best Search: No path found");
		} else {
			System.out.print("Best Search: Path found:");
			for (String city : cityPath5) {
				System.out.print(" " + city);

			}
			System.out.println();
		}

		// best search from Fort Wayne to Toledo
		List<String> cityPath6 = cities.bestSearch("Fort Wayne", "Toledo", cities.getCoster());

		if (cityPath6 == null) {
			System.out.println("Best Search: No path found");
		} else {
			System.out.print("Best Search: Path found:");
			for (String city : cityPath6) {
				System.out.print(" " + city);

			}
			System.out.println();
		}

		System.out.println();

		// rebuild graph using time as edges
		cities = new ALGraph<String, Integer>(false, true);
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
		
		useTime = true;
		for (Road r : roads) {
			cities.addEdge(r, useTime);
		}

		// best search from Philadelphia to Detroit using time
		List<String> cityPath7 = cities.bestSearch("Philadelphia", "Detroit", cities.getCoster());

		if (cityPath5 == null) {
			System.out.println("Best Search by Time: No path found");
		} else {
			System.out.print("Best Search by Time: Path found:");
			for (String city : cityPath7) {
				System.out.print(" " + city);

			}
			System.out.println();
		}

		// best search from Fort Wayne to Toledo using time
		List<String> cityPath8 = cities.bestSearch("Fort Wayne", "Toledo", cities.getCoster());

		if (cityPath6 == null) {
			System.out.println("Best Search by Time: No path found");
		} else {
			System.out.print("Best Search by Time: Path found:");
			for (String city : cityPath8) {
				System.out.print(" " + city);

			}
			System.out.println();
		}

		System.out.println();

	}
}
