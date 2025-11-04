/**
Name: Kinjal Amit Heda
PRN: 123B1F048
Date: 14/10/25

Scenario: Optimizing Delivery Routes for a Logistics Company
A leading logistics company, SwiftShip, is responsible for delivering packages to multiple cities.
To minimize fuel costs and delivery time, the company needs to find the shortest possible route
that allows a delivery truck to visit each city exactly once and return to the starting point.
The company wants an optimized solution that guarantees the least cost route, considering:
● Varying distances between cities.
● Fuel consumption costs, which depend on road conditions.
● Time constraints, as deliveries must be completed within a given period.
Since there are N cities, a brute-force approach checking all (N-1)!permutations is infeasible
for large N (e.g., 20+ cities). Therefore, you must implement an LC (Least Cost) Branch and
Bound algorithm to find the optimal route while reducing unnecessary computations
efficiently.
**/
import java.util.*;

class Assignment8 {

    private int numCities;           
    private int[][] distanceMatrix;  
    private int[][] fuelCostMatrix;  
    private int maxDeliveryTime;     

    private int minTotalCost = Integer.MAX_VALUE;
    private List<Integer> optimalRoute;

    public SwiftShipTSP(int numCities, int[][] distanceMatrix, int[][] fuelCostMatrix, int maxDeliveryTime) {
        this.numCities = numCities;
        this.distanceMatrix = distanceMatrix;
        this.fuelCostMatrix = fuelCostMatrix;
        this.maxDeliveryTime = maxDeliveryTime;
        optimalRoute = new ArrayList<>();
    }

    private int calculateLowerBound(boolean[] visited, int currentCity) {
        int bound = 0;

        for (int city = 0; city < numCities; city++) {
            if (!visited[city]) {
                int minEdge = Integer.MAX_VALUE;
                for (int next = 0; next < numCities; next++) {
                    if (city != next && !visited[next]) {
                        minEdge = Math.min(minEdge, fuelCostMatrix[city][next]);
                    }
                }
                bound += (minEdge == Integer.MAX_VALUE) ? 0 : minEdge;
            }
        }

        int minReturn = Integer.MAX_VALUE;
        for (int city = 0; city < numCities; city++) {
            if (!visited[city]) minReturn = Math.min(minReturn, fuelCostMatrix[city][0]);
        }
        if (minReturn != Integer.MAX_VALUE) bound += minReturn;

        return bound;
    }

    // Recursive LC Branch and Bound 
    private void branchAndBound(List<Integer> currentRoute, boolean[] visited, int currentCity, int currentCost) {

        if (currentRoute.size() == numCities) {
            int totalCost = currentCost + fuelCostMatrix[currentCity][0]; 
            int totalTime = calculateTotalTime(currentRoute) + distanceMatrix[currentCity][0];

            if (totalCost < minTotalCost && totalTime <= maxDeliveryTime) {
                minTotalCost = totalCost;
                optimalRoute = new ArrayList<>(currentRoute);
                optimalRoute.add(0); 
            }
            return;
        }

        for (int nextCity = 0; nextCity < numCities; nextCity++) {
            if (!visited[nextCity]) {
                int nextCost = currentCost + fuelCostMatrix[currentCity][nextCity];
                int lowerBound = nextCost + calculateLowerBound(visited, nextCity);

                if (lowerBound < minTotalCost) {
                    visited[nextCity] = true;
                    currentRoute.add(nextCity);

                    branchAndBound(currentRoute, visited, nextCity, nextCost);

                    visited[nextCity] = false;
                    currentRoute.remove(currentRoute.size() - 1);
                }
            }
        }
    }

    // Calculate Total Delivery Time for Route 
    private int calculateTotalTime(List<Integer> route) {
        int totalTime = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            totalTime += distanceMatrix[route.get(i)][route.get(i + 1)];
        }
        if (!route.isEmpty()) totalTime += distanceMatrix[route.get(route.size() - 1)][0]; // return to start
        return totalTime;
    }

    //  Method to Find Optimal Delivery Route 
    public void findOptimalRoute() {
        long startTime = System.currentTimeMillis();

        boolean[] visited = new boolean[numCities];
        List<Integer> currentRoute = new ArrayList<>();
        visited[0] = true; 
        currentRoute.add(0);

        branchAndBound(currentRoute, visited, 0, 0);

        long endTime = System.currentTimeMillis();

        System.out.println("Optimal Delivery Route (City Indexes): " + optimalRoute);
        System.out.println("Minimum Total Fuel Cost: " + minTotalCost);
        System.out.println("Estimated Delivery Time (km proxy): " + calculateTotalTime(optimalRoute));
        System.out.println("Time Taken: " + (endTime - startTime) + " milliseconds");
    }

    public static void main(String[] args) {
        int numCities = 4;

        int[][] distanceMatrix = {
            {0, 10, 15, 20},
            {10, 0, 35, 25},
            {15, 35, 0, 30},
            {20, 25, 30, 0}
        };

        int[][] fuelCostMatrix = {
            {0, 5, 8, 7},
            {5, 0, 12, 10},
            {8, 12, 0, 6},
            {7, 10, 6, 0}
        };

        int maxDeliveryTime = 100; 

        SwiftShipTSP optimizer = new SwiftShipTSP(numCities, distanceMatrix, fuelCostMatrix, maxDeliveryTime);
        optimizer.findOptimalRoute();
    }
}
