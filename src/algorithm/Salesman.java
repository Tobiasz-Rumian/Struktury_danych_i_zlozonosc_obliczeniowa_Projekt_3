package algorithm;

import structures.AdjacencyMatrix;

/**
 * Created by Tobiasz Rumian on 04.06.2017.
 */
public class Salesman {
    private AdjacencyMatrix adjacencyMatrix;
    private StringBuilder sb = new StringBuilder();
    private int v0, d, dh, sptr, shptr;
    private int[] S, Sh;                       // Stosy w tablicy
    private boolean[] visited;                    // Tablica odwiedzin
    private int graphOrder;//Liczba wierzchołków

    public Salesman(AdjacencyMatrix adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
        this.graphOrder = adjacencyMatrix.getSize();
    }

    public String bruteForce(long seconds) {
        S = new int[graphOrder];
        Sh = new int[graphOrder];
        visited = new boolean[graphOrder];
        for (int i = 0; i < graphOrder; i++) visited[i] = false;
        sptr = shptr = 0;
        // Rozpoczynamy algorytm
        long tStart = System.currentTimeMillis();
        d = Integer.MAX_VALUE;
        dh = v0 = 0;
        if(!TSP(v0,seconds,tStart))return "Przekroczono czas oczekiwania!";
        if (sptr != 0) {
            for (int i = 0; i < sptr; i++) sb.append(S[i]).append(" ");
            sb.append(v0).append("\n");
            sb.append("d = ").append(d).append("\n");
        } else sb.append("Brak cyklu Hamiltona").append("\n");
        return sb.toString();
    }

    // Rekurencyjna procedura poszukiwania cyklu Hamiltona
    // o najmniejszej sumie wag krawędzi
    // v - wierzchołek bieżący
    //----------------------------------------------------
    private boolean TSP(int v,long seconds,long tStart) {
        int u;
        if((System.currentTimeMillis() - tStart / 1000.0)>=seconds)return false;
        Sh[shptr++] = v;                // zapamiętujemy na stosie bieżący wierzchołek

        if (shptr < graphOrder)                   // jeśli brak ścieżki Hamiltona, to jej szukamy
        {
            visited[v] = true;            // Oznaczamy bieżący wierzchołek jako odwiedzony
            for (u = 0; u < graphOrder; u++)        // Przeglądamy sąsiadów wierzchołka v
                if (adjacencyMatrix.getMatrixElement(v, u) != Integer.MIN_VALUE && !visited[u])  // Szukamy nieodwiedzonego jeszcze sąsiada
                {
                    dh += adjacencyMatrix.getMatrixElement(v, u);            // Dodajemy wagę krawędzi v-u do sumy
                    if(!TSP(u,seconds,tStart))return false;                   // Rekurencyjnie wywołujemy szukanie cyklu Hamiltona
                    dh -= adjacencyMatrix.getMatrixElement(v, u);            // Usuwamy wagę krawędzi z sumy
                }
            visited[v] = false;           // Zwalniamy bieżący wierzchołek
        } else if (adjacencyMatrix.getMatrixElement(v0, v) != Integer.MIN_VALUE)            // Jeśli znaleziona ścieżka jest cyklem Hamiltona
        {
            dh += adjacencyMatrix.getMatrixElement(v, v0);              // to sprawdzamy, czy ma najmniejszą sumę wag
            if (dh < d)                    // Jeśli tak,
            {
                d = dh;                     // To zapamiętujemy tę sumę
                for (u = 0; u < shptr; u++)  // oraz kopiujemy stos Sh do S
                    S[u] = Sh[u];
                sptr = shptr;
            }
            dh -= adjacencyMatrix.getMatrixElement(v, v0);               // Usuwamy wagę krawędzi v-v0 z sumy
        }
        shptr--;                        // Usuwamy bieżący wierzchołek ze ścieżki
        return true;
    }
}
