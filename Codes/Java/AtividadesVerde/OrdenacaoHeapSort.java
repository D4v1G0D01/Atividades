import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.nio.charset.Charset;

class Pokemon {

    // Atributos
    private int id;
    private int generation;
    private String name;
    private String description;
    private List<String> types;
    private List<String> abilities;
    private double weight;
    private double height;
    private int captureRate;
    private boolean isLegendary;
    private LocalDate captureDate;

    // Construtores
    public Pokemon() {
        id = 0;
        generation = 0;
        name = "";
        description = "";
        types = null;
        abilities = null;
        weight = 0.0;
        height = 0.0;
        captureRate = 0;
        isLegendary = false;
        captureDate = null;
    }

    public Pokemon(int id, int generation, String name, String description, List<String> types, List<String> abilities, double weight,
            double height, int captureRate, boolean isLegendary, LocalDate captureDate) {
        this.id = id;
        this.generation = generation;
        this.name = name;
        this.description = description;
        this.types = types;
        this.abilities = abilities;
        this.weight = weight;
        this.height = height;
        this.captureRate = captureRate;
        this.isLegendary = isLegendary;
        this.captureDate = captureDate;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public int getGeneration() {
        return generation;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getTypes() {
        return types;
    }

    public List<String> getAbilities() {
        return abilities;
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }

    public int getCaptureRate() {
        return captureRate;
    }

    public boolean isLegendary() {
        return isLegendary;
    }

    public LocalDate getCaptureDate() {
        return captureDate;
    }

    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "[#" + id + " -> " + name + ": " + description + " - " + String.join(", ", types) + " - "
                + String.join(", ", abilities) + " - " + weight + "kg - " + height + "m - " + captureRate + "% - "
                + isLegendary + " - " + generation + " gen] - " + captureDate.format(dtf);
    }

    public Pokemon clone() {
        return new Pokemon(id, generation, name, description, types, abilities, weight, height, captureRate, isLegendary, captureDate);
    }
}

public class OrdenacaoHeapSort {

    static Pokemon[] pokemons = new Pokemon[801];
    static Pokemon[] array = new Pokemon[801];
    static int n = 0;
    static int comp = 0;
    static long tempo;
    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void preencherPokedex() {

        String pokedex = "/tmp/pokemon.csv";
        // String pokedex = "../pokemon.csv";

        try {
            RandomAccessFile file = new RandomAccessFile(pokedex, "r");
            Charset charset = Charset.forName("UTF-8");
            file.readLine(); // Ignorar a primeira linha (cabeçalho)

            String linha;
            int i = 0;
            while ((linha = file.readLine()) != null) {

                linha = new String(linha.getBytes("ISO-8859-1"), charset);

                String[] dadospok = linha.split("\"");
                String habilidades = dadospok[1];
                String[] primeiraParte = dadospok[0].split(",", -1);
                String[] segundaParte = dadospok[2].split(",", -1);

                int id = Integer.parseInt(primeiraParte[0]);
                int generation = Integer.parseInt(primeiraParte[1]);
                String name = primeiraParte[2];
                String description = primeiraParte[3];

                List<String> types = new ArrayList<>();
                types.add(primeiraParte[4]);
                types.add(primeiraParte[5].isEmpty() ? null : primeiraParte[5]);

                List<String> abilities = new ArrayList<>();
                abilities.add(habilidades);

                double weight = segundaParte[1].isEmpty() ? 0 : Double .parseDouble(segundaParte[1]);
                double height = segundaParte[2].isEmpty() ? 0 : Double.parseDouble(segundaParte[2]);
                int captureRate = segundaParte[3].isEmpty() ? 0 : Integer.parseInt(segundaParte[3]);
                boolean isLegendary = segundaParte[4].charAt(0) == '0' ? false : true;
                LocalDate captureDate = LocalDate.parse(segundaParte[5], dtf);

                Pokemon pokemon = new Pokemon(id, generation, name, description, types, abilities, weight, height, captureRate, isLegendary, captureDate);

                pokemons[i] = pokemon;

                i++;
            }

            file.close();

        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    // Função para realizar a ordenação por Heapsort
    public static void ordenarPorHeapsort() {
        long inicio = System.nanoTime();

        buildHeap(array, n);

        for (int i = n - 1; i > 0; i--) {
            Pokemon temp = array[0];
            array[0] = array[i];
            array[i] = temp;

            heapify(array, i, 0);
        }

        long fim = System.nanoTime();
        tempo = (fim - inicio);
    }

    // Função para construir o heap
    private static void buildHeap(Pokemon[] array, int n) {
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i);
        }
    }

    // Função para heapify
    private static void heapify(Pokemon[] array, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && array[left].getHeight() > array[largest].getHeight()) {
            largest = left;
        }

        if (right < n && array[right].getHeight() > array[largest].getHeight()) {
            largest = right;
        }

        if (largest != i) {
            Pokemon temp = array[i];
            array[i] = array[largest];
            array[largest] = temp;

            heapify(array, n, largest);
        }
    }

    public static boolean isFim(String str) {
        return str.equals("FIM");
    }

    public static void criarLog(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("848324" + "\t" + tempo + "ns\t" + comp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {

        preencherPokedex();

        Scanner scan = new Scanner(System.in);

        String str = "";

        do {
            str = scan.nextLine();

            if (!isFim(str)) {
                array[n] = pokemons[Integer.parseInt(str) - 1].clone();
                n++;
            }

        } while (!isFim(str));

        ordenarPorHeapsort();

        for (int i = 0; i < n; i++) {
            System.out.println(array[i].toString());
        }

        criarLog("matrícula_heapsort.txt");

        scan.close();
    }
}