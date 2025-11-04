//Name: Tushar Kshirsagar
//PRN: 123B1F050

import java.util.*;

class ReliefItem {
    String itemName;
    double weightKg;
    double importance;    
    boolean canDivide;   

    public ReliefItem(String itemName, double weightKg, double importance, boolean canDivide) {
        this.itemName = itemName;
        this.weightKg = weightKg;
        this.importance = importance;
        this.canDivide = canDivide;
    }
    public double importancePerKg() {
        return importance / weightKg;
    }
}

public class Assignment3 {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("=== Emergency Relief Supply Distribution ===");

        System.out.print("Enter number of relief items: ");
        int itemCount = input.nextInt();
        input.nextLine(); 

        if (itemCount <= 0) {
            System.out.println("No items to load. Exiting.");
            input.close();
            return;
        }

        List<ReliefItem> supplies = new ArrayList<>();

        for (int i = 0; i < itemCount; i++) {
            System.out.println("\nItem #" + (i + 1));

            System.out.print("Name: ");
            String name = input.nextLine();

            System.out.print("Weight (kg): ");
            double weight = input.nextDouble();
            if (weight <= 0) {
                System.out.println("Invalid weight. Must be > 0. Exiting.");
                input.close();
                return;
            }

            System.out.print("Importance Value: ");
            double value = input.nextDouble();
            if (value < 0) {
                System.out.println("Invalid importance. Must be >= 0. Exiting.");
                input.close();
                return;
            }

            System.out.print("Divisible? (1 = yes, 0 = no): ");
            boolean divisible = input.nextInt() == 1;
            input.nextLine(); 

            supplies.add(new ReliefItem(name, weight, value, divisible));
        }
        System.out.print("\nEnter boat capacity (kg): ");
        double boatCapacity = input.nextDouble();
        if (boatCapacity <= 0) {
            System.out.println("Boat capacity must be > 0. Exiting.");
            input.close();
            return;
        }

        long startTime = System.nanoTime();

        supplies.sort((a, b) -> Double.compare(b.importancePerKg(), a.importancePerKg()));

        double totalImportance = 0;
        double remainingCapacity = boatCapacity;

        System.out.println("\n=== Supplies Loaded on Boat ===");

        for (ReliefItem item : supplies) {
            if (remainingCapacity <= 0) break; 

            if (item.canDivide) {
                double loadWeight = Math.min(item.weightKg, remainingCapacity);
                double loadImportance = item.importancePerKg() * loadWeight;
                totalImportance += loadImportance;
                remainingCapacity -= loadWeight;

                System.out.printf(" - %s: %.2f kg, Importance: %.2f%n", item.itemName, loadWeight, loadImportance);
            } else {
                if (item.weightKg <= remainingCapacity) {
                    totalImportance += item.importance;
                    remainingCapacity -= item.weightKg;

                    System.out.printf(" - %s: %.2f kg, Importance: %.2f%n", item.itemName, item.weightKg, item.importance);
                }
            }
        }

        System.out.printf("\nTotal importance carried: %.2f%n", totalImportance);

        long endTime = System.nanoTime();
        System.out.printf("Execution time: %.3f ms%n", (endTime - startTime) / 1_000_000.0);

        input.close();
    }
}


