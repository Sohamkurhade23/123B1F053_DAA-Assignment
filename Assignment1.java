//Name: Tushar Kshirsagar
//PRN: 123B1F050

import java.util.*;

class CustomerOrder {
    String orderId;
    long timestamp;

    public CustomerOrder(String orderId, long timestamp) {
        this.orderId = orderId;
        this.timestamp = timestamp;
    }
}

public class Assignment1 {
    public static void mergeSort(CustomerOrder[] orders, int left, int right) {
        if (orders == null || orders.length == 0) return;
        if (left < right) {
            int mid = left + (right - left) / 2;

            mergeSort(orders, left, mid);       
            mergeSort(orders, mid + 1, right);    
            merge(orders, left, mid, right);    
        }
    }
    
    private static void merge(CustomerOrder[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        CustomerOrder[] leftArr = new CustomerOrder[n1];
        CustomerOrder[] rightArr = new CustomerOrder[n2];

        for (int i = 0; i < n1; i++)
            leftArr[i] = arr[left + i];
        for (int j = 0; j < n2; j++)
            rightArr[j] = arr[mid + 1 + j];

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            if (leftArr[i].timestamp <= rightArr[j].timestamp)
                arr[k++] = leftArr[i++];
            else
                arr[k++] = rightArr[j++];
        }
        while (i < n1) arr[k++] = leftArr[i++];
        while (j < n2) arr[k++] = rightArr[j++];
    }

    public static CustomerOrder[] sampleData() {
        return new CustomerOrder[]{
            new CustomerOrder("O01", 1700000100000L),
            new CustomerOrder("O02", 1699999999000L),
            new CustomerOrder("O03", 1700000200000L),
            new CustomerOrder("O04", 1699999950000L),
            new CustomerOrder("O05", 1700000300000L),
            new CustomerOrder("O06", 1700000000000L),
            new CustomerOrder("O07", 1699999900000L),
            new CustomerOrder("O08", 1700000500000L),
            new CustomerOrder("O09", 1699999850000L),
            new CustomerOrder("O10", 1700000400000L),
            new CustomerOrder("O11", 1700000000000L)
        };
    }

    public static void main(String[] args) {
        CustomerOrder[] orders = sampleData();

        System.out.println("========== CUSTOMER ORDERS ==========\n");
        System.out.println("Before Sorting:");
        for (CustomerOrder c : orders)
            System.out.println("Order ID: " + c.orderId + " | Timestamp: " + c.timestamp);

        long start = System.nanoTime();
        mergeSort(orders, 0, orders.length - 1);
        long end = System.nanoTime();

        System.out.println("\nAfter Sorting (by Timestamp):");
        for (CustomerOrder c : orders)
            System.out.println("Order ID: " + c.orderId + " | Timestamp: " + c.timestamp);

        double timeTaken = (end - start) / 1_000_000.0;
        System.out.println("\nTime Taken: " + timeTaken + " ms");

    }

}

