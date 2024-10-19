import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Pokemon implements Comparable<Pokemon> {
    private String id;
    private int generation;
    private String name;
    private String description;
    private String[] types;
    private String[] abilities;
    private double weight;
    private double height;
    private int captureRate;
    private boolean isLegendary;
    private LocalDate captureDate;

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public int getGeneration() { return generation; }
    public void setGeneration(int generation) { this.generation = generation; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String[] getTypes() { return types; }
    public void setTypes(String[] types) { this.types = types; }
    public String[] getAbilities() { return abilities; }
    public void setAbilities(String[] abilities) { this.abilities = abilities; }
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }
    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }
    public int getCaptureRate() { return captureRate; }
    public void setCaptureRate(int captureRate) { this.captureRate = captureRate; }
    public boolean isLegendary() { return isLegendary; }
    public void setIsLegendary(boolean isLegendary) { this.isLegendary = isLegendary; }
    public LocalDate getCaptureDate() { return captureDate; }
    public void setCaptureDate(LocalDate captureDate) { this.captureDate = captureDate; }

    // Método para ler os dados de uma linha CSV
    void ler(String csvLine) {
        String[] data = csvLine.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
        
        setId(data[0].trim());
        setGeneration(Integer.parseInt(data[1].trim()));
        setName(data[2].trim());
        setDescription(data[3].trim());

        List<String> typesList = new ArrayList<>();
        typesList.add(data[4].trim());
        if (!data[5].trim().isEmpty()) typesList.add(data[5].trim());
        setTypes(typesList.toArray(new String[0]));

        // Processar habilidades sem aspas
        String abilitiesStr = data[6].replaceAll("[\\[\\]\"']", "").trim(); // Remove colchetes e aspas
        setAbilities(abilitiesStr.split(",\\s*")); // Divide as habilidades e remove espaços extras

        setWeight(data[7].trim().isEmpty() ? 0 : Double.parseDouble(data[7].trim()));
        setHeight(data[8].trim().isEmpty() ? 0 : Double.parseDouble(data[8].trim()));
        setCaptureRate(data[9].trim().isEmpty() ? 0 : Integer.parseInt(data[9].trim()));
        setIsLegendary(data[10].trim().equals("1") || data[10].trim().equalsIgnoreCase("true"));

        LocalDate date = parseDate(data[11].trim());
        setCaptureDate(date);
    }

    private LocalDate parseDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(dateStr, formatter);
    }

    String imprimir() {
        StringBuilder sb = new StringBuilder();
        sb.append("[#").append(getId()).append(" -> ").append(getName()).append(": ").append(getDescription()).append(" - [");

        String[] types = getTypes();
        for (int i = 0; i < types.length; i++) {
            sb.append("'").append(types[i]).append("'");
            if (i < types.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("] - [");

        String[] abilities = getAbilities();
        for (int i = 0; i < abilities.length; i++) {
            sb.append("'").append(abilities[i]).append("'");
            if (i < abilities.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("] - ");
        sb.append(getWeight()).append("kg - ");
        sb.append(getHeight()).append("m - ");
        sb.append(getCaptureRate()).append("% - ");
        sb.append(isLegendary() ? "true" : "false").append(" - ");
        sb.append(getGeneration()).append(" gen] - ");
        sb.append(getCaptureDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        return sb.toString();
    }

    @Override
    public int compareTo(Pokemon a2) {
        return this.getName().compareTo(a2.getName());
    }
}

class SelectionSortPokemon {
    private Pokemon[] array;
    private int tam;
    
    public int comparacoes = 0;  // Contador de comparações
    public int movimentacoes = 0;  // Contador de movimentações

    public SelectionSortPokemon(Pokemon[] array, int tam) {
        this.array = array;
        this.tam = tam;
    }

    public void sort(int k) {
        k = Math.min(k, tam);

        for (int i = 0; i < k - 1; i++) { 
            int menor = i;
            for (int j = i + 1; j < tam; j++) {
                comparacoes++; 
                if (array[j] != null && array[menor] != null && array[j].getName().compareTo(array[menor].getName()) < 0) {
                    menor = j;
                }
            }
            swap(i, menor);
            movimentacoes++;
        }
    }

    private void swap(int i, int j) {
        Pokemon temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public void imprimir() {
        for (int i = 0; i < tam; i++) {
            if (array[i] != null) { 
                System.out.println(array[i].imprimir());
            }
        }
    }
}

public class SelecaoParcial{
    public static Pokemon getPokArrayList(String id, ArrayList<Pokemon> array) {
        for (Pokemon pokemon : array) {
            if (pokemon.getId().equals(id)) {
                return pokemon;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        int count = 0;
        ArrayList<Pokemon> pokamao = new ArrayList<>();
        long startTime = System.currentTimeMillis(); // Início da contagem do tempo

        try (Scanner scanner = new Scanner(System.in);
             BufferedReader br = new BufferedReader(new FileReader("/tmp/pokemon.csv"))) {

            ArrayList<Pokemon> pokemonsEncontrados = new ArrayList<>();
            String linha;

            br.readLine(); // Ignora a primeira linha (cabeçalho)

            while ((linha = br.readLine()) != null) {
                Pokemon pokemon = new Pokemon();
                pokemon.ler(linha);
                pokemonsEncontrados.add(pokemon);
            }

            String id;
            do {
                id = scanner.nextLine();
                if (!id.equals("FIM")) {
                    Pokemon aux = getPokArrayList(id, pokemonsEncontrados);
                    if (aux != null) {
                        pokamao.add(aux);
                        count++;
                    }
                }
            } while (!id.equals("FIM"));

        } catch (IOException e) {
            System.err.println("Erro de I/O: " + e.getMessage());
        }

        Pokemon[] array = new Pokemon[count]; 
        int count2 = 0;

        for (Pokemon poke : pokamao) {
            array[count2++] = poke;
        }

        SelectionSortPokemon sort = new SelectionSortPokemon(array, count);
        int k = Math.min(10, count);  
        sort.sort(k);  

        long endTime = System.currentTimeMillis(); 
        long totalTime = endTime - startTime; 

        try (FileWriter logWriter = new FileWriter("848324_selecaoparcial.txt")) {
            logWriter.write("848324\t" + sort.comparacoes + "\t" + sort.movimentacoes + "\t" + totalTime + "ms\n");
        } catch (IOException e) {
            System.err.println("Erro ao escrever o arquivo de log: " + e.getMessage());
        }

        for (int i = 0; i < Math.min(10, count); i++) {
            System.out.println(array[i].imprimir());
        }
    }
}
