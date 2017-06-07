package addon;

import algorithm.Knapsack;
import algorithm.Node;
import algorithm.Salesman;
import enums.Algorithm;
import structures.AdjacencyMatrix;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

/**
 * Klasa reprezentująca zadanie do wykonania.
 *
 * @author Tobiasz Rumian
 */
public class Task {
    private enums.Task typeOfTask;
    private ArrayList<Node> knapsackList;
    private int knapsackSize;
    private AdjacencyMatrix adjacencyMatrix;

    public Task(enums.Task typeOfTask) {
        this.typeOfTask = typeOfTask;
    }

    public void setKnapsackListAndSize(ArrayList<Node> knapsackList, int knapsackSize) {
        this.knapsackList = knapsackList;
        this.knapsackSize = knapsackSize;
    }

    public void setAdjacencyMatrix(AdjacencyMatrix adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
    }

    public enums.Task getTypeOfTask() {
        return typeOfTask;
    }


    /**
     * Pokazuje dostępne dla danego zadania algorytmy.
     *
     * @return Tablica dostępnych algorytmów.
     */
    public ArrayList<Algorithm> getAvailableAlgorithms() {
        ArrayList<Algorithm> algorithms = new ArrayList<>();
        switch (typeOfTask) {
            case KNAPSACK:
                algorithms.add(Algorithm.BRUTEFORCE);
                //algorithms.add(Algorithm.GREEDY);
                algorithms.add(Algorithm.DYNAMIC);
                break;
            case SALESMAN:
                algorithms.add(Algorithm.BRUTEFORCE);
                algorithms.add(Algorithm.GREEDY);
                break;
        }
        return algorithms;
    }


    /**
     * Wykonuje zadany algorytm.
     *
     * @param algorithm typ algorytmu.
     */
    public BigDecimal testAlgorithm(Algorithm algorithm, ArrayList<Node> arrayList, int N) {
        TimeTracker timeTracker = new TimeTracker();
        Knapsack knapsack = new Knapsack(arrayList, N);
        switch (algorithm) {
            case BRUTEFORCE: timeTracker.start();
                knapsack.bruteForce(10);
                return timeTracker.getElapsedTime();
            case DYNAMIC: timeTracker.start();
                knapsack.dynamic();
                return timeTracker.getElapsedTime();
        }
        return null;
    }

    public BigDecimal testAlgorithm(Algorithm algorithm, AdjacencyMatrix adjacencyMatrix) {
        TimeTracker timeTracker = new TimeTracker();
        Salesman salesman = new Salesman(adjacencyMatrix);
        switch (algorithm) {
            case BRUTEFORCE:
                timeTracker.start();
                salesman.bruteForce(10);
                return timeTracker.getElapsedTime();
            case GREEDY: timeTracker.start();
                salesman.greedy();
                return timeTracker.getElapsedTime();
        }
        return null;
    }

    /**
     * Zwraca wynik algorytmudla postaci macierzowej oraz listowej.
     *
     * @param algorithm typ algorytmu.
     * @return Wynik algorytmu.
     */
    public String getAlgorithm(Algorithm algorithm) {
        switch (typeOfTask) {
            case KNAPSACK:
                Knapsack knapsack = new Knapsack(knapsackList, knapsackSize);
                switch (algorithm) {
                    case BRUTEFORCE: return knapsack.bruteForce(10);
                    //case GREEDY:
                    //    if(View.select("Sortuj kopiec po wartości?(0-nie, 1-tak)",0,1)==0)return knapsack.greedy(false);
                    //    return knapsack.greedy(true);
                    case DYNAMIC:
                        return knapsack.dynamic();
                }
                break;
            case SALESMAN:
                Salesman salesman = new Salesman(adjacencyMatrix);
                switch (algorithm) {
                    case BRUTEFORCE: return salesman.bruteForce(10);
                    case GREEDY: return salesman.greedy();
                }
                break;
        }

        return null;
    }

    /**
     * Generuje losowy graf.
     *
     * @param graphOrder Ilość wierzchołków grafu.
     */
    public AdjacencyMatrix generateRandomGraph(int graphOrder) {
        AdjacencyMatrix adjacencyMatrix = new AdjacencyMatrix(graphOrder);
        Random random = new Random();
        for (int i = 0; i < graphOrder; i++) {
            for (int j = i + 1; j < graphOrder; j++) {
                adjacencyMatrix.add(i, j, random.nextInt(100));
                adjacencyMatrix.add(j, i, random.nextInt(100));
            }
        }
        return adjacencyMatrix;
    }
    public ArrayList<Node> generateKnapsack(int size){
        Random random = new Random();
        ArrayList<Node> knapsack = new ArrayList<>(size);
        for (int i=0;i<size;i++) knapsack.add(new Node(random.nextInt(10),random.nextInt(100)));
        return knapsack;
    }
}
