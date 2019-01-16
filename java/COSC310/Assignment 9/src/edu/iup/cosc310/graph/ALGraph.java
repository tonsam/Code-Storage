package edu.iup.cosc310.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * An adjacency list implementation for a generic graph.
 * 
 * @author dtsmith, modified by David Kornish
 *
 * @param <V>
 *            data type for the vertices of the graph
 * @param <W>
 *            data type for the weight of edges of a graph. Use Boolean for an
 *            unweighted graph
 */
public class ALGraph<V, W> implements Graph<V, W> {
	private boolean directed;
	private boolean weighted;

	/**
	 * Inner class to represent a vertex.
	 * 
	 * Value attribute is the vertex as seen by the applications using the ALGraph.
	 */
	private class Vertex {
		private V value;
		private Map<V, Edge> adjacencies = new TreeMap<V, Edge>();

		public Vertex(V value) {
			this.value = value;
		}

		public V getValue() {
			return value;
		}

		public void addEdge(Vertex to, W weight) {
			adjacencies.put(to.value, new Edge(this, to, weight));
		}

		public Edge getEdge(V to) {
			return adjacencies.get(to);
		}

		public W getWeight(V to) {
			Edge edge = getEdge(to);

			if (edge == null) {
				return null;
			} else {
				return edge.weight;
			}
		}

		public Iterator<Edge> edges() {
			return adjacencies.values().iterator();
		}
	}

	/**
	 * Inner class to represent an edge.
	 * 
	 * Weight attribute is the weight of the edge as seen by the applications using
	 * the ALGraph.
	 */
	private class Edge {
		private Vertex vertexFrom;
		private Vertex vertexTo;
		private W weight;

		public Edge(Vertex vertexFrom, Vertex vertexTo, W weight) {
			super();
			this.vertexFrom = vertexFrom;
			this.vertexTo = vertexTo;
			this.weight = weight;
		}

		public Vertex getVertexFrom() {
			return vertexFrom;
		}

		public Vertex getVertexTo() {
			return vertexTo;
		}

		public W getWeight() {
			return weight;
		}

		public void setWeight(W weight) {
			this.weight = weight;
		}
	}

	private Map<V, Vertex> verticies = new HashMap<V, Vertex>();

	/**
	 * Constructor
	 * 
	 * @param directed
	 *            - indicates if the graph is directed
	 */
	public ALGraph(boolean directed, boolean weighted) {
		super();
		this.directed = directed;
		this.weighted = weighted;
	}

	/**
	 * Returns true if the graph is directed
	 */
	public boolean isDirected() {
		return directed;
	}

	/**
	 * Returns true if the graph is directed
	 */
	public boolean isWeighted() {
		return weighted;
	}

	/**
	 * Add a vertex to the graph. Value is the the value stored at the vertex.
	 * Values must be unique within the graph.
	 * 
	 * @param value
	 *            the value stored at the vertex
	 */
	public void addVertex(V value) {
		verticies.put(value, new Vertex(value));
	}

	private Vertex getVertex(V value) {
		return verticies.get(value);
	}

	/**
	 * Add an unweighted edge between two vertices. The values stored is each vertex
	 * is used to identify the vertices. If the graph is undirected then two edges
	 * will be added; an edge from the to vertex to the from vertex will also be
	 * added.
	 * 
	 * @param from
	 *            value of the from vertex
	 * @param to
	 *            value of the to vertex
	 */
	public void addEdge(V from, V to) {
		if (isWeighted()) {
			throw new RuntimeException("Weight must be provided for a weighted graph");
		}

		addEdge(from, to, (W) new Boolean(true));
	}

	/**
	 * create a weighted edge from a Road object.
	 * 
	 * @param road
	 *            the road object in question
	 * @param useTime
	 *            true if using time instead of distance to calculate
	 */
	public void addEdge(Road road, Boolean useTime) {
		if (useTime) {
			addEdge((V) road.getFrom(), (V) road.getTo(), (W) new Double(road.getTime()));
		} else {
			addEdge((V) road.getFrom(), (V) road.getTo(), (W) new Double(road.getDistance()));
		}
	}

