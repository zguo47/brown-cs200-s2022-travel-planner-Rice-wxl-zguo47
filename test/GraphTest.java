package test;

import org.junit.Test;
import sol.TravelController;
import sol.TravelGraph;
import src.City;
import src.Transport;
import src.TransportType;
import test.simple.SimpleEdge;
import test.simple.SimpleGraph;
import test.simple.SimpleVertex;

import static org.junit.Assert.*;

/**
 * Your Graph method tests should all go in this class!
 * The test we've given you will pass, but we still expect you to write more
 * tests using the
 * City and Transport classes.
 * You are welcome to write more tests using the Simple classes, but you will
 * not be graded on
 * those.
 *
 */
public class GraphTest {
    private TravelController controller;
    private SimpleGraph graph;

    private SimpleVertex a;
    private SimpleVertex b;
    private SimpleVertex c;

    private SimpleEdge edgeAB;
    private SimpleEdge edgeBC;
    private SimpleEdge edgeCA;
    private SimpleEdge edgeAC;

    /**
     * Creates a simple graph.
     * You'll find a similar method in each of the Test files.
     * Normally, we'd like to use @Before, but because each test may require
     * a different setup,
     * we manually call the setup method at the top of the test.
     *
     */
    private void createSimpleGraph() {
        this.graph = new SimpleGraph();

        this.a = new SimpleVertex("A");
        this.b = new SimpleVertex("B");
        this.c = new SimpleVertex("C");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);

        // create and insert edges
        this.edgeAB = new SimpleEdge(1, this.a, this.b);
        this.edgeBC = new SimpleEdge(1, this.b, this.c);
        this.edgeCA = new SimpleEdge(1, this.c, this.a);
        this.edgeAC = new SimpleEdge(1, this.a, this.c);

