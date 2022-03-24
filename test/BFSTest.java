package test;

import org.junit.Assert;
import org.junit.Test;
import sol.BFS;
import sol.TravelController;
import sol.TravelGraph;
import src.City;
import src.IGraph;
import src.Transport;
import test.simple.SimpleEdge;
import test.simple.SimpleGraph;
import test.simple.SimpleVertex;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Your BFS tests should all go in this class!
 * The test we've given you will pass if you've implemented BFS correctly, but we still expect
 * you to write more tests using the City and Transport classes.
 * You are welcome to write more tests using the Simple classes, but you will not be graded on
 * those.
 *
 * TODO: Recreate the test below for the City and Transport classes
 * TODO: Expand on your tests, accounting for basic cases and edge cases
 */
public class BFSTest {

    private static final double DELTA = 0.001;

    private SimpleVertex a;
    private SimpleVertex b;
    private SimpleVertex c;
    private SimpleVertex d;
    private SimpleVertex e;
    private SimpleVertex f;
    private SimpleGraph graph;

    /**
     * Creates a simple graph.
     * You'll find a similar method in each of the Test files.
     * Normally, we'd like to use @Before, but because each test may require a different setup,
     * we manually call the setup method at the top of the test.
     *
     * TODO: create more setup methods!
     */
    public void makeSimpleGraph() {
        this.graph = new SimpleGraph();

        this.a = new SimpleVertex("a");
        this.b = new SimpleVertex("b");
        this.c = new SimpleVertex("c");
        this.d = new SimpleVertex("d");
        this.e = new SimpleVertex("e");
        this.f = new SimpleVertex("f");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);
        this.graph.addVertex(this.d);
        this.graph.addVertex(this.e);
        this.graph.addVertex(this.f);

        this.graph.addEdge(this.a, new SimpleEdge(1, this.a, this.b));
        this.graph.addEdge(this.b, new SimpleEdge(1, this.b, this.c));
        this.graph.addEdge(this.c, new SimpleEdge(1, this.c, this.e));
        this.graph.addEdge(this.d, new SimpleEdge(1, this.d, this.e));
        this.graph.addEdge(this.a, new SimpleEdge(100, this.a, this.f));
        this.graph.addEdge(this.f, new SimpleEdge(100, this.f, this.e));
    }

    @Test
    public void testBasicBFS() {
        this.makeSimpleGraph();
        BFS<SimpleVertex, SimpleEdge> bfs = new BFS<>();
        List<SimpleEdge> path = bfs.getPath(this.graph, this.a, this.e);
        assertEquals(SimpleGraph.getTotalEdgeWeight(path), 200.0, DELTA);
        assertEquals(path.size(), 2);
    }

    @Test
    public void testMostDirectGraphOne(){
        TravelController travelController = new TravelController();
        travelController.load("data/cities01", "data/transport01");

        List<Transport> path = travelController.mostDirectRoute("Shenzhen", "Chengdu");
        assertEquals(path.size(), 2);
        assertEquals(travelController.getGraph().getTotalPrice(path), 70, 0.001);
        assertEquals(travelController.getGraph().getTotalTime(path), 817, 0.001);

        List<Transport> path2 = travelController.mostDirectRoute("Guangzhou", "Shanghai");
        assertEquals(path2.size(), 2);
        assertEquals(travelController.getGraph().getTotalPrice(path2), 173, 0.001);
        assertEquals(travelController.getGraph().getTotalTime(path2), 208, 0.001);

        List<Transport> path3 = travelController.mostDirectRoute("Beijing", "Chengdu");
        assertEquals(path3.size(), 1);
        assertEquals(travelController.getGraph().getTotalPrice(path3), 15, 0.001);
        assertEquals(travelController.getGraph().getTotalTime(path3), 900, 0.001);

        List<Transport> path4 = travelController.mostDirectRoute("Beijing", "Beijing");
        assertEquals(path4.size(), 0);
        assertEquals(travelController.getGraph().getTotalPrice(path4), 0, 0.001);
        assertEquals(travelController.getGraph().getTotalTime(path4), 0, 0.001);

        travelController.getGraph().addVertex(new City("Xian"));
        List<Transport> path5 = travelController.mostDirectRoute("Beijing", "Xian");
        assertEquals(path4.size(), 0);
        assertEquals(travelController.getGraph().getTotalPrice(path4), 0, 0.001);
        assertEquals(travelController.getGraph().getTotalTime(path4), 0, 0.001);


    }

    @Test
    public void testMostDirectGraphTwo(){
        TravelController travelController = new TravelController();
        travelController.load("data/cities02", "data/transport02");

        List<Transport> path = travelController.mostDirectRoute("Sirius", "Mars");
        assertEquals(path.size(), 2);
        assertEquals(travelController.getGraph().getTotalPrice(path), 3157, 0.001);
        assertEquals(travelController.getGraph().getTotalTime(path), 35948, 0.001);

        List<Transport> path2 = travelController.mostDirectRoute("Earth", "Mars");
        assertEquals(path2.size(), 1);
        assertEquals(travelController.getGraph().getTotalPrice(path2), 77, 0.001);
        assertEquals(travelController.getGraph().getTotalTime(path2), 23008, 0.001);

        List<Transport> path3 = travelController.mostDirectRoute("Mercury", "Mercury");
        assertEquals(path3.size(), 0);
        assertEquals(travelController.getGraph().getTotalPrice(path3), 0, 0.001);
        assertEquals(travelController.getGraph().getTotalTime(path3), 0, 0.001);

        travelController.getGraph().addVertex(new City("Venus"));
        List<Transport> path4 = travelController.mostDirectRoute("Mercury", "Venus");
        assertEquals(path4.size(), 0);
        assertEquals(travelController.getGraph().getTotalPrice(path4), 0, 0.001);
        assertEquals(travelController.getGraph().getTotalTime(path4), 0, 0.001);

    }
}
