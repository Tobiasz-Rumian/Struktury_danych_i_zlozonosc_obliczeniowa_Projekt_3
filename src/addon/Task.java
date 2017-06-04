package addon;

import algorithm.Knapsack;
import algorithm.Node;
import algorithm.Salesman;
import enums.Algorithm;
import structures.AdjacencyMatrix;
import view.View;

import java.util.ArrayList;

/**
 * Klasa reprezentująca zadanie do wykonania.
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
     * @return Tablica dostępnych algorytmów.
     */
    public ArrayList<Algorithm> getAvailableAlgorithms() {
        ArrayList<Algorithm> algorithms = new ArrayList<>();
        switch (typeOfTask) {
            case KNAPSACK:
                algorithms.add(Algorithm.BRUTEFORCE);
                algorithms.add(Algorithm.GREEDY);
                algorithms.add(Algorithm.DYNAMIC);
                break;
            case SALESMAN:
                algorithms.add(Algorithm.BRUTEFORCE);
                break;
        }
        return algorithms;
    }


    /**
     * Wykonuje zadany algorytm.
     * @param algorithm typ algorytmu.
     */
    public void testAlgorithm(Algorithm algorithm){
        switch (algorithm) {

        }
    }

    /**
     * Zwraca wynik algorytmudla postaci macierzowej oraz listowej.
     * @param algorithm typ algorytmu.
     * @return Wynik algorytmu.
     */
    public String getAlgorithm(Algorithm algorithm) {
        switch (typeOfTask){
            case KNAPSACK:
                Knapsack knapsack = new Knapsack(knapsackList,knapsackSize);
                switch (algorithm) {
                    case BRUTEFORCE:return knapsack.bruteForce(1000);
                    case GREEDY:
                        if(View.select("Sortuj kopiec po wartości?(0-nie, 1-tak)",0,1)==0)return knapsack.greedy(false);
                        return knapsack.greedy(true);
                    case DYNAMIC:
                        return knapsack.dynamic();
                }
                break;
            case SALESMAN:
                Salesman salesman = new Salesman(adjacencyMatrix);
                switch (algorithm){
                    case BRUTEFORCE:return salesman.bruteForce(1000);
                }
                break;
        }

        return null;
    }
}