        this.graph.addEdge(this.a, this.edgeAB);
        this.graph.addEdge(this.b, this.edgeBC);
        this.graph.addEdge(this.c, this.edgeCA);
        this.graph.addEdge(this.a, this.edgeAC);
    }

    /**
     * checks on method getVertices with the simple graph.
     */
    @Test
    public void testGetVertices() {
        this.createSimpleGraph();

        // test getVertices to check this method AND insertVertex
        assertEquals(this.graph.getVertices().size(), 3);
        assertTrue(this.graph.getVertices().contains(this.a));
        assertTrue(this.graph.getVertices().contains(this.b));
        assertTrue(this.graph.getVertices().contains(this.c));
    }

    private TravelGraph travelGraph;

    private City beijing;
    private City shanghai;
    private City chengdu;
    private City shenzhen;
    private City guangzhou;

    private Transport edgeBeiShang1;
    private Transport edgeBeiShang2;
    private Transport edgeBeiGuang;
    private Transport edgeBeiShen;
    private Transport edgeBeiCheng1;
    private Transport edgeBeiCheng2;

    private Transport edgeChengBei;
    private Transport edgeChengShen;

    private Transport edgeGuangCheng;
    private Transport edgeGuangShen;
    private Transport edgeGuangBei;

    private Transport edgeShangBei1;
    private Transport edgeShangBei2;
    private Transport edgeShangShen;
    private Transport edgeShangGuang;

    private Transport edgeShenGuang;
    private Transport edgeShenShang;


    /**
     * creates the travelGraph graph one.
     */
    private void setUpGraph() {
        this.travelGraph = new TravelGraph();

        this.beijing = new City("Beijing");
        this.chengdu = new City("Chengdu");
        this.shanghai = new City("Shanghai");
        this.shenzhen = new City("Shenzhen");
        this.guangzhou = new City("Guangzhou");

        this.travelGraph.addVertex(this.beijing);
        this.travelGraph.addVertex(this.shanghai);
        this.travelGraph.addVertex(this.chengdu);
        this.travelGraph.addVertex(this.shenzhen);
        this.travelGraph.addVertex(this.guangzhou);

        // create and insert edges
        this.edgeBeiShang1 = new Transport(this.beijing, this.shanghai,
                TransportType.PLANE, 75.0, 55.0);
        this.edgeBeiShang2 = new Transport(this.beijing, this.shanghai,
                TransportType.TRAIN, 15.0, 180.0);
        this.edgeBeiGuang = new Transport(this.beijing, this.guangzhou,
                TransportType.PLANE, 105.0, 185.0);
        this.edgeBeiShen = new Transport(this.beijing, this.shenzhen,
                TransportType.PLANE, 120.0, 231.0);
        this.edgeBeiCheng1 = new Transport(this.beijing, this.chengdu,
                TransportType.BUS, 15.0, 900.0);
        this.edgeBeiCheng2 = new Transport(this.beijing, this.chengdu,
                TransportType.TRAIN, 55.0, 335.0);

        this.edgeChengBei = new Transport(this.chengdu, this.beijing,
                TransportType.PLANE, 75.0, 111.0);
        this.edgeChengShen = new Transport(this.chengdu, this.shenzhen,
                TransportType.TRAIN, 32.0, 288.0);

        this.edgeGuangCheng = new Transport(this.guangzhou, this.chengdu,
                TransportType.BUS, 15.0, 770.0);
        this.edgeGuangShen = new Transport(this.guangzhou, this.shenzhen,
                TransportType.TRAIN, 23.0, 115.0);
        this.edgeGuangBei = new Transport(this.guangzhou, this.beijing,
                TransportType.TRAIN, 60.0, 400.0);

        this.edgeShangBei1 = new Transport(this.shanghai, this.beijing,
                TransportType.BUS, 9.0, 300.0);
        this.edgeShangBei2 = new Transport(this.shanghai, this.beijing,
                TransportType.PLANE, 65.0, 60.0);
        this.edgeShangGuang = new Transport(this.shanghai, this.guangzhou,
                TransportType.BUS, 27.0, 950.0);
        this.edgeShangShen = new Transport(this.shanghai, this.shenzhen,
                TransportType.PLANE, 70.0, 90.0);

        this.edgeShenGuang = new Transport(this.shenzhen, this.guangzhou,
                TransportType.PLANE, 55.0, 47.0);
        this.edgeShenShang = new Transport(this.shenzhen, this.shanghai,
                TransportType.PLANE, 150.0, 93.0);


        this.travelGraph.addEdge(this.beijing, this.edgeBeiShang1);
        this.travelGraph.addEdge(this.beijing, this.edgeBeiShang2);
        this.travelGraph.addEdge(this.beijing, this.edgeBeiShen);
        this.travelGraph.addEdge(this.beijing, this.edgeBeiGuang);
        this.travelGraph.addEdge(this.beijing, this.edgeBeiCheng1);
        this.travelGraph.addEdge(this.beijing, this.edgeBeiCheng2);

        this.travelGraph.addEdge(this.chengdu, this.edgeChengBei);
        this.travelGraph.addEdge(this.chengdu, this.edgeChengShen);

        this.travelGraph.addEdge(this.guangzhou, this.edgeGuangBei);
        this.travelGraph.addEdge(this.guangzhou, this.edgeGuangCheng);
        this.travelGraph.addEdge(this.guangzhou, this.edgeGuangShen);

        this.travelGraph.addEdge(this.shanghai, this.edgeShangBei1);
        this.travelGraph.addEdge(this.shanghai, this.edgeShangBei2);
        this.travelGraph.addEdge(this.shanghai, this.edgeShangShen);
        this.travelGraph.addEdge(this.shanghai, this.edgeShangGuang);

        this.travelGraph.addEdge(this.shenzhen, this.edgeShenShang);
        this.travelGraph.addEdge(this.shenzhen, this.edgeShenGuang);

    }


    /**
     * checks on the method getVertices on graph one.
     */
      @Test
    public void testGetVerticesOne() {
          this.setUpGraph();

        // test getVertices to check this method AND insertVertex
          assertEquals(this.travelGraph.getVertices().size(), 5);
          assertTrue(this.travelGraph.getVertices().contains(
                  this.travelGraph.getCity("Beijing")));
          assertTrue(this.travelGraph.getVertices().contains(
                  this.travelGraph.getCity("Shanghai")));
          assertTrue(this.travelGraph.getVertices().contains(
                  this.travelGraph.getCity("Chengdu")));
    }

    /**
     * checks on the method getEdgeSource on graph one.
     */
    @Test
    public void testGetEdgeSource(){
        this.setUpGraph();

        assertEquals(this.travelGraph.getEdgeSource(this.edgeShenShang),
                this.shenzhen);
        assertEquals(this.travelGraph.getEdgeSource(this.edgeGuangCheng),
                this.guangzhou);
        assertEquals(this.travelGraph.getEdgeSource(this.edgeShangBei1),
                this.shanghai);

    }
    /**
     * checks on the method getEdgeTarget on graph one.
     */
    @Test
    public void testGetEdgeTarget(){
        this.setUpGraph();

        assertEquals(this.travelGraph.getEdgeTarget(this.edgeShenGuang),
                this.guangzhou);
        assertEquals(this.travelGraph.getEdgeTarget(this.edgeBeiCheng2),
                this.chengdu);
        assertEquals(this.travelGraph.getEdgeTarget(this.edgeShenShang),
                this.shanghai);
    }

    /**
     * checks on the method getOutgoingEdges on graph one.
     */
    @Test
    public void testOutgoingEdges(){
        this.setUpGraph();

        assertEquals(this.travelGraph.getOutgoingEdges(
                this.travelGraph.getCity("Beijing")).size(), 6);
        assertEquals(this.travelGraph.getOutgoingEdges(
                this.travelGraph.getCity("Shenzhen")).size(), 2);
        assertTrue(this.travelGraph.getOutgoingEdges(
                this.travelGraph.getCity("Guangzhou")).contains(
                        this.edgeGuangCheng));
        assertTrue(this.travelGraph.getOutgoingEdges(
                this.travelGraph.getCity("Beijing")).contains(
                        this.edgeBeiCheng2));
        assertFalse(this.travelGraph.getOutgoingEdges(
                this.travelGraph.getCity("Beijing")).contains(
                        this.edgeShangBei2));
        assertFalse(this.travelGraph.getOutgoingEdges(
                this.travelGraph.getCity("Beijing")).contains(
                        this.edgeChengShen));

    }

    /**
     * checks on the method getCity on graph one.
     */
    @Test
    public void testGetCity(){
        this.setUpGraph();

        assertEquals(this.travelGraph.getCity("Beijing"), this.beijing);
        assertEquals(this.travelGraph.getCity("Chengdu"), this.chengdu);
        assertFalse(this.travelGraph.getCity("Shanghai").equals(
                this.shenzhen));
    }

    /**
     * checks on the method load on graph one and graph two from data file.
     */
    @Test
    public void testLoad(){
        TravelController travelController = new TravelController();
        travelController.load("data/cities01",
                "data/transport01");

        assertEquals(travelController.getGraph().getVertices().size(),
                5);
        assertEquals(travelController.getGraph().getCity("Guangzhou").
                toString(), "Guangzhou");
        assertEquals(travelController.getGraph().getCity("Shenzhen").
                toString(), "Shenzhen");

        assertEquals(travelController.getGraph().getCity("Beijing").
                getOutgoing().size(), 6);
        assertEquals(travelController.getGraph().getOutgoingEdges(
                travelController.getGraph().getCity("Shanghai")).size(),
                4);

        TravelController travelController2 = new TravelController();
        travelController2.load("data/cities02",
                "data/transport02");

        assertEquals(travelController2.getGraph().getVertices().size(),
                4);
        assertEquals(travelController2.getGraph().getCity("Mars")
                .toString(), "Mars");
        assertEquals(travelController2.getGraph().getCity("Sirius")
                .toString(), "Sirius");

        assertEquals(travelController2.getGraph().getCity("Earth")
                .getOutgoing().size(), 4);
        assertEquals(travelController2.getGraph().getOutgoingEdges(
                travelController2.getGraph().getCity("Earth")).size(),
                4);
    }

    /**
     * Test IllegalArgumentException when the origin or destination is not on
     * the travelGraph, throwing "City does not exist!".
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentException() {
        TravelController travelController = new TravelController();
        travelController.load("data/cities01",
                "data/transport01");
        travelController.cheapestRoute("bug", "Guangzhou");
        travelController.fastestRoute("Beijing", "bug");

        TravelController travelController2 = new TravelController();
        travelController2.load("data/cities02",
                "data/transport02");
        travelController2.mostDirectRoute("bug", "bug");

    }

}
