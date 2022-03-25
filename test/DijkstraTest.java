package test;

import org.junit.Test;
import sol.Dijkstra;
import sol.TravelController;
import sol.TravelGraph;
import src.City;
import src.IDijkstra;
import src.Transport;
import src.TransportType;
import test.simple.SimpleEdge;
import test.simple.SimpleGraph;
import test.simple.SimpleVertex;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.*;

/**
 * Your Dijkstra's tests should all go in this class!
 * The test we've given you will pass if you've implemented Dijkstra's
 * correctly, but we still expect you to write more tests using the City and
 * Transport classes. You are welcome to write more tests using the Simple
 * classes, but you will not be graded on
 * those.
 *
 * TODO: Recreate the test below for the City and Transport classes
 * TODO: Expand on your tests, accounting for basic cases and edge cases
 */
public class DijkstraTest {

    private static final double DELTA = 0.001;

    private SimpleGraph graph;
    private SimpleVertex a;
    private SimpleVertex b;
    private SimpleVertex c;
    private SimpleVertex d;
    private SimpleVertex e;

    /**
     * Creates a simple graph.
     * You'll find a similar method in each of the Test files.
     * Normally, we'd like to use @Before, but because each test may require a
     * different setup, we manually call the setup method at the top of the
     * test.
     *
     * TODO: create more setup methods!
     */
    private void createSimpleGraph() {
        this.graph = new SimpleGraph();

        this.a = new SimpleVertex("a");
        this.b = new SimpleVertex("b");
        this.c = new SimpleVertex("c");
        this.d = new SimpleVertex("d");
        this.e = new SimpleVertex("e");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);
        this.graph.addVertex(this.d);
        this.graph.addVertex(this.e);

