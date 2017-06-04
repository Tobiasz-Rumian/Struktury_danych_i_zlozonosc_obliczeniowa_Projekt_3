package addon;

import algorithm.Knapsack;
import algorithm.Node;
import enums.Algorithm;
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
    public Task(enums.Task typeOfTask) {
        this.typeOfTask = typeOfTask;
    }

    public void setKnapsackListAndSize(ArrayList<Node> knapsackList, int knapsackSize) {
        this.knapsackList = knapsackList;
        this.knapsackSize = knapsackSize;
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
                break;
            case SALESMAN:
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
                    case BRUTEFORCE:return knapsack.bruteForce();
                    case GREEDY:
                        if(View.select("Sortuj kopiec po wartości?(0-nie, 1-tak)",0,1)==0)return knapsack.greedy(false);
                        return knapsack.greedy(true);
                }
                break;
            case SALESMAN:
                break;
        }

        return null;
    }
}
