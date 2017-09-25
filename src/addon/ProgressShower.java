package addon;

import java.util.Collections;

public class ProgressShower {
   /**
    * Funkcja pozwalająca na zobaczenie postępu.
    *
    * @param now   Wartość chwilowa.
    * @param end   Wartość końcowa.
    * @param time  Czas wykonywania obliczenia.
    * @param label Dodatkowa informacja do wyświetlenia.
    */
   public static void showProgress(int now, int end, long time, String label) {
      StringBuilder string = new StringBuilder(140);
      int percent = (now * 100 / end);
      string
              .append('\r')
              .append(String.join("", Collections.nCopies(percent == 0 ? 2 : 2 - (int) (Math.log10(percent)), " ")))
              .append(String.format(" %d%% [", percent))
              .append(String.join("", Collections.nCopies(percent, "=")))
              .append('>')
              .append(String.join("", Collections.nCopies(100 - percent, " ")))
              .append(']')
              .append(String.format(" %d/%d    %d    %s", now, end, time, label));
      System.out.print(string);
   }
}