	/**
	 * Add a weighted edge between two vertices. The values stored is each vertex is
	 * used to identify the vertices. If the graph is undirected then two edges will
	 * be added; an edge from the to vertex to the from vertex will also be added.
	 * 
	 * @param from
	 *            value of the from vertex
	 * @param to
	 *            value of the to vertex
	 * @param weight
	 *            the weight stored on the edge
	 */
	public void addEdge(V from, V to, W weight) {
		if (!weighted && !(weight instanceof Boolean)) {
			throw new RuntimeException("Weight must be of type Boolean for unweighted graphs");
		}

		Vertex vertexFrom = getVertex(from);

		if (vertexFrom == null) {
			throw new RuntimeException("Invalid from vertex " + from);
		}

		Vertex vertexTo = getVertex(to);

		if (vertexTo == null) {
			throw new RuntimeException("Invalid to vertex " + to);
		}

		Edge edge = this.getEdge(vertexFrom, vertexTo);

		if (edge != null) {
			edge.setWeight(weight);
			if (!isDirected()) {
				edge = this.getEdge(vertexTo, vertexFrom);
				edge.setWeight(weight);
			}
		} else {
			vertexFrom.addEdge(vertexTo, weight);

			if (!isDirected()) {
				vertexTo.addEdge(vertexFrom, weight);
			}
		}
	}

	/**
	 * Inner class to iterate over the adjacency list of a vertex
	 *
	 */
	private class AdjacencyIterator implements Iterator<V> {
		private Iterator<Edge> edgeIterator;

		public AdjacencyIterator(Vertex from) {
			edgeIterator = from.adjacencies.values().iterator();
		}

		public boolean hasNext() {
			return edgeIterator.hasNext();
		}

		public V next() {
			Edge nextEdge = edgeIterator.next();
			return nextEdge.vertexTo.value;
		}

		public void remove() {
			throw new RuntimeException("Unsupported operation");
		}
	}

	/**
	 * Create an iterator of vertices that are adjacent to a given vertex. The given
	 * vertex is identified by the value of that vertex. The iterator will return
	 * the value of the vertices for which there exists an edge from the from vertex
	 * to a to vertex.
	 * 
	 * @param from
	 *            the value of the vertex from which to identify adjacent vertices
	 * @return an iterator of vertex values
	 */
	public Iterator<V> adjacentTo(V from) {
		Vertex vertexFrom = getVertex(from);

		if (vertexFrom == null) {
			throw new RuntimeException("Invalid from vertex " + from);
		}

		return new AdjacencyIterator(vertexFrom);
	}

	private Iterator<Edge> edges(V from) {
		Vertex vertexFrom = getVertex(from);

		if (vertexFrom == null) {
			throw new RuntimeException("Invalid from vertex " + from);
		}

		return vertexFrom.edges();
	}

	/**
	 * Get the weight of an edge between two vertices. The vertices are identified
	 * by their values. Returns null if an edge does not exist between the vertices.
	 * 
	 * @param from
	 *            value of the from vertex
	 * @param to
	 *            value of the to vertex
	 * @return
	 */
	public W getWeight(V from, V to) {
		Vertex vertexFrom = getVertex(from);

		if (vertexFrom == null) {
			return null;
		}

		return vertexFrom.getWeight(to);
	}

	private Edge getEdge(Vertex vertexFrom, Vertex to) {
		if (vertexFrom == null) {
			return null;
		}

		return vertexFrom.getEdge(to.value);
	}

	@Override
	public Iterator<V> breadthFirstIterator(V start) {
		return null;
	}

