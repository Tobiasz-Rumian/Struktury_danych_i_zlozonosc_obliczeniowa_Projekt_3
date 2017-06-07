package algorithm;

import structures.BinaryHeapForNodes;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Tobiasz Rumian on 04.06.2017.
 */
public class Knapsack {
    private ArrayList<Node> list;
    private int backpackSize;
    private int bestValue = 0;
    private ArrayList<Node> best = new ArrayList<>();
    private int size = 0;
    private StringBuilder sb = new StringBuilder();
    public Knapsack(ArrayList<Node> list, int backpackSize) {
        this.list = list;
        this.backpackSize = backpackSize;
    }
    public String bruteForce(long seconds) {
        Node[] nodes;
        long tStart = System.currentTimeMillis();
        for (int i = 1; i < list.size(); i++) {
            nodes = new Node[i];
            combinationUtil(nodes, 0, 0, i);
            if((long)((double)(System.currentTimeMillis() - tStart) / 1000.0)>=seconds)return "Przekroczono czas oczekiwania!";
        }
        if (best == null) return "";
        for (Node n : best) {
            sb.
                    append(" S: ").append(n.getSize()).
                    append(" V: ").append(n.getValue()).
                    append(" $S ").append(size).
                    append(" $V ").append(bestValue).
                    append("\n");
        }
        return sb.toString();
    }

    private void combinationUtil(Node[] data, int start, int index, int r) {
        if (index == r) {
            int size = 0, value = 0;
            for (Node n : data) {
                size += n.getSize();
                value += n.getValue();
            }
            if (this.backpackSize < size) return;
            if (value > bestValue){
                this.size=size;
                bestValue = value;
                ArrayList<Node> x = new ArrayList<>();
                x.addAll(Arrays.asList(data).subList(0, r));
                best = x;
            }
            return;
        }
        for (int i = start; i <= list.size() - 1 && list.size() - i >= r - index; i++) {
            data[index]=list.get(i);
            combinationUtil(data, i + 1, index + 1, r);
        }
    }

    public String greedy(boolean compareByValue) {
        BinaryHeapForNodes binaryHeapForNodes = new BinaryHeapForNodes(list.size(),compareByValue);
        for (Node n:list) binaryHeapForNodes.push(n);
        System.out.print(binaryHeapForNodes.read());
        Node node = binaryHeapForNodes.pop();
        while(node!=null){
            if(node.getSize()+size<backpackSize){
                best.add(node);
                size+=node.getSize();
                bestValue+=node.getValue();
            }
            node = binaryHeapForNodes.pop();
        }
        if (best == null) return "";
        for (Node n : best) {
            sb.
                    append(" S: ").append(n.getSize()).
                    append(" V: ").append(n.getValue()).
                    append(" $S ").append(size).
                    append(" $V ").append(bestValue).
                    append("\n");
        }
        return sb.toString();
    }

    public String dynamic() {
        int i, w, k;
        int K[][] = new int[list.size() + 1][backpackSize + 1];
        for (i = 0; i <= list.size(); i++) {
            for (w = 0; w <= backpackSize; w++) {
                if (i == 0 || w == 0) K[i][w] = 0;
                else if (list.get(i - 1).getSize() <= w)
                    K[i][w] = Integer.max(list.get(i - 1).getValue() + K[i - 1][w - list.get(i - 1).getSize()], K[i - 1][w]);
                else K[i][w] = K[i - 1][w];
            }
        }
        //Pobieranie elementów dodanych do plecaka przez cofanie
        i = list.size();
        k = backpackSize;
        while (i > 0 && k > 0) {
            if (K[i][k] != K[i - 1][k]) {
                best.add(list.get(i - 1));
                k = k - list.get(i - 1).getSize();
            }
            i--;
        }
        for (Node n : best) size += n.getSize();
        bestValue = K[list.size()][backpackSize];
        //Wypisywanie elementów
        for (Node n : best) {
            sb.
                    append(" S: ").append(n.getSize()).
                    append(" V: ").append(n.getValue()).
                    append(" $S ").append(size).
                    append(" $V ").append(bestValue).
                    append("\n");
        }
        return sb.toString();
    }
}
