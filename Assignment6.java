//Name: Soham Kurhade
//PRN: 123B1F053

//SOLUTION:
import java.util.*;

class Item {
    int id;
    String name;
    int weight;
    int utility;
    boolean perishable;

    public Item(int id, String name, int weight, int utility, boolean perishable) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.utility = utility;
        this.perishable = perishable;
    }
}

public class Assignment6 {

    public static int knapsackBruteForce(List<Item> items, int W, int n) {
        if (n == 0 || W == 0) return 0;
        Item current = items.get(n - 1);
        if (current.weight > W)
            return knapsackBruteForce(items, W, n - 1);
        else
            return Math.max(
                    current.utility + knapsackBruteForce(items, W - current.weight, n - 1),
                    knapsackBruteForce(items, W, n - 1)
            );
    }

    public static int knapsackDP(List<Item> items, int W) {
        int n = items.size();
        int[][] dp = new int[n + 1][W + 1];
        for (int i = 0; i <= n; i++) {
            for (int w = 0; w <= W; w++) {
                if (i == 0 || w == 0)
                    dp[i][w] = 0;
                else if (items.get(i - 1).weight <= w)
                    dp[i][w] = Math.max(
                            items.get(i - 1).utility + dp[i - 1][w - items.get(i - 1).weight],
                            dp[i - 1][w]
                    );
                else
                    dp[i][w] = dp[i - 1][w];
            }
        }
        int res = dp[n][W];
        int w = W;
        System.out.println("\nOptimal Set of Items (DP):");
        for (int i = n; i > 0 && res > 0; i--) {
            if (res != dp[i - 1][w]) {
                System.out.println(items.get(i - 1).name);
                res -= items.get(i - 1).utility;
                w -= items.get(i - 1).weight;
            }
        }
        return dp[n][W];
    }

    public static double knapsackGreedy(List<Item> items, int W) {
        items.sort((a, b) -> {
            double ratioA = (double) a.utility / a.weight + (a.perishable ? 1.0 : 0);
            double ratioB = (double) b.utility / b.weight + (b.perishable ? 1.0 : 0);
            return Double.compare(ratioB, ratioA);
        });
        int curWeight = 0;
        double totalUtility = 0.0;
        System.out.println("\nSelected Items (Greedy Approximation):");
        for (Item item : items) {
            if (curWeight + item.weight <= W) {
                curWeight += item.weight;
                totalUtility += item.utility;
                System.out.println(item.name);
            }
        }
        return totalUtility;
    }

    public static void multiTruckAllocation(List<Item> items, int[] truckCapacities) {
        System.out.println("\nMulti-Truck Allocation:");
        int truckNo = 1;
        for (int capacity : truckCapacities) {
            System.out.println("\nTruck " + truckNo + " (Capacity: " + capacity + " kg)");
            int utility = knapsackDP(items, capacity);
            System.out.println("Total Utility for Truck " + truckNo + ": " + utility);
            truckNo++;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Item> items = new ArrayList<>();

        // Original + New Items
        items.add(new Item(1, "Medical Kits", 10, 60, true));
        items.add(new Item(2, "Food Supplies", 20, 100, true));
        items.add(new Item(3, "Water Bottles", 30, 120, false));
        items.add(new Item(4, "Blankets", 25, 80, false));
        items.add(new Item(5, "Tents", 40, 150, false));
        items.add(new Item(6, "First Aid Kits", 8, 70, true));
        items.add(new Item(7, "Fuel Cans", 35, 130, false));
        items.add(new Item(8, "Batteries", 12, 90, false));
        items.add(new Item(9, "Clothes", 18, 60, false));
        items.add(new Item(10, "Flashlights", 5, 40, false));
        items.add(new Item(11, "Satellite Phone", 6, 110, false));
        items.add(new Item(12, "Dry Fruits", 10, 85, true));
        items.add(new Item(13, "Power Banks", 7, 75, false));
        items.add(new Item(14, "Oxygen Cylinders", 32, 200, false));
        items.add(new Item(15, "Instant Meals", 15, 95, true));

        System.out.print("Enter Truck Capacity (kg): ");
        int W = sc.nextInt();

        long startBF = System.nanoTime();
        int bruteResult = knapsackBruteForce(items, W, items.size());
        long endBF = System.nanoTime();
        double bruteTime = (endBF - startBF) / 1e6;
        System.out.println("\n--- Brute Force Approach ---");
        System.out.println("Max Utility: " + bruteResult);
        System.out.printf("Execution Time: %.3f ms%n", bruteTime);

        long startDP = System.nanoTime();
        int dpResult = knapsackDP(items, W);
        long endDP = System.nanoTime();
        double dpTime = (endDP - startDP) / 1e6;
        System.out.println("\n--- Dynamic Programming Approach ---");
        System.out.println("Max Utility (DP): " + dpResult);
        System.out.printf("Execution Time: %.3f ms%n", dpTime);

        long startGR = System.nanoTime();
        double greedyResult = knapsackGreedy(items, W);
        long endGR = System.nanoTime();
        double greedyTime = (endGR - startGR) / 1e6;
        System.out.println("\n--- Greedy Approximation Approach ---");
        System.out.println("Approx Utility (Greedy): " + greedyResult);
        System.out.printf("Execution Time: %.3f ms%n", greedyTime);

        int[] truckCapacities = { W, W + 20, W + 40};
        multiTruckAllocation(items, truckCapacities);

        System.out.println("\nPerformance Comparison Summary:");
        System.out.printf("Brute Force Time: %.3f ms%n", bruteTime);
        System.out.printf("Dynamic Programming Time: %.3f ms%n", dpTime);
        System.out.printf("Greedy Approximation Time: %.3f ms%n", greedyTime);

        sc.close();
    }
}
