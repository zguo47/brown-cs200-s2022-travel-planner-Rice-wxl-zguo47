package sol;

import src.IDijkstra;
import src.IGraph;

import java.util.*;
import java.util.function.Function;

public class Dijkstra<V, E> implements IDijkstra<V, E> {

    @Override
    public List<E> getShortestPath(IGraph<V, E> graph, V source, V destination,
                                   Function<E, Double> edgeWeight) {

        HashMap<V, Double> vWithWeight = new HashMap<>();
        for (V v: graph.getVertices()) {
            if (v.equals(source)){
                vWithWeight.put(v, 0.0);
            } else {
                vWithWeight.put(v, Double.POSITIVE_INFINITY);
            }
        }

        HashMap<V, E> cameFrom = new HashMap<>();


        Comparator<V> vertexByRoute = (vertex1, vertex2)  -> {
            return Double.compare(vWithWeight.get(vertex1), vWithWeight.get(vertex2));
        };

        PriorityQueue<V> vQueue = new PriorityQueue<V>(vertexByRoute);
        for (V v : vWithWeight.keySet()) {
            vQueue.add(v);
        }

        while (!vQueue.isEmpty()) {
            V checkingVertex = vQueue.remove();

            if (graph.getOutgoingEdges(checkingVertex) != null) {
                for (E theTransport : graph.getOutgoingEdges(checkingVertex)) {
                    V neighbor = graph.getEdgeTarget(theTransport);
                    if (vWithWeight.get(checkingVertex)
                            + edgeWeight.apply(theTransport) < vWithWeight.get(neighbor)) {
                        vWithWeight.replace(neighbor, vWithWeight.get(checkingVertex)
                                + edgeWeight.apply(theTransport));
                        cameFrom.put(neighbor, theTransport);
                    }
                }
            }
            }


        LinkedList<E> thePath = new LinkedList<E>();
        thePath.addFirst(cameFrom.get(destination));
        while (!graph.getEdgeSource(thePath.getFirst()).equals(source)) {
            thePath.addFirst(cameFrom.get(graph.getEdgeSource(thePath.getFirst())));
        }
        return thePath;
    }

}
