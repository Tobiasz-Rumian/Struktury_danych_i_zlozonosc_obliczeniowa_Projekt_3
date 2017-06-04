package algorithm;

import structures.BinaryHeap;

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
    public Knapsack(ArrayList<Node> list, int backpackSize) {
        this.list = list;
        this.backpackSize = backpackSize;
    }
    public String bruteForce() {
        Node[] nodes;
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < list.size(); i++) {
            nodes = new Node[i];
            combinationUtil(nodes, 0, 0, i);
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
            for (int j=0; j<r; j++)
                System.out.print(data[j].getSize()+" ");
            System.out.println("");
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
        StringBuilder sb = new StringBuilder();
        BinaryHeap binaryHeap = new BinaryHeap(list.size(),compareByValue);
        for (Node n:list) binaryHeap.push(n);
        System.out.print(binaryHeap.read());
        Node node = binaryHeap.pop();
        while(node!=null){
            if(node.getSize()+size<backpackSize){
                best.add(node);
                size+=node.getSize();
                bestValue+=node.getValue();
            }
            node = binaryHeap.pop();
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
}