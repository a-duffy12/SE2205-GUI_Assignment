package sample;

import java.util.*;

public class Graph {

	private String dfs = ""; // required to deal with javafx
	private HashMap<Node, LinkedList<Node>> adjacencyMap;

	public Graph() {

		adjacencyMap = new HashMap<>();
	}

	public void addEdgeHelper(Node a, Node b) {

		LinkedList<Node> tmp = adjacencyMap.get(a);

		if (tmp != null) {
			tmp.remove(b);
		} else
			tmp = new LinkedList<>();
		tmp.add(b);
		adjacencyMap.put(a, tmp);
	}

	public void addEdge(Node source, Node destination) {

		if (!adjacencyMap.keySet().contains(source))
			adjacencyMap.put(source, null);

		if (!adjacencyMap.keySet().contains(destination))
			adjacencyMap.put(destination, null);

		addEdgeHelper(source, destination);
		addEdgeHelper(destination, source);

	}

	public boolean hasEdge(Node source, Node destination) {

		return adjacencyMap.containsKey(source) && adjacencyMap.get(source).contains(destination);
	}

	public void resetNodesVisited() {
		for (Node n : adjacencyMap.keySet()) {
			n.unvisit();
		}

	}

	public String BFS(Node node) {

		// string return, required for GUI component
		String bfs = "";
		
		// code to account for the chance that the node in the argument does not exist
		// in the graph
		if (node == null)
			return bfs;

		// create a queue
		LinkedList<Node> queue = new LinkedList<>(); // use a linked list as the queue
		queue.add(node); // add the first node to the queue

		// loop to remove first element in queue while the queue has a first element
		// available to move
		while (!queue.isEmpty()) {

			Node curFirst = queue.removeFirst(); // removes the first element in the queue

			// code to handle the event that a visited node is revisited
			if (curFirst.isVisited())
				continue; // skips to the next available node that has not yet been visited

			// a visited node is marked as such
			curFirst.visit();
			bfs += (curFirst.name + " "); // adds the node being visited to the output

			// create a list of all the current node's neighbouring nodes
			LinkedList<Node> allNeighbours = adjacencyMap.get(curFirst);

			// check to see if the list of neighboring nodes is empty
			if (allNeighbours == null)
				continue; // if there are no neighbors, the search continues

			for (Node neighbour : allNeighbours) {

				// add all unvisited neighboring nodes to the queue (list of nodes to visit)
				if (!neighbour.isVisited()) {
					queue.add(neighbour);
				}
			}
		}
		
		bfs += "\n"; // clears the row for the next node

		return bfs; 
	}

	public void DFS(Node node) {

		// visits the first node, arbitrarily chosen
		node.visit();
		dfs += (node.name + " "); // adds the name of the visited nodes, required for GUI portion

		// creates a queue using a linked list for all the neighboring nodes of a given
		// node
		LinkedList<Node> allNeighbours = adjacencyMap.get(node);
		if (allNeighbours == null)
			return; // if there are no neighboring nodes, return up a level, as there are no more
					// possible nodes to visit

		// search through all neighbouring nodes to find one that has not been visited
		for (Node neighbour : allNeighbours) {
			if (!neighbour.isVisited()) // checks to see if this node has been visited
				DFS(neighbour); // recursively check the unvisited neighbours
		}

	}

	public String DFSforGUI(Node node) {

		this.DFS(node); // takes in the dfs of a node
		return this.dfs; // outputs it as a string
	}

	public String printEdges() {

		// string to output, required for GUI component
		String edges = "";
		
		// runs for every node in the graph
		for (Node node : adjacencyMap.keySet()) {

			edges += ("Node " + node.name + " has an edge towards: "); // outputs to the console which node is
																				// being looked at

			// runs for all the neighbors of the given node
			for (Node neighbor : adjacencyMap.get(node)) {

				edges += (neighbor.name + " "); // outputs to the console the nodes connected to the current node
														// in question
			}

			edges += "\n"; // clears the line for the next node to be displayed
		}

		return edges; // outputs the string of edges
	}

}