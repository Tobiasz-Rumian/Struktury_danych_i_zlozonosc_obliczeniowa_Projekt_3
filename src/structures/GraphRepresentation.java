package structures;

/**
 * Interfejs reprezentacji grafu.
 *
 * @author Tobiasz Rumian
 */
public interface GraphRepresentation {

   /**
    * Pozwala dodać krawędź do grafu.
    *
    * @param start  Początek krawędzi.
    * @param end    Koniec krawędzi.
    * @param weight Waga krawędzi.
    */
   void add(int start, int end, int weight);

   /**
    * Stopień wierzchołka.
    *
    * @param vertex numer wierzchołka.
    * @return Zwraca stopień wierzchołka.
    */
   int getDegree(int vertex);
}
