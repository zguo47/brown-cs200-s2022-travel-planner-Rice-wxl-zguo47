package sol;

import src.City;
import src.IBFS;
import src.IGraph;
import src.Transport;

import java.util.*;

/**
 * This is the BFS algorithm that calculates the most direct route.
 * @param <V> abstract vertex type
 * @param <E> abstract edge type
 */
public class BFS<V, E> implements IBFS<V, E> {

    /**
     * finds the most direct path between the start and end vertices in the
     * input graph.
     * @param graph the graph including the vertices
     * @param start the start vertex
     * @param end   the end vertex
     * @return the most direct path as a list of edges
     */
    @Override
    public List<E> getPath(IGraph<V, E> graph, V start, V end) {

        // if the start and end vertices are the same, returns an empty list.
        if (start.toString().equals(end.toString())) {
            return Collections.EMPTY_LIST;
        }

        // instantiates the needed data structures.
        LinkedList<V> toCheck = new LinkedList<>();
        ArrayList<V> visited = new ArrayList<>();
        HashMap<V, E> cameFrom = new HashMap<>();

        // adds the starting vertex to toCheck.
        toCheck.addLast(start);

        while (!toCheck.isEmpty()) {
            // the case when the algorithm finds the route.
            if (cameFrom.containsKey(end)) {
                LinkedList<E> thePath = new LinkedList<E>();
                thePath.addFirst(cameFrom.get(end));
                while (!graph.getEdgeSource(thePath.getFirst()).equals(start)) {
                    thePath.addFirst(cameFrom.get
                            (graph.getEdgeSource(thePath.getFirst())));
                }
                return thePath;
            }

            // The checkingVertex is the first element of toCheck.
            V checkingVertex = toCheck.removeFirst();

            // Adds the checkingVertex to the visited.
            visited.add(checkingVertex);

            for (E theTransport : graph.getOutgoingEdges(checkingVertex)) {
                V neighbor = graph.getEdgeTarget(theTransport);

                // updates toCheck.
                if (!visited.contains(neighbor)) {
                    toCheck.addLast(neighbor);

                    // updates cameFrom.
                    if (!cameFrom.containsKey(neighbor)) {
                        cameFrom.put(neighbor, theTransport);
                    }

                }
            }
        }
        // if the algorithm can not find the path, returns an empty list.
        return Collections.EMPTY_LIST;
    }

}