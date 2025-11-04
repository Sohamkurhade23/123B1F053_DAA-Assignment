//Name: Soham Kurhade
//PRN: 123B1F053
//SOLUTION:
import java.util.*;

class Edge {
    int targetNode;
    int travelMinutes;

    public Edge(int targetNode, int travelMinutes) {
        this.targetNode = targetNode;
        this.travelMinutes = travelMinutes;
    }
}

public class Assignment4 {

    // Dijkstra's algorithm to calculate shortest travel times from source
    public static int[] shortestTravelTimes(List<List<Edge>> cityMap, int source, int[] previous) {
        int n = cityMap.size();
        int[] timeTo = new int[n];
        Arrays.fill(timeTo, Integer.MAX_VALUE);
        Arrays.fill(previous, -1);
        timeTo[source] = 0;

        // Priority queue: [node, current travel time]
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.add(new int[]{source, 0});

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int node = current[0], time = current[1];

            if (time > timeTo[node]) continue;

            for (Edge e : cityMap.get(node)) {
                int neighbor = e.targetNode;
                int newTime = timeTo[node] + e.travelMinutes;

                if (newTime < timeTo[neighbor]) {
                    timeTo[neighbor] = newTime;
                    previous[neighbor] = node;
                    pq.add(new int[]{neighbor, newTime});
                }
            }
        }
        return timeTo;
    }

    // Print the shortest path from source to destination
    public static void displayPath(int[] previous, int destination) {
        if (previous[destination] == -1) {
            System.out.print(destination);
            return;
        }
        displayPath(previous, previous[destination]);
        System.out.print(" -> " + destination);
    }

    // Find the nearest hospital based on travel times
    public static int getNearestHospital(int[] travelTimes, int[] hospitalNodes) {
        int nearest = -1;
        int minTime = Integer.MAX_VALUE;
        for (int h : hospitalNodes) {
            if (travelTimes[h] < minTime) {
                minTime = travelTimes[h];
                nearest = h;
            }
        }
        return nearest;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter number of intersections: ");
        int intersections = input.nextInt();

        System.out.print("Enter number of roads: ");
        int roads = input.nextInt();

        // Initialize city graph
        List<List<Edge>> cityGraph = new ArrayList<>();
        for (int i = 0; i < intersections; i++) cityGraph.add(new ArrayList<>());

        System.out.println("Enter roads as: startNode endNode travelTime(mins)");
        for (int i = 0; i < roads; i++) {
            int u = input.nextInt(), v = input.nextInt(), t = input.nextInt();
            cityGraph.get(u).add(new Edge(v, t));
            cityGraph.get(v).add(new Edge(u, t)); // undirected road
        }

        System.out.print("Ambulance starting intersection: ");
        int startNode = input.nextInt();

        System.out.print("Number of hospitals: ");
        int numHospitals = input.nextInt();

        int[] hospitals = new int[numHospitals];
        System.out.print("Enter hospital node indices: ");
        for (int i = 0; i < numHospitals; i++) hospitals[i] = input.nextInt();

        int[] previousNodes = new int[intersections];

        System.out.println("\nCalculating shortest travel paths...");
        long startTime = System.nanoTime();
        int[] travelTimes = shortestTravelTimes(cityGraph, startNode, previousNodes);
        long endTime = System.nanoTime();

        int nearestHospital = getNearestHospital(travelTimes, hospitals);

        System.out.println("\n--- Emergency Route ---");
        System.out.println("Ambulance start: " + startNode);
        System.out.println("Hospital nodes: " + Arrays.toString(hospitals));

        if (nearestHospital == -1 || travelTimes[nearestHospital] == Integer.MAX_VALUE) {
            System.out.println("No reachable hospital!");
        } else {
            System.out.println("Nearest hospital: " + nearestHospital +
                    " (Travel time: " + travelTimes[nearestHospital] + " mins)");
            System.out.print("Shortest path: ");
            displayPath(previousNodes, nearestHospital);
            System.out.println();
        }

        System.out.printf("Execution time: %.3f ms%n", (endTime - startTime) / 1_000_000.0);

        input.close();
    }
}


