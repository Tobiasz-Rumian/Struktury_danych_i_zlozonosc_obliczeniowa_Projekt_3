package view;

import addon.FileLoader;
import addon.Task;
import enums.Algorithm;

import java.util.Scanner;

import static addon.Test.runTests;
import static enums.Task.KNAPSACK;
import static enums.Task.SALESMAN;

/**
 * Klasa reprezentująca widok
 *
 * @author Tobiasz Rumian.
 */
public class View {

   private Task task;

   /**
    * Główna pętla programu, wyświetla główne menu.
    */
   private View() {
      showWelcomeMessage();
      int selectedTask;
      do {
         showMainMenuMessage();
         selectedTask = select("Podaj numer zadania:", 0, 3);
         switch (selectedTask) {
            case 1:
               selectTask(KNAPSACK);
               break;
            case 2:
               selectTask(SALESMAN);
               break;
            case 3:
               runTests();
         }
      } while (selectedTask != 0);

   }

   /**
    * Funkcja formatująca tytuł.
    *
    * @param title Tekst do sformatowania.
    * @return Zwraca sformatowany tytuł.
    */
   public static String title(String title) {
      return "===========" + title.toUpperCase() + "===========\n";
   }

   /**
    * Funkcja obsługują wybieranie numeru przez użytkownika.
    * Obsługuje wybór z konsoli.
    *
    * @param message Wiadomość do wyświetlenia użytkownikowi.
    * @param min     Minimalna akceptowalna wartość.
    * @param max     Maksymalna akceptowalna wartość.
    * @return Zwraca odpowiedź użytkownika.
    */
   public static int select(String message, Integer min, Integer max) {
      do {
         printMessage(message);
         Scanner in = new Scanner(System.in);
         int i = Integer.parseInt(in.nextLine());
         if (i <= max && i >= min) {
            return i;
         }
      } while (true);
   }

   /**
    * Funkcja pozwalająca wyświetlić wiadomość na ekranie.
    *
    * @param message Wiadomość do wyświetlenia.
    */
   public static void printMessage(String message) {
      System.out.println(message + "\n");
   }

   public static void printErrorMessage(String message) {
      System.err.println(message + "\n");
   }

   public static void main(String[] args) {
      new View();
   }

   private void showWelcomeMessage() {
      printMessage("Witaj " + System.getProperty("user.name") + "." + "\n" +
              "Uruchomiles projekt nr 3 autorstwa Tobiasza Rumiana." + "\n" +
              "Rozsiadz sie wygodnie i wybierz co chcesz zrobic.");
   }

   private void showMainMenuMessage() {
      printMessage(View.title("menu glowne") +
              "1. Dyskretny problem plecakowy\n" +
              "2. Asymetryczny problem komiwojażera\n" +
              "3. Wykonaj pełny test\n" +
              "0. Wyjscie");
   }

   /**
    * Funkcja pozwalająca na wybranie zadania wykonywanego na strukturze.
    */
   private void selectTask(enums.Task task) {
      this.task = new Task(task);
      Integer selectedTask;
      do {
         showSelectTaskMessage();
         selectedTask = View.select("Podaj numer zadania:", 0, 2);
         goToSelectedTask(selectedTask);
      } while (selectedTask != 0);
   }

   private void goToSelectedTask(int selectedTask) {
      if (selectedTask == 1) {
         task = FileLoader.loadFromFile(task);
         printMessage("Załadowano pomyślnie");
      } else if (selectedTask == 2) {
         printMessage(task.getAlgorithm(chooseAlgorithm()));
      }
   }

   private void showSelectTaskMessage() {
      printMessage(View.title("wybor zadania") +
              "1. Wczytaj z pliku" + "\n" +
              "2. Uruchom algorytm" + "\n" +
              "0. Wyjscie");
   }

   /**
    * Funkcja pozwalająca na wybór, przez użytkownika, algorytmu.
    * Wybór odbywa się w konsoli.
    *
    * @return Zwraca wybrany algorytm.
    */
   private Algorithm chooseAlgorithm() {
      View.printMessage("Dostępne algorytmy");
      for (int i = 0; i < task.getAvailableAlgorithms().size(); i++) {
         View.printMessage((i + 1) + ". " + task.getAvailableAlgorithms().get(i).toString());
      }
      return task.getAvailableAlgorithms().get(View.select(
              "Podaj numer wybranego algorytmu",
              1,
              task.getAvailableAlgorithms().size()) - 1);
   }
}
