package edu.iup.cosc310.graph;

import java.util.Iterator;
import java.util.List;

/**
 * Test program to test given graph cases.
 * 
 * @author David Kornish
 *
 */
public class TestGraph3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ALGraph<String, Integer> graph = new ALGraph<String, Integer>(true, true);
		
		graph.addVertex("0");
		graph.addVertex("1");
		graph.addVertex("2");
		graph.addVertex("3");
		graph.addVertex("4");
		
		graph.addEdge("0", "1", 10);
		graph.addEdge("0", "3", 30);
		graph.addEdge("0", "4", 100);
		graph.addEdge("1", "2", 50);
		graph.addEdge("2", "4", 10);
		graph.addEdge("3", "2", 20);
		graph.addEdge("3", "4", 60);
		
		
		// breadth first search from 0 to 4
				List<String> path = graph.breadthFirstSearch("0", "4");

				if (path == null) {
					System.out.println("Breadth First: No path found");
				} else {
					System.out.print("Breadth First: Path found:");
					for (String city : path) {
						System.out.print(" " + city);

					}
					System.out.println();
				}

				// breadth first search from 1 to 0
				List<String> path2 = graph.breadthFirstSearch("1", "0");

				if (path2 == null) {
					System.out.println("Breadth First: No path found");
				} else {
					System.out.print("Breadth First: Path found:");
					for (String city : path2) {
						System.out.print(" " + city);

					}
					System.out.println();
				}

				System.out.println();

				// depth first search from 0 to 4
				List<String> path3 = graph.depthFirstSearch("0", "4");

				if (path3 == null) {
					System.out.println("Depth First: No path found");
				} else {
					System.out.print("Depth First: Path found:");
					for (int i = path3.size()-1; i >= 0; i--) {
						// depthFirst returns path in reverse, so print starting from end
						System.out.print(" " + path3.get(i));
					}
					System.out.println();
				}

				// depth first search from 1 to 0
				List<String> path4 = graph.depthFirstSearch("1", "0");

				if (path4 == null) {
					System.out.println("Depth First: No path found");
				} else {
					System.out.print("Depth First: Path found:");
					for (int i = path3.size()-1; i >= 0; i--) {
						// depthFirst returns path in reverse, so print starting from end
						System.out.print(" " + path4.get(i));
					}
					System.out.println();
				}

				System.out.println();

				// best search from 0 to 4
				List<String> path5 = graph.bestSearch("0", "4", graph.getCoster());

				if (path5 == null) {
					System.out.println("Best Search: No path found");
				} else {
					System.out.print("Best Search: Path found:");
					for (String city : path5) {
						System.out.print(" " + city);

					}
					System.out.println();
				}

				// best search from 1 to 0
				List<String> path6 = graph.bestSearch("1", "0", graph.getCoster());

				if (path6 == null) {
					System.out.println("Best Search: No path found");
				} else {
					System.out.print("Best Search: Path found:");
					for (String city : path6) {
						System.out.print(" " + city);

					}
					System.out.println();
				}

				System.out.println();
	}
}
