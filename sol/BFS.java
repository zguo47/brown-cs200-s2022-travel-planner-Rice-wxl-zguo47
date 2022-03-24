package sol;

import src.City;
import src.IBFS;
import src.IGraph;
import src.Transport;

import java.util.*;

public class BFS<V, E> implements IBFS<V, E> {

    @Override
    public List<E> getPath(IGraph<V, E> graph, V start, V end) {

        if (start.toString().equals(end.toString())) {
            return Collections.EMPTY_LIST;
        }


        LinkedList<V> toCheck = new LinkedList<>();
        ArrayList<V> visited = new ArrayList<>();
        HashMap<V, E> cameFrom = new HashMap<>();

        toCheck.addLast(start);

        while (!toCheck.isEmpty()) {
            if (cameFrom.containsKey(end)) {
                LinkedList<E> thePath = new LinkedList<E>();
                thePath.addFirst(cameFrom.get(end));
                while (!graph.getEdgeSource(thePath.getFirst()).equals(start)) {
                    thePath.addFirst(cameFrom.get
                            (graph.getEdgeSource(thePath.getFirst())));
                }
                return thePath;
            }
            V checkingVertex = toCheck.removeFirst();

            visited.add(checkingVertex);

                for (E theTransport : graph.getOutgoingEdges(checkingVertex)) {
                    V neighbor = graph.getEdgeTarget(theTransport);
                    if (!visited.contains(neighbor)) {
                        toCheck.addLast(neighbor);
                        if (!cameFrom.containsKey(neighbor)){
                            cameFrom.put(neighbor, theTransport);
                        }
                    }
                }
        }
        return Collections.EMPTY_LIST;
    }


}

