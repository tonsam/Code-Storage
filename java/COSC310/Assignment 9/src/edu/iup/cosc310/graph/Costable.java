package edu.iup.cosc310.graph;

public interface Costable<V,W> {
	double getCost(V startVertex, V endVertex);
}
