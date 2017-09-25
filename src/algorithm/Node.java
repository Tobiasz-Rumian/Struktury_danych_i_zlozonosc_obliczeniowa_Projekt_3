package algorithm;

/**
 * Created by Tobiasz Rumian on 03.06.2017.
 */
public class Node implements Comparable {
   private int size;
   private int value;

   public Node(int size, int value) {
      this.size = size;
      this.value = value;
   }

   public int getSize() {
      return size;
   }

   public void setSize(int size) {
      this.size = size;
   }

   public int getValue() {
      return value;
   }

   public void setValue(int value) {
      this.value = value;
   }

   @Override
   public int compareTo(Object o) {
      if (o.getClass() != Node.class) {
         return 0;
      }
      if ((float) (((Node) o).getValue()) / (float) (((Node) o).getSize()) > value / size) {
         return -1;
      }
      if ((float) (((Node) o).getValue()) / (float) (((Node) o).getSize()) < value / size) {
         return 1;
      }
      return 0;
   }

   public int compareTo1(Object o) {
      if (o.getClass() != Node.class) {
         return 0;
      }
      if (((Node) o).getValue() < value) {
         return -1;
      }
      if (((Node) o).getValue() > value) {
         return 1;
      }
      return 0;
   }
}