        this.graph.addEdge(this.a, new SimpleEdge(100, this.a, this.b));
        this.graph.addEdge(this.a, new SimpleEdge(3, this.a, this.c));
        this.graph.addEdge(this.a, new SimpleEdge(1, this.a, this.e));
        this.graph.addEdge(this.c, new SimpleEdge(6, this.c, this.b));
        this.graph.addEdge(this.c, new SimpleEdge(2, this.c, this.d));
        this.graph.addEdge(this.d, new SimpleEdge(1, this.d, this.b));
        this.graph.addEdge(this.d, new SimpleEdge(5, this.e, this.d));
    }

    /**
     * checks on the methods getShortestPath method on the simple graph
     */
    @Test
    public void testSimple() {
        this.createSimpleGraph();

        IDijkstra<SimpleVertex, SimpleEdge> dijkstra = new Dijkstra<>();
        Function<SimpleEdge, Double> edgeWeightCalculation = e -> e.weight;
        // a -> c -> d -> b
        List<SimpleEdge> path =
            dijkstra.getShortestPath(this.graph, this.a, this.b,
                    edgeWeightCalculation);
        assertEquals(6, SimpleGraph.getTotalEdgeWeight(path), DELTA);
        assertEquals(3, path.size());

        // c -> d -> b
        path = dijkstra.getShortestPath(this.graph, this.c, this.b,
                edgeWeightCalculation);
        assertEquals(3, SimpleGraph.getTotalEdgeWeight(path), DELTA);
        assertEquals(2, path.size());
    }

    /**
     * checks on method cheapestRoute on the first graph constructed using
     * getTotalPrice.
     * checks on edge cases: travelling to the same location, travelling to a
     * location with no connection.
     */
    @Test
    public void testGraphOneCheapest(){
        TravelController travelController = new TravelController();
        travelController.load("data/cities01",
                "data/transport01");

        List<Transport> path = travelController.cheapestRoute("Beijing",
                "Chengdu");
        assertEquals(path.size(), 1);
        assertEquals(travelController.getGraph().getTotalPrice(path), 15,
                0.001);
        assertEquals(travelController.mostDirectRoute("Beijing",
                "Chengdu"), path);

        List<Transport> path2 = travelController.cheapestRoute(
                "Shanghai", "Shenzhen");
        assertEquals(path2.size(), 2);
        assertEquals(travelController.getGraph().getTotalPrice(path2),
                50, 0.001);
        assertFalse(travelController.mostDirectRoute("Shanghai",
                "Shenzhen").equals(path2));

        List<Transport> path3 = travelController.cheapestRoute("Beijing",
                "Guangzhou");
        assertEquals(path3.size(), 2);
        assertEquals(travelController.getGraph().getTotalPrice(path3),
                42, 0.001);
        assertFalse(travelController.mostDirectRoute("Beijing",
                "Guangzhou").equals(path3));

        List<Transport> path4 = travelController.cheapestRoute(
                "Guangzhou", "Guangzhou");
        assertEquals(path4.size(), 0);
        assertEquals(travelController.getGraph().getTotalPrice(path4),
                0, 0.001);

        travelController.getGraph().addVertex(new City("Nanjing"));
        List<Transport> path5 = travelController.cheapestRoute(
                "Guangzhou", "Nanjing");
        assertEquals(path5.size(), 0);
        assertEquals(travelController.getGraph().getTotalPrice(path5),
                0, 0.001);

    }

    /**
     * checks on method FastestRoute on the first graph constructed using
     * getTotalTime.
     * checks on edge cases: travelling to the same location, travelling to a
     * location with no connection.
     */
    @Test
    public void testGraphOneFastest(){
        TravelController travelController = new TravelController();
        travelController.load("data/cities01",
                "data/transport01");

        List<Transport> path = travelController.fastestRoute("Beijing",
                "Shanghai");
        assertEquals(path.size(), 1);
        assertEquals(travelController.getGraph().getTotalTime(path), 55,
                0.001);
        assertFalse(travelController.cheapestRoute("Beijing",
                "Shanghai").equals(path));

        List<Transport> path2 = travelController.fastestRoute("Shanghai",
                "Guangzhou");
        assertEquals(path2.size(), 2);
        assertEquals(travelController.getGraph().getTotalTime(path2),
                137, 0.001);
        assertFalse(travelController.mostDirectRoute("Shanghai",
                "Guangzhou").equals(path2));

        List<Transport> path3 = travelController.fastestRoute(
                "Guangzhou", "Beijing");
        assertEquals(path3.size(), 3);
        assertEquals(travelController.getGraph().getTotalTime(path3),
                268, 0.001);
        assertFalse(travelController.mostDirectRoute("Guangzhou",
                "Beijing").equals(path3));

        List<Transport> path4 = travelController.fastestRoute("Beijing",
                "Beijing");
        assertEquals(path4.size(), 0);
        assertEquals(travelController.getGraph().getTotalTime(path4),
                0, 0.001);

        travelController.getGraph().addVertex(new City("Haerbin"));
        List<Transport> path5 = travelController.fastestRoute("Beijing",
                "Haerbin");
        assertEquals(path5.size(), 0);
        assertEquals(travelController.getGraph().getTotalTime(path5),
                0, 0.001);

    }


    /**
     * checks on method cheapestRoute on the second graph constructed using
     * getTotalPrice.
     * checks on edge cases: travelling to the same location, travelling to a
     * location with no connection.
     */
    @Test
    public void testGraphTwoCheapest(){
        TravelController travelController = new TravelController();
        travelController.load("data/cities02",
                "data/transport02");

        List<Transport> path = travelController.cheapestRoute("Mars",
                "Sirius");
        assertEquals(path.size(), 2);
        assertEquals(travelController.getGraph().getTotalPrice(path),
                473, 0.001);
        assertFalse(travelController.mostDirectRoute("Mars",
                "Sirius").equals(path));

        List<Transport> path2 = travelController.cheapestRoute("Earth",
                "Mars");
        assertEquals(path2.size(), 1);
        assertEquals(travelController.getGraph().getTotalPrice(path2),
                77, 0.001);
        assertFalse(travelController.fastestRoute("Earth",
                "Mars").equals(path2));


        List<Transport> path3 = travelController.cheapestRoute("Mars",
                "Mars");
        assertEquals(path3.size(), 0);
        assertEquals(travelController.getGraph().getTotalPrice(path3),
                0, 0.001);

        travelController.getGraph().addVertex(new City("Neptune"));
        List<Transport> path4 = travelController.cheapestRoute("Earth",
                "Neptune");
        assertEquals(path4.size(), 0);
        assertEquals(travelController.getGraph().getTotalPrice(path4),
                0, 0.001);

    }


    /**
     * checks on method fastestRoute on the second graph constructed using
     * getTotalTime.
     * checks on edge cases: travelling to the same location, travelling to a
     * location with no connection.
     */
    @Test
    public void testGraphTwoFastest(){
        TravelController travelController = new TravelController();
        travelController.load("data/cities02",
                "data/transport02");

        List<Transport> path = travelController.fastestRoute("Earth",
                "Mercury");
        assertEquals(path.size(), 2);
        assertEquals(travelController.getGraph().getTotalTime(path),
                8930, 0.001);
        assertFalse(travelController.cheapestRoute("Earth",
                "Mercury").equals(path));

        List<Transport> path2 = travelController.fastestRoute("Mercury",
                "Sirius");
        assertEquals(path2.size(), 2);
        assertEquals(travelController.getGraph().getTotalTime(path2),
                8600, 0.001);
        assertFalse(travelController.mostDirectRoute("Mercury",
                "Sirius").equals(path2));

        List<Transport> path3 = travelController.fastestRoute("Mars",
                "Mars");
        assertEquals(path3.size(), 0);
        assertEquals(travelController.getGraph().getTotalTime(path3), 0,
                0.001);

        travelController.getGraph().addVertex(new City("Uranus"));
        List<Transport> path4 = travelController.fastestRoute("Uranus",
                "Sirius");
        assertEquals(path4.size(), 0);
        assertEquals(travelController.getGraph().getTotalTime(path4), 0,
                0.001);


    }


}
