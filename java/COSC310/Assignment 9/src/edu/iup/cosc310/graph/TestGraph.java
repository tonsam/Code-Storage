package edu.iup.cosc310.graph;

import java.util.Iterator;
import java.util.List;

/**
 * Test program to test given graph cases.
 * 
 * @author David Kornish
 *
 */
public class TestGraph {

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
		cities.addEdge("Detroit", "Pittsburgh", 400);

		for (Iterator<String> iter = cities.adjacentTo("Columbus"); iter.hasNext();) {
			String city = iter.next();
			Integer distance = cities.getWeight("Columbus", city);
			System.out.printf("%s %d\n", city, distance);
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
			for (int i = cityPath3.size()-1; i >= 0; i--) {
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
			for (int i = cityPath3.size()-1; i >= 0; i--) {
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
	}
}
