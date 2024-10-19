import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Pokemon implements Comparable<Pokemon>
{
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

    public String getId() 
    {
        return id;
    }

    public void setId(String id) 
    {
        this.id = id;
    }

    public int getGeneration() 
    {
        return generation;
    }

    public void setGeneration(int generation) 
    {
        this.generation = generation;
    }

    public String getName() 
    {
        return name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getDescription() 
    {
        return description;
    }

    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String[] getTypes() 
    {
        return types;
    }

    public void setTypes(String[] types) 
    {
        this.types = types;
    }

    public String[] getAbilities() 
    {
        return abilities;
    }

    public void setAbilities(String[] abilities) 
    {
        this.abilities = abilities;
    }

    public double getWeight() 
    {
        return weight;
    }

    public void setWeight(double weight) 
    {
        this.weight = weight;
    }

    public double getHeight() 
    {
        return height;
    }

    public void setHeight(double height) 
    {
        this.height = height;
    }

    public int getCaptureRate() 
    {
        return captureRate;
    }

    public void setCaptureRate(int captureRate) 
    {
        this.captureRate = captureRate;
    }

    public boolean isLegendary() 
    {
        return isLegendary;
    }

    public void setIsLegendary(boolean isLegendary) 
    {
        this.isLegendary = isLegendary;
    }

    public LocalDate getCaptureDate() 
    {
        return captureDate;
    }

    public void setCaptureDate(LocalDate captureDate) 
    {
        this.captureDate = captureDate;
    }

    void ler(String csvLine) 
    {
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

    private LocalDate parseDate(String dateStr) 
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(dateStr, formatter);
    }

    String imprimir() 
    {
        StringBuilder sb = new StringBuilder();
        sb.append("[#").append(getId()).append(" -> ").append(getName()).append(": ").append(getDescription()).append(" - [");

        String[] types = getTypes();
        for (int i = 0; i < types.length; i++) 
        {
            sb.append("'").append(types[i]).append("'");
            if (i < types.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("] - ");

        sb.append("[");
        String[] abilities = getAbilities();
        for (int i = 0; i < abilities.length; i++) 
        {
            sb.append("'").append(abilities[i]).append("'"); // Mantém as aspas
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
    public int compareTo(Pokemon other)
    {
        return this.getName().compareTo(other.getName());
    }
}

class MergeSortPokemon 
{
    int comparacoes = 0;
    int movimentacoes = 0;

    public void sort(Pokemon[] array) 
    {
        if (array.length < 2) 
        {
            return; // Array já está ordenado
        }
        int mid = array.length / 2;

        Pokemon[] left = new Pokemon[mid];
        Pokemon[] right = new Pokemon[array.length - mid];

        System.arraycopy(array, 0, left, 0, mid);
        System.arraycopy(array, mid, right, 0, array.length - mid);

        sort(left);
        sort(right);

        merge(array, left, right);
    }

    private void merge(Pokemon[] array, Pokemon[] left, Pokemon[] right) {
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            comparacoes++; // Incrementa a contagem de comparações
            if (compare(left[i], right[j]) <= 0) {
                array[k++] = left[i++];
                movimentacoes++; // Incrementa a contagem de movimentações
            } else {
                array[k++] = right[j++];
                movimentacoes++; // Incrementa a contagem de movimentações
            }
        }        
        while (i < left.length) {
            array[k++] = left[i++];
            movimentacoes++; // Incrementa a contagem de movimentações
        }
        while (j < right.length) {
            array[k++] = right[j++];
            movimentacoes++; // Incrementa a contagem de movimentações
        }        
    }

    private int compare(Pokemon p1, Pokemon p2) {
        int typeComparison = compareTypes(p1.getTypes(), p2.getTypes());
        if (typeComparison == 0) {
            return p1.getName().compareTo(p2.getName()); // Ordena por name em caso de empate
        }
        return typeComparison;
    }

    private int compareTypes(String[] types1, String[] types2) {
        if (types1.length == 0 && types2.length == 0) return 0;
        if (types1.length == 0) return -1;
        if (types2.length == 0) return 1;
        return types1[0].compareTo(types2[0]); // Comparar pelo primeiro tipo
    }
}

public class OrdenacaoMergesort
{
    public static Pokemon getPokemonById(String id, ArrayList<Pokemon> list)
    {
        Pokemon retorno = null;
        for (Pokemon pokemon : list) 
        {
            if(pokemon.getId().equals(id))
            {
                retorno = pokemon;
            }
        }
        return retorno;
    }

    public static void main(String[] args) {
        int count = 0;
        ArrayList<Pokemon> pokemonsList = new ArrayList<>();
        long startTime = System.currentTimeMillis(); // Início da contagem do tempo

        try (Scanner scanner = new Scanner(System.in);
             BufferedReader br = new BufferedReader(new FileReader("/tmp/pokemon.csv"))) {

            ArrayList<Pokemon> foundPokemons = new ArrayList<>();
            String linha;

            br.readLine(); // Ignora a primeira linha (cabeçalho)

            while ((linha = br.readLine()) != null) {
                Pokemon pokemon = new Pokemon();
                pokemon.ler(linha);
                foundPokemons.add(pokemon);
            }

            String id;
            while (true) {
                id = scanner.nextLine();
                if (id.equals("FIM")) break; // Condição de parada
                Pokemon aux = getPokemonById(id, foundPokemons);
                if (aux != null) {
                    pokemonsList.add(aux);
                    count++; // Incrementa apenas se o Pokémon foi encontrado
                }
            }

        } catch (IOException e) {
            System.err.println("Erro de I/O: " + e.getMessage());
        }

        // Verifica se há Pokémon para ordenar
        if (count > 0) {
            Pokemon[] array = new Pokemon[count]; 
            int index = 0;
            for (Pokemon poke : pokemonsList) {
                array[index++] = poke;
            }

            MergeSortPokemon mergeSort = new MergeSortPokemon();
            mergeSort.sort(array);

            long endTime = System.currentTimeMillis(); // Fim da contagem do tempo
            long totalTime = endTime - startTime; // Tempo total de execução

            try (FileWriter logWriter = new FileWriter("848324_mergesort.txt")) {
                logWriter.write("848324\t" + mergeSort.comparacoes + "\t" + mergeSort.movimentacoes + "\t" + totalTime + "ms\n");
            } catch (IOException e) {
                System.err.println("Erro ao escrever o arquivo de log: " + e.getMessage());
            }

            for (int i = 0; i < count; i++) {
                System.out.println(array[i].imprimir());
            }
        } else {
            System.out.println("Nenhum Pokémon encontrado para ordenar.");
        }
    }
}