	/**
	 * Return a list containing the path found between two vertices using a breath
	 * first search
	 * 
	 * @param start
	 *            - starting vertex
	 * @param end
	 *            - ending vertex
	 * @return list containing the found path
	 */
	public List<V> breadthFirstSearch(V start, V end) {
		ArrayList<V> path = new ArrayList<V>();
		LinkedList<ArrayList<V>> queue = new LinkedList<ArrayList<V>>();
		Set<V> visited = new HashSet<V>();

		path.add(start);
		queue.add(path);
		visited.add(start);

		while (!queue.isEmpty()) {
			ArrayList<V> lastPath = queue.removeFirst();

			V value = lastPath.get(lastPath.size() - 1);

			if (value.equals(end)) {
				return lastPath;
			}

			for (Iterator<V> iter = adjacentTo(value); iter.hasNext();) {
				V adjacentValue = iter.next();
				if (!visited.contains(adjacentValue)) {
					visited.add(adjacentValue);
					ArrayList<V> nextPath = (ArrayList<V>) lastPath.clone();
					nextPath.add(adjacentValue);
					queue.add(nextPath);
				}

			}

		}

		return null;
	}

	/**
	 * Return a list containing the path found between two vertices using a depth
	 * first search
	 * 
	 * @param start
	 *            - starting vertex
	 * @param end
	 *            - ending vertex
	 * @return list containing the found path
	 */
	public List<V> depthFirstSearch(V start, V end) {
		ArrayList<V> path = new ArrayList<V>();
		ArrayList<V> visited = new ArrayList<V>();

		visited.add(start);

		path = (ArrayList<V>) depthFirstSearch(visited, path, end);

		// if path is empty, return null, else return path
		if (path.isEmpty()) {
			return null;
		} else {
			return path;
		}
	}

	/**
	 * Private method for a depth first search, returns
	 * 
	 * @param visited
	 * @param path
	 * @param end
	 * @return
	 */
	private List<V> depthFirstSearch(ArrayList<V> visited, ArrayList<V> path, V end) {
		// get current vertex from end of visited set
		V current = (V) visited.get(visited.size() - 1);

		if (current.equals(end)) {
			// we've made it to the target, add it to the path and return it
			path.add(current);
			return path;
		}

		// check if any adjacent nodes have not been visited and visit them
		for (Iterator<V> iter = adjacentTo(current); iter.hasNext();) {
			V adjacentValue = iter.next();
			if (!visited.contains(adjacentValue)) {
				visited.add(adjacentValue);
				path = (ArrayList<V>) (depthFirstSearch(visited, path, end));

				// check if the target has been added to the path, if it has, add current node
				// and return path
				if (path.contains(end)) {
					path.add(current);
					return path;
				}

			}
		}

		// full path has been determined, return it
		return path;
	}

	public class Coster implements Costable<V, W> {
		ALGraph<V, W> graph;

		public Coster(ALGraph g) {
			this.graph = g;
		}

		@Override
		public double getCost(V startVertex, V endVertex) {
			Edge e = graph.getEdge(graph.getVertex(startVertex), graph.getVertex(endVertex));
			try {
				Double cost = (Double) e.getWeight();
				return cost;
			} catch (ClassCastException exception) {
				// if this happens, the weight is an int, so initialize it like one
				Integer cost = (Integer) e.getWeight();
				return cost;
			}
		}

	}

	/**
	 * Creates a new Coster using this ALGraph
	 * 
	 * @return the Coster
	 */
	public Coster getCoster() {
		return new Coster(this);
	}

