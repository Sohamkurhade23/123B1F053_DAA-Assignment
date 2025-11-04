//Name: Soham Kurhade
//PRN: 123B1F053

//SOLUTION:
import java.util.*;

class Route {
    int destinationNode; // target node
    int travelCost;      // cost or time to travel

    public Route(int destinationNode, int travelCost) {
        this.destinationNode = destinationNode;
        this.travelCost = travelCost;
    }
}

public class Assignment5 {

    // Dynamic Programming approach to find shortest path in a multistage graph
    public static int[] computeOptimalPath(List<List<Route>> network, int totalNodes, int start, int end) {
        int[] minCost = new int[totalNodes];       // minimum cost to reach end from node i
        int[] nextStep = new int[totalNodes];      // next node in optimal path
        Arrays.fill(minCost, Integer.MAX_VALUE);
        Arrays.fill(nextStep, -1);

        minCost[end] = 0; // cost to reach destination from itself = 0

        // Process nodes in reverse order for DP
        for (int i = totalNodes - 2; i >= 0; i--) {
            for (Route r : network.get(i)) {
                if (minCost[r.destinationNode] != Integer.MAX_VALUE &&
                        r.travelCost + minCost[r.destinationNode] < minCost[i]) {

                    minCost[i] = r.travelCost + minCost[r.destinationNode];
                    nextStep[i] = r.destinationNode;
                }
            }
        }

        return nextStep;
    }

    // Display the optimal path from source to destination
    public static void showPath(int start, int[] nextStep) {
        int current = start;
        System.out.print(current);
        while (nextStep[current] != -1) {
            current = nextStep[current];
            System.out.print(" -> " + current);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter total number of nodes in the multistage network: ");
        int totalNodes = input.nextInt();

        List<List<Route>> networkGraph = new ArrayList<>();
        for (int i = 0; i < totalNodes; i++) networkGraph.add(new ArrayList<>());

        System.out.print("Enter number of routes: ");
        int totalRoutes = input.nextInt();

        System.out.println("Enter route details as: fromNode toNode cost/time");
        for (int i = 0; i < totalRoutes; i++) {
            int from = input.nextInt();
            int to = input.nextInt();
            int cost = input.nextInt();
            networkGraph.get(from).add(new Route(to, cost)); // directed route
        }

        System.out.print("Enter source node: ");
        int sourceNode = input.nextInt();

        System.out.print("Enter destination node: ");
        int destinationNode = input.nextInt();

        long startTime = System.nanoTime();
        int[] nextStep = computeOptimalPath(networkGraph, totalNodes, sourceNode, destinationNode);
        long endTime = System.nanoTime();

        System.out.println("\n--- Optimal Delivery Route ---");
        System.out.print("Path: ");
        showPath(sourceNode, nextStep);

        System.out.printf("Execution time: %.3f ms%n", (endTime - startTime) / 1_000_000.0);

        input.close();
    }
}



