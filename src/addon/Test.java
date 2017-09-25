package addon;

import algorithm.Node;
import enums.Algorithm;
import structures.AdjacencyMatrix;
import view.View;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import static addon.ProgressShower.showProgress;
import static enums.Task.KNAPSACK;
import static enums.Task.SALESMAN;

public class Test {

   private static final int HOW_MANY_REPEATS = 100;
   private static final int[] GRAPH_ORDERS = new int[] {3, 5, 10, 15, 20};
   private static final int[] GRAPH_ORDERS_1 = new int[] {5, 10, 20};
   private static final int[] BACKPACK_SIZE = new int[] {5, 10, 20};
   private static final int[] NODES = new int[] {3, 5, 7, 10, 12};
   private static final Algorithm[] ALGORITHMS_SALESMAN = new Algorithm[] {Algorithm.BRUTEFORCE, Algorithm.GREEDY};
   private static final Algorithm[] ALGORITHMS_KNAPSACK = new Algorithm[] {Algorithm.BRUTEFORCE, Algorithm.DYNAMIC};

   private static Results results = new Results();//Obiekt zawierający wyniki testów.

   /**
    * Funkcja pozwalająca na wykonanie pełnych testów.
    */
   public static void runTests() {
      runTestsForKnapsack();
      runTestsForSalesmanAlgorithm();
      saveResults();
      clearResults();
   }

   private static void runTestsForSalesmanAlgorithm() {
      Task task;
      task = new Task(SALESMAN);
      for (Algorithm algorithm : ALGORITHMS_SALESMAN) {
         int[] graphOrders = GRAPH_ORDERS;
         if (algorithm == Algorithm.BRUTEFORCE) {
            graphOrders = GRAPH_ORDERS_1;
         }
         runTestForDifferentGraphOrders(task, algorithm, graphOrders);
         saveResults();
      }
   }

   private static void runTestForDifferentGraphOrders(Task task, Algorithm algorithm, int[] graphOrders) {
      BigDecimal time;
      String label;
      for (int graphOrder : graphOrders) {
         AdjacencyMatrix adjacencyMatrix = task.generateRandomGraph(graphOrder);
         time = createBigDecimal();
         for (int i = 0; i < HOW_MANY_REPEATS; i++) {
            time = runSalesmanAlgorithm(time, task, algorithm, adjacencyMatrix, graphOrder, i);
         }
         time = divideByHowManyRepeats(time);
         label = createSalesmanTestLabel(algorithm, graphOrder);
         showTime(time);
         results.add(label, time.longValue());
      }
   }

   private static void showTime(BigDecimal time) {
      View.printMessage(time.toString());
   }

   private static BigDecimal runSalesmanAlgorithm(BigDecimal time, Task task, Algorithm algorithm, AdjacencyMatrix adjacencyMatrix, int graphOrder, int i) {
      BigDecimal timeForTestedAlgorithm = task.testAlgorithm(algorithm, adjacencyMatrix);
      time = time.add(timeForTestedAlgorithm);
      String restOfMessage = graphOrder + "  " + "Salesman";
      showTestedAlgorithmProgress(time, algorithm, i, restOfMessage);
      return time;
   }

   private static BigDecimal runKnapsackAlgorithm(BigDecimal time, Task task, Algorithm algorithm, int node, ArrayList<Node> arrayList, int size, int i) {
      BigDecimal timeForTestedAlgorithm = task.testAlgorithm(algorithm, arrayList, size);
      time = time.add(timeForTestedAlgorithm);
      String restOfMessage = node + "  " + size + " " + "Knapsack";
      showTestedAlgorithmProgress(time, algorithm, i, restOfMessage);
      return time;
   }

   private static void runTestForDifferentBackpackSizes(Task task, Algorithm algorithm, int node, ArrayList<Node> arrayList) {
      BigDecimal time;
      String label;
      for (int size : BACKPACK_SIZE) {
         time = createBigDecimal();
         for (int i = 0; i < HOW_MANY_REPEATS; i++) {
            time = runKnapsackAlgorithm(time, task, algorithm, node, arrayList, size, i);
         }
         time = divideByHowManyRepeats(time);
         label = createKnapsackTestLabel(algorithm, node, size);
         showTime(time);
         results.add(label, time.longValue());
      }
   }

   private static BigDecimal divideByHowManyRepeats(BigDecimal time) {
      time = time.divide(BigDecimal.valueOf(HOW_MANY_REPEATS), RoundingMode.UP);
      return time;
   }

   private static String createSalesmanTestLabel(Algorithm algorithm, int graphOrder) {
      String label;
      label = "SALESMAN\t" + algorithm.toString() + "\t" + graphOrder;
      return label;
   }

   private static String createKnapsackTestLabel(Algorithm algorithm, int node, int size) {
      String label;
      label = "KNAPSACK\t" + algorithm.toString() + "\t" + node + "\t" + size;
      return label;
   }

   private static void showTestedAlgorithmProgress(BigDecimal time, Algorithm algorithm, int i, String restOfMessage) {
      showProgress(i, HOW_MANY_REPEATS, time.longValue(), "     " +
              algorithm.toString() + "  " + restOfMessage);
   }

   private static void clearResults() {
      results.clear();
   }

   private static void runTestsForKnapsack() {
      Task task = new Task(KNAPSACK);
      runTestForDifferentTypesOfAlgorithm(task);
   }

   private static void runTestForDifferentTypesOfAlgorithm(Task task) {
      for (Algorithm algorithm : ALGORITHMS_KNAPSACK) {
         runTestForDifferentNumberOfNodes(task, algorithm);
         saveResults();
      }
   }

   private static void saveResults() {
      results.save();
   }

   private static void runTestForDifferentNumberOfNodes(Task task, Algorithm algorithm) {
      for (int node : NODES) {
         ArrayList<Node> arrayList = task.generateKnapsack(node);
         runTestForDifferentBackpackSizes(task, algorithm, node, arrayList);
      }
   }

   private static BigDecimal createBigDecimal() {
      BigDecimal time;
      time = new BigDecimal(0).setScale(0, RoundingMode.CEILING);
      return time;
   }
}
