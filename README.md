# OOP - ex2
### Ex2 contains the following code packages:
- api- containing the implementation of a directed weighted graph.
- gameClient- containing the implementation of the Pokemon game.

The Pokemon game's goal is for the agents to catch as many pokemons as they can in 60 seconds. 
The game contains 24 level options (0-23), each has a different amount of agents and a different amount of Pokemons to catch at any given moment.
At each level the pokemons are located on the edges of a directed weighted graph, and the agents can move on the graph only in the direction of each edge.
The graph is drawn each time from the data of one of the json files in the data package, which contains different graph stuctures (by providing a list of nodes and edges).
The higher level the player is choosing- the more complicated the graph structure is, making it hader to catch the pokemons.

# How does it work?
The src-api package contains a number of classes to implement a graph, such as: NodeData (implementing the node_data interface), edgeData (implementing the edge_data interface)
GeoLocation (implementing the geo_location interface), DWGraph_DS (implementing the directed_weighted_graph interface) and the DWGraph_Algo (implementing the dw_graph_algorithms
interface). Each class is being used to create the final graph structure that the game is using, and to activate the game. 
The agents on the graph are constantly calculating the distance between them and the pokemons on the graph, using the Dijkstra algorithm. This algorithms allows to find the
shortest path to a target node in a directed weighted graph in a fast and efficient way. Therefor the agents have the ability to catch as many pokemons as they can without
wasting time, which helps them achiving a high grade in the game.
For more information on the algorithm: https://brilliant.org/wiki/dijkstras-short-path-finder/

# Other Algorithms
DWGraph_Algo class represents the regular Graph Theory algorithms including:
- clone(); (copy)
- init(graph);
- isConnected(); - using the BFS algorithm.
- double shortestPathDist(int src, int dest); - using the Dijkstra algorithm.
- List<node_data> shortestPath(int src, int dest); - using the Dijkstra algorithm.
- Save(file); - using the json serialization.
- Load(file); - using the DW_GraphJsonDeserializer class that is implementing JsonDeserializer<directed_weighted_graph>.

# Graph implementation
DWGraph_DS class represents a directed weighted graph, represented by a HashMap.
The reason we chose to represent the graph in a HashMap is because it allows easy access to each node in the graph, therefor questions like whether two nodes are connected 
and how many edges are in the graph can be answered in O(1). Adding a new node to the graph, or connecting two nodes in the graph with an edge, can also be done easily in O(1). 
Connecting simply requires adding each of the nodes to the other node's neighbors list with the wanted weight, and removing an edge requires removing each of the nodes from 
the other node's neighbors list. 

# How do I play?
### Clone repository:
$ git clone https://github.com/Orya-s/OOP-ex2.git
Then enter you ID and the wanted game level and press start!


See our [reference style][Wiki] for full documentation, examples, operational details and other information.
[Wiki]: https://github.com/Orya-s/OOP-ex2/wiki