	/**
	 * Return a list containing the path found between two vertices using a best
	 * first search. A Costable is used to get the cost of an edge
	 * 
	 * @param start
	 *            - starting vertex
	 * @param end
	 *            - ending vertex
	 * @param coster
	 *            - costable used to the cost of an edge
	 * @return list containing the found path
	 */
	public List<V> bestSearch(V start, V end, Costable coster) {
		ArrayList<V> s = new ArrayList<V>();
		ArrayList<V> vms = new ArrayList<V>();
		Map<V, Double> dist = new HashMap<V, Double>();
		Map<V, ArrayList<V>> paths = new HashMap<V, ArrayList<V>>();

		// initialize s with start vertex, and V-S with remaining vertices
		s.add(start);
		vms.addAll(verticies.keySet());
		vms.remove(start);

		// initialize dist with adjacent edges
		for (V vertex : vms) {
			// if vertex is adjacent to start, add to dist, else add null
			for (Iterator<V> iter = adjacentTo(start); iter.hasNext();) {
				if (iter.next().equals(vertex)) {
					// if the graph is weighted, it may not actually be adjacent even if it makes it
					// this far, so add null to the list if a NullPointerException is thrown
					try {
						// put the weight of adjacent edge into dist
						double weight = coster.getCost(start, vertex);
						dist.put(vertex, weight);
						break;
					} catch (NullPointerException n) {
						dist.put(vertex, null);
					}
				} else {
					dist.put(vertex, null);
				}
			}
		}

		// initialize path to s for each vertex in vms
		for (V vertex : vms) {
			ArrayList<V> newList = new ArrayList<V>();
			newList.add(start);
			paths.put(vertex, newList);
		}

		while (!vms.isEmpty()) {
			V smallestVertex = null;
			double smallest = -1;
			// find smallest distance in vms
			for (V vertex : vms) {
				if (dist.get(vertex) == null) {
					// no path from start to this node established yet, continue
					continue;
				}
				// node has path from start, check if smallest
				double weight = dist.get(vertex);
				if (smallest > weight || smallest < 0) {
					smallest = dist.get(vertex);
					smallestVertex = vertex;
				}
			}

			// at this point, if the smallest vertex is null, there are no paths to the
			// other nodes in VMS from here (probably a weighted graph), so clear vms
			if (smallestVertex == null) {
				vms.clear();
				continue;
			}

			// remove vertex from vms and add to s
			vms.remove(smallestVertex);
			s.add(smallestVertex);

			// add vertex to path to itself, if it hasn't already
			ArrayList<V> firstPath = (ArrayList<V>) paths.get(smallestVertex).clone();
			if (!firstPath.contains(smallestVertex)) {
				firstPath.add(smallestVertex);
				paths.put(smallestVertex, firstPath);
			}

			// check adjacent verticies in vms for better paths
			for (Iterator<V> iter = adjacentTo(smallestVertex); iter.hasNext();) {
				V adjacentVertex = iter.next();
				if (vms.contains(adjacentVertex)) {

					// vertex is adjacent and present in vms, continue
					double weight = (Double) coster.getCost(smallestVertex, adjacentVertex);
					double newDist = dist.get(smallestVertex) + weight;

					if (dist.get(adjacentVertex) == null || newDist < dist.get(adjacentVertex)) {
						// new distance is smaller than previous, update dist
						dist.put(adjacentVertex, newDist);

						// update path
						ArrayList<V> newPath = (ArrayList<V>) paths.get(smallestVertex).clone();
						newPath.add(adjacentVertex);
						paths.put(adjacentVertex, newPath);
					}
				}
			}

		}

		// all paths calculated, check that a path to target really exists (sometimes
		// directed graphs fail without this step)
		boolean hasPath = false;
		for (Iterator<V> iter = paths.keySet().iterator(); iter.hasNext();) {
			if (paths.get(iter.next()).contains(end)) {
				// end node is in path
				hasPath = true;
			}
		}
		// correct path exists, return path to end vertex
		if (hasPath) {
			return paths.get(end);
		} else {
			return null;
		}

	}

	/**
	 * A* search, which I actually never implemented because I've stared at the
	 * debugger for long enough and I'm not really sure how to implement it.
	 */
	public List<V> aStarSearch(V start, V end) {

		return null;
	}

}
