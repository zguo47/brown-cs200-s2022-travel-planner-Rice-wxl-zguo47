package sol;

import src.IDijkstra;
import src.IGraph;

import java.util.*;
import java.util.function.Function;

/**
 * This is the Dijkstra algorithm that calculates the route based on edge weights.
 * @param <V> abstract vertex type
 * @param <E> abstract edge type
 */
public class Dijkstra<V, E> implements IDijkstra<V, E> {

    /**
     * finds the shortest path between the start and end vertices in the input graph, based on the input edge weight.
     * @param graph       the graph including the vertices
     * @param source      the source vertex
     * @param destination the destination vertex
     * @param edgeWeight the edge weight to consider.
     * @return the shortest path as a list of edges.
     */
    @Override
    public List<E> getShortestPath(IGraph<V, E> graph, V source, V destination,
                                   Function<E, Double> edgeWeight) {

        // if the start and end vertices are the same, returns an empty list.
        if (source.toString().equals(destination.toString())) {
            return Collections.EMPTY_LIST;
        }

        // the data structure that stores each vertex's total edge weights from the source.
        HashMap<V, Double> vWithWeight = new HashMap<>();
        for (V v : graph.getVertices()) {
            if (v.equals(source)) {
                vWithWeight.put(v, 0.0);
            } else {
                vWithWeight.put(v, Double.POSITIVE_INFINITY);
            }
        }

        // instantiates cameFrom.
        HashMap<V, E> cameFrom = new HashMap<>();

        // the comparator that calculates which total edge weights are shorter.
        Comparator<V> vertexByRoute = (vertex1, vertex2) -> {
            return Double.compare(vWithWeight.get(vertex1), vWithWeight.get(vertex2));
        };

        // the PriorityQueue which is used to order the vertices by total edge weights.
        PriorityQueue<V> vQueue = new PriorityQueue<V>(vertexByRoute);
        for (V v : vWithWeight.keySet()) {
            vQueue.add(v);
        }

        while (!vQueue.isEmpty()) {
            // The checkingVertex is the element of vQueue with the least total edge weights.
            V checkingVertex = vQueue.remove();

            if (graph.getOutgoingEdges(checkingVertex) != null) {
                for (E theTransport : graph.getOutgoingEdges(checkingVertex)) {
                    V neighbor = graph.getEdgeTarget(theTransport);

                    // updates the total edge weights for the checkingVertex from the source.
                    if (vWithWeight.get(checkingVertex)
                            + edgeWeight.apply(theTransport) < vWithWeight.get(neighbor)) {
                        vWithWeight.replace(neighbor, vWithWeight.get(checkingVertex)
                                + edgeWeight.apply(theTransport));

                        // updates cameFrom.
                        cameFrom.put(neighbor, theTransport);
                    }
                }
            }
            }

        // the case when the algorithm finds the route.
        if (cameFrom.containsKey(destination)) {
            LinkedList<E> thePath = new LinkedList<E>();
            thePath.addFirst(cameFrom.get(destination));
            while (!graph.getEdgeSource(thePath.getFirst()).equals(source)) {
                thePath.addFirst(cameFrom.get(graph.getEdgeSource(thePath.getFirst())));
            }
            return thePath;
        } else {
            // if the algorithm can not find the path, returns an empty list.
            return Collections.EMPTY_LIST;
        }
    }
}
