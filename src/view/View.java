package view;

import addon.FileChooser;
import addon.Results;
import algorithm.Node;
import enums.Algorithm;
import enums.Task;
import structures.AdjacencyMatrix;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Klasa reprezentująca widok
 *
 * @author Tobiasz Rumian.
 */
public class View {
    private Results results = new Results();//Obiekt zawierający wyniki testów.
    private addon.Task task;

    /**
     * Główna pętla programu, wyświetla główne menu.
     */
    private View() {
        message("Witaj " + System.getProperty("user.name") + "." + "\n" +
                "Uruchomiles projekt nr 3 autorstwa Tobiasza Rumiana." + "\n" +
                "Rozsiadz sie wygodnie i wybierz co chcesz zrobic.", false);
        while (true) {
            message(View.title("menu glowne") +
                    "1. Dyskretny problem plecakowy\n" +
                    "2. Asymetryczny problem komiwojażera\n" +
                    "3. Wykonaj pełny test\n" +
                    "0. Wyjscie", false);

            switch (select("Podaj numer zadania:", 0, 3)) {
                case 1: task = new addon.Task(Task.KNAPSACK);
                    break;
                case 2: task = new addon.Task(Task.SALESMAN);
                    break;
                case 3: createTests();
                    return;
                case 0: return;
            }
            selectTask();
        }
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
            try {
                message(message, false);
                Scanner in = new Scanner(System.in);
                int i = Integer.parseInt(in.nextLine());
                if (i <= max && i >= min) return i;
            } catch (NumberFormatException ignored) {
            }
        } while (true);
    }

    /**
     * Funkcja pozwalająca wyświetlić wiadomość na ekranie.
     *
     * @param message Wiadomość do wyświetlenia.
     * @param error   Jeżeli true, wyświetla wiadomość jako błąd.
     */
    private static void message(String message, Boolean error) {
        if (error) System.err.println(message + "\n");
        else System.out.println(message + "\n");
    }

    /**
     * Funkcja pozwalająca na wybranie zadania wykonywanego na strukturze.
     */
    private void selectTask() {
        do {
            message(View.title("wybor zadania") +
                    "1. Wczytaj z pliku" + "\n" +
                    "2. Uruchom algorytm" + "\n" +
                    "0. Wyjscie", false);

            switch (View.select("Podaj numer zadania:", 0, 2)) {
                case 1: loadFromFile();
                    message("Załadowano pomyślnie", false);
                    break;
                case 2: message(task.getAlgorithm(chooseAlgorithm()), false);
                    break;
                case 0: return;
            }
        } while (true);
    }

    /**
     * Funkcja pozwalająca na załadowanie danych z pliku tekstowego do struktury.
     * Wyświetla okno pozwalające na wybór pliku.
     */
    private void loadFromFile() {
        FileChooser fileChooser = new FileChooser();
        if (fileChooser.getPath() == null) return;
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
            for (String s : arrayList)
                nodes.add(new Node(
                        Integer.parseInt(s.substring(0, s.indexOf(" "))),

                        Integer.parseInt((s.substring(s.indexOf(" ") + 1, s.length())).trim())));

            task.setKnapsackListAndSize(nodes, backpackSize);
        } else {
            String x = arrayList.get(0);
            int graphOrder = Integer.parseInt(x.trim());
            arrayList.remove(0);
            AdjacencyMatrix adjacencyMatrix = new AdjacencyMatrix(graphOrder);
            for (int i = 0; i < graphOrder; i++)
                for (int j = 0; j < graphOrder; j++) {
                    if (j == graphOrder - 1)
                        adjacencyMatrix.add(i, j, Integer.parseInt((arrayList.get(i).substring(0, arrayList.get(i).length())).trim()));
                    else {
                        adjacencyMatrix.add(i, j, Integer.parseInt(arrayList.get(i).substring(0, arrayList.get(i).indexOf(" ")).trim()));
                        arrayList.set(i, arrayList.get(i).substring(arrayList.get(i).indexOf(" ") + 1, arrayList.get(i).length()).trim());
                    }
                }
            task.setAdjacencyMatrix(adjacencyMatrix);
        }
    }


    /**
     * Funkcja pozwalająca na wykonanie pełnych testów.
     */
    private void createTests() {
        final int howManyRepeats = 100;
        int[] graphOrders = {3, 5, 10, 15, 20};
        int[] graphOrders1 = {5,10,20};
        int[] backpackSize = {5, 10, 20};
        int[] nodes = {3, 5, 7, 10, 12};
        String label;
        Algorithm[] algorithmsSalesman = {Algorithm.BRUTEFORCE, Algorithm.GREEDY};
        Algorithm[] algorithmsKnapsack = {Algorithm.BRUTEFORCE, Algorithm.DYNAMIC};
        BigDecimal time;
        addon.Task task = new addon.Task(Task.KNAPSACK);
        for (Algorithm algorithm : algorithmsKnapsack) {
            for (int node : nodes) {
                ArrayList<Node> arrayList = task.generateKnapsack(node);
                for (int size : backpackSize) {
                    time = new BigDecimal(0).setScale(0, RoundingMode.CEILING);
                    for (int i = 0; i < howManyRepeats; i++) {
                        time = time.add(task.testAlgorithm(algorithm, arrayList, size));
                        showProgress(i, howManyRepeats, time.longValue(), "     " +
                                algorithm.toString() + "  " + node + "  " + size + " " + "Knapsack");
                    }
                    time = time.divide(BigDecimal.valueOf(howManyRepeats), RoundingMode.UP);
                    label = "KNAPSACK\t" + algorithm.toString() + "\t" + node + "\t" + size;
                    message(time.toString(), false);
                    results.add(label, time.longValue());
                }
            }
            results.save();
        }
        task = new addon.Task(Task.SALESMAN);
        for (Algorithm algorithm : algorithmsSalesman) {
            if(algorithm==Algorithm.BRUTEFORCE){
                for (int graphOrder : graphOrders1) {
                    AdjacencyMatrix adjacencyMatrix = task.generateRandomGraph(graphOrder);
                    time = new BigDecimal(0).setScale(0, RoundingMode.CEILING);
                    for (int i = 0; i < howManyRepeats; i++) {
                        time = time.add(task.testAlgorithm(algorithm, adjacencyMatrix));
                        showProgress(i, howManyRepeats, time.longValue(), "     " +
                                algorithm.toString() + "  " + graphOrder + "  " + "Salesman");
                    }
                    time = time.divide(BigDecimal.valueOf(howManyRepeats), RoundingMode.UP);
                    label = "SALESMAN\t" + algorithm.toString() + "\t" + graphOrder;
                    message(time.toString(), false);
                    results.add(label, time.longValue());
                }
                results.save();
            }else{
                for (int graphOrder : graphOrders) {
                    AdjacencyMatrix adjacencyMatrix = task.generateRandomGraph(graphOrder);
                    time = new BigDecimal(0).setScale(0, RoundingMode.CEILING);
                    for (int i = 0; i < howManyRepeats; i++) {
                        time = time.add(task.testAlgorithm(algorithm, adjacencyMatrix));
                        showProgress(i, howManyRepeats, time.longValue(), "     " +
                                algorithm.toString() + "  " + graphOrder + "  " + "Salesman");
                    }
                    time = time.divide(BigDecimal.valueOf(howManyRepeats), RoundingMode.UP);
                    label = "SALESMAN\t" + algorithm.toString() + "\t" + graphOrder;
                    message(time.toString(), false);
                    results.add(label, time.longValue());
                }
                results.save();
            }

        }
        results.save();
        results.clear();
    }

    /**
     * Funkcja pozwalająca na wybór, przez użytkownika, algorytmu.
     * Wybór odbywa się w konsoli.
     *
     * @return Zwraca wybrany algorytm.
     */
    private Algorithm chooseAlgorithm() {
        View.message("Dostępne algorytmy", false);
        for (int i = 0; i < task.getAvailableAlgorithms().size(); i++)
            View.message((i + 1) + ". " + task.getAvailableAlgorithms().get(i).toString(), false);
        return task.getAvailableAlgorithms().get(View.select(
                "Podaj numer wybranego algorytmu",
                1,
                task.getAvailableAlgorithms().size()) - 1);
    }

    /**
     * Funkcja pozwalająca na zobaczenie postępu.
     *
     * @param now   Wartość chwilowa.
     * @param end   Wartość końcowa.
     * @param time  Czas wykonywania obliczenia.
     * @param label Dodatkowa informacja do wyświetlenia.
     */
    private void showProgress(int now, int end, long time, String label) {
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

    public static void main(String[] args) {
        new View();
    }
}
