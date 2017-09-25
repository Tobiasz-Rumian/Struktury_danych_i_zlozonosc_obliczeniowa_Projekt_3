package addon;

import algorithm.Node;
import enums.Task;
import structures.AdjacencyMatrix;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class FileLoader {

   /**
    * Funkcja pozwalająca na załadowanie danych z pliku tekstowego do struktury.
    * Wyświetla okno pozwalające na wybór pliku.
    */
   public static addon.Task loadFromFile(addon.Task task) {
      FileChooser fileChooser = new FileChooser();
      if (fileChooser.getPath() == null) {
         return task;
      }
      ArrayList<String> arrayList = new ArrayList<>();
      try (Stream<String> stream = Files.lines(Paths.get(fileChooser.getPath()))) {
         stream.filter(x -> !x.equals("")).forEach(arrayList::add);
      } catch (IOException e) {
         e.getMessage();
      }
      if (task.getTypeOfTask() == Task.KNAPSACK) {
         String x = arrayList.get(0);
         int backpackSize = Integer.parseInt(x.substring(0, x.indexOf(" ")));
         arrayList.remove(0);
         ArrayList<Node> nodes = new ArrayList<>();
         for (String s : arrayList) {
            nodes.add(new Node(
                    Integer.parseInt(s.substring(0, s.indexOf(" "))),

                    Integer.parseInt((s.substring(s.indexOf(" ") + 1, s.length())).trim())));
         }

         task.setKnapsackListAndSize(nodes, backpackSize);
      } else {
         String x = arrayList.get(0);
         int graphOrder = Integer.parseInt(x.trim());
         arrayList.remove(0);
         AdjacencyMatrix adjacencyMatrix = new AdjacencyMatrix(graphOrder);
         for (int i = 0; i < graphOrder; i++) {
            for (int j = 0; j < graphOrder; j++) {
               if (j == graphOrder - 1) {
                  adjacencyMatrix.add(i, j, Integer.parseInt((arrayList.get(i).substring(0, arrayList.get(i).length())).trim()));
               } else {
                  adjacencyMatrix.add(i, j, Integer.parseInt(arrayList.get(i).substring(0, arrayList.get(i).indexOf(" ")).trim()));
                  arrayList.set(i, arrayList.get(i).substring(arrayList.get(i).indexOf(" ") + 1, arrayList.get(i).length()).trim());
               }
            }
         }
         task.setAdjacencyMatrix(adjacencyMatrix);
      }
      return task;
   }

}
