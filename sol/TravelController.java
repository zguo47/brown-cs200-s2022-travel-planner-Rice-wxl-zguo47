package sol;


import src.*;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * This class is the controller for the Travel Planner. It manages the method of
 * loading graph and the method of
 * calculating the path based on requirements.
 */
public class TravelController implements ITravelController<City, Transport> {

    // Why is this field of type TravelGraph and not IGraph?
    // Are there any advantages to declaring a field as a specific type rather
    // than the interface?
    // If this were of type IGraph, could you access methods in TravelGraph not
    // declared in IGraph?
    // Hint: perhaps you need to define a method!
    private TravelGraph graph;

    /**
     * A constructor for the TravelController.
     */
    public TravelController() {
    }

    /**
     * generates the graph from the input files. It uses a TravelCSVParser to
     * parse the input files.
     * @param citiesFile    the filename of the cities csv
     * @param transportFile the filename of the transportations csv
     * @return the message of whether succeeds or fails to load the graph.
     */
    @Override
    public String load(String citiesFile, String transportFile) {
        this.graph = new TravelGraph();
        TravelCSVParser parser = new TravelCSVParser();

        Function<Map<String, String>, Void> addVertex = map -> {
            this.graph.addVertex(new City(map.get("name")));
            return null; // need explicit return null to account for Void type
        };

        Function<Map<String, String>, Void> addEdge = map -> {
            this.graph.addEdge(this.graph.getCity(map.get("origin")),
                    new Transport
                    (this.graph.getCity(map.get("origin")),
                            this.graph.getCity(map.get("destination")),
                            TransportType.fromString(map.get("type")),
                            Double.parseDouble(map.get("price")),
                            Double.parseDouble(map.get("duration"))));
            return null; // need explicit return null to account for Void type
        };

        try {
            // pass in string for CSV and function to create City (vertex)
            // using city name
            parser.parseLocations(citiesFile, addVertex);
        } catch (IOException e) {
            return "Error parsing file: " + citiesFile;
        }

        try {
            // pass in string for CSV and function to create Transport (edge)
            // using city name
            parser.parseTransportation(transportFile, addEdge);
        } catch (IOException e) {
            return "Error parsing file: " + transportFile;
        }

        // hint: note that parseLocations and parseTransportation can
        //       throw IOExceptions. How can you handle these calls cleanly?

        return "Successfully loaded cities and transportation files.";
    }

    /**
     * finds the fastest route between the input source and destination.
     * It applies the Dijkstra algorithm.
     * @param source      the name of the source city
     * @param destination the name of the destination city
     * @return the fastest route in terms of a list of transports.
     */
    @Override
    public List<Transport> fastestRoute(String source, String destination) {
        Function<Transport, Double> getTime = transport -> {
            return transport.getMinutes();
        };
        City start = this.graph.getCity(source);
        City end = this.graph.getCity(destination);
        Dijkstra<City, Transport> newDijkstra = new Dijkstra<>();
        return newDijkstra.getShortestPath(this.graph, start, end, getTime);
    }

    /**
     * finds the cheapest route between the input source and destination.
     * It applies the Dijkstra algorithm.
     * @param source      the name of the source city
     * @param destination the name of the destination city
     * @return the cheapest route in terms of a list of transports.
     */
    @Override
    public List<Transport> cheapestRoute(String source, String destination) {

        Function<Transport, Double> getMoney = transport -> {
            return transport.getPrice();
        };
        City start = this.graph.getCity(source);
        City end = this.graph.getCity(destination);
        Dijkstra<City, Transport> newDijkstra = new Dijkstra<>();
        return newDijkstra.getShortestPath(this.graph, start, end, getMoney);
    }

    /**
     * finds the most direct route (least number of transports) between the
     * input source and destination.It applies
     * the BFS algorithm.
     * @param source      the name of the source city
     * @param destination the name of the destination city
     * @return the most direct route in terms of a list of transports.
     */
    @Override
    public List<Transport> mostDirectRoute(String source, String destination) {
        City start = this.graph.getCity(source);
        City end = this.graph.getCity(destination);
        BFS<City, Transport> newBFS = new BFS<>();
        return newBFS.getPath(this.graph, start, end);
    }

    /**
     * This method returns the created graph for the travel controller.
     * @return
     */
    public TravelGraph getGraph(){ return this.graph; }
}
