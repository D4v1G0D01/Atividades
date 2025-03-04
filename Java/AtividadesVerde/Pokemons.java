import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Pokemon {
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

    // Métodos de acesso e modificação para os atributos do Pokémon

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    public String[] getAbilities() {
        return abilities;
    }

    public void setAbilities(String[] abilities) {
        this.abilities = abilities;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getCaptureRate() {
        return captureRate;
    }

    public void setCaptureRate(int captureRate) {
        this.captureRate = captureRate;
    }

    public boolean isLegendary() {
        return isLegendary;
    }

    public void setIsLegendary(boolean isLegendary) {
        this.isLegendary = isLegendary;
    }

    public LocalDate getCaptureDate() {
        return captureDate;
    }

    public void setCaptureDate(LocalDate captureDate) {
        this.captureDate = captureDate;
    }

    // Método para ler dados de uma linha CSV e preencher os atributos do Pokémon
    void lerDados(String csvLine) {
        String[] data = csvLine.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");

        setId(data[0].trim());
        setGeneration(Integer.parseInt(data[1].trim()));
        setName(data[2].trim());
        setDescription(data[3].trim());

        // Tratamento para tipos de Pokémon (até dois tipos)
        List<String> listaTipos = new ArrayList<>();
        listaTipos.add(data[4].trim());
        if (!data[5].trim().isEmpty()) listaTipos.add(data[5].trim());
        setTypes(listaTipos.toArray(new String[0]));

        // Processamento de habilidades, removendo colchetes e aspas
        String abilitiesStr = data[6].replaceAll("[\\[\\]\"']", "").trim();
        setAbilities(abilitiesStr.split(",\\s*")); 

        setWeight(data[7].trim().isEmpty() ? 0 : Double.parseDouble(data[7].trim()));
        setHeight(data[8].trim().isEmpty() ? 0 : Double.parseDouble(data[8].trim()));
        setCaptureRate(data[9].trim().isEmpty() ? 0 : Integer.parseInt(data[9].trim()));
        setIsLegendary(data[10].trim().equals("1") || data[10].trim().equalsIgnoreCase("true"));

        LocalDate date = parseDate(data[11].trim());
        setCaptureDate(date);
    }

    // Método auxiliar para converter uma string de data para o formato LocalDate
    private LocalDate parseDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(dateStr, formatter);
    }

    // Método para formatar as informações do Pokémon como string
    String exibirDados() {
        StringBuilder sb = new StringBuilder();
        sb.append("[#").append(getId()).append(" -> ").append(getName()).append(": ").append(getDescription()).append(" - [");

        String[] tipos = getTypes();
        for (int i = 0; i < tipos.length; i++) {
            sb.append("'").append(tipos[i]).append("'");
            if (i < tipos.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("] - ");

        sb.append("[");
        String[] habilidades = getAbilities();
        for (int i = 0; i < habilidades.length; i++) {
            sb.append("'").append(habilidades[i]).append("'");
            if (i < habilidades.length - 1) {
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
}

// Classe para gerenciar uma lista de Pokémons com várias operações
class ListaPokemon {
    private int capacidadeMax;
    private Pokemon[] pokemons;
    private int totalPokemons;

    public ListaPokemon() {
        this(100);
    }

    public ListaPokemon(int capacidadeMax) {
        this.capacidadeMax = capacidadeMax;
        this.pokemons = new Pokemon[this.capacidadeMax];
        this.totalPokemons = 0;
    }

    // Insere um Pokémon no início da lista
    public void inserirInicio(Pokemon pokemon) throws Exception {
        if (totalPokemons >= pokemons.length) {
            throw new Exception("Erro ao inserir!");
        } 

        for (int i = totalPokemons; i > 0; i--) {
            pokemons[i] = pokemons[i-1];
        }

        pokemons[0] = pokemon;
        totalPokemons++;
    }

    // Insere um Pokémon em uma posição específica da lista
    public void inserirNaPosicao(Pokemon pokemon, int posicao) throws Exception {
        if (totalPokemons >= pokemons.length || posicao < 0 || posicao > totalPokemons) {
            throw new Exception("Erro ao inserir!");
        }

        for (int i = totalPokemons; i > posicao; i--) {
            pokemons[i] = pokemons[i-1];
        }

        pokemons[posicao] = pokemon;
        totalPokemons++;
    }

    // Insere um Pokémon no final da lista
    public void inserirFim(Pokemon pokemon) throws Exception {
        if (totalPokemons >= pokemons.length) {
            throw new Exception("Erro ao inserir!");
        }

        pokemons[totalPokemons] = pokemon;
        totalPokemons++;
    }

    // Remove um Pokémon do início da lista e o retorna
    public Pokemon removerInicio() throws Exception {
        if (totalPokemons == 0) {
            throw new Exception("Erro ao remover!");
        }

        Pokemon removido = pokemons[0];
        totalPokemons--;

        for (int i = 0; i < totalPokemons; i++) {
            pokemons[i] = pokemons[i+1];
        }

        return removido;
    }

    // Remove um Pokémon de uma posição específica e o retorna
    public Pokemon removerNaPosicao(int posicao) throws Exception {
        if (totalPokemons == 0 || posicao < 0 || posicao >= totalPokemons) {
            throw new Exception("Erro ao remover!");
        }

        Pokemon removido = pokemons[posicao];
        totalPokemons--;

        for (int i = posicao; i < totalPokemons; i++) {
            pokemons[i] = pokemons[i+1];
        }

        return removido;
    }

    // Remove um Pokémon do final da lista e o retorna
    public Pokemon removerFim() throws Exception {
        if (totalPokemons == 0) {
            throw new Exception("Erro ao remover!");
        }

        return pokemons[--totalPokemons];
    }

    // Imprime todos os Pokémons da lista
    public void exibirTodos() {
        for (int i = 0; i < totalPokemons; i++) {
            if (pokemons[i] != null) {
                System.out.print("[" + i + "] ");
                System.out.println(pokemons[i].exibirDados());
            }
        }
    }    
}

// Classe principal para execução das operações de leitura e manipulação dos Pokémons
public class Pokemons {
    public static void main(String[] args) {
        String caminhoArquivo = "/tmp/pokemon.csv";
        ArrayList<Pokemon> pokedex = new ArrayList<>();

        // Lê os dados dos Pokémons do arquivo CSV
        try (Scanner scanner = new Scanner(new File(caminhoArquivo))) {
            scanner.nextLine(); // Ignora cabeçalho
            while (scanner.hasNextLine()) {
                String linhaCSV = scanner.nextLine();
                Pokemon pokemon = new Pokemon();
                pokemon.lerDados(linhaCSV); 
                pokedex.add(pokemon);
            }
        } catch (Exception e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        ListaPokemon listaPokemons = new ListaPokemon();

        String entrada = scanner.nextLine();
        while (!entrada.equals("FIM")) {
            int indicePokemon = Integer.parseInt(entrada) - 1;
            if (indicePokemon >= 0 && indicePokemon < pokedex.size()) {
                try {
                    listaPokemons.inserirFim(pokedex.get(indicePokemon));
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            } else {
                System.out.println("Índice inválido para Pokémon.");
            }
            entrada = scanner.nextLine();  
        }

        int numeroOperacoes = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < numeroOperacoes; i++) {
            String linha = scanner.nextLine();
            String[] partes = linha.split(" ");
            String operacao = partes[0];
            int indicePokemon = 0, posicao = 0;

            if (partes.length == 3) {
                posicao = Integer.parseInt(partes[1]);
                indicePokemon = Integer.parseInt(partes[2]) - 1;
            } else if (operacao.equals("R*")) {
                posicao = Integer.parseInt(partes[1]);
            } else if (partes.length == 2) {
                indicePokemon = Integer.parseInt(partes[1]) - 1;
            }

            try {
                switch (operacao) {
                    case "II":
                        listaPokemons.inserirInicio(pokedex.get(indicePokemon));
                        break;
                    case "I*":
                        listaPokemons.inserirNaPosicao(pokedex.get(indicePokemon), posicao);
                        break;
                    case "IF":
                        listaPokemons.inserirFim(pokedex.get(indicePokemon));
                        break;
                    case "RI":
                        Pokemon removidoInicio = listaPokemons.removerInicio();
                        System.out.println("(R) " + removidoInicio.getName());
                        break;
                    case "R*":
                        Pokemon removidoPosicao = listaPokemons.removerNaPosicao(posicao);
                        System.out.println("(R) " + removidoPosicao.getName());
                        break;
                    case "RF":
                        Pokemon removidoFim = listaPokemons.removerFim();
                        System.out.println("(R) " + removidoFim.getName());
                        break;
                }
            } catch (Exception e) {
                System.out.println("Erro na operação: " + e.getMessage());
            }
        }

        listaPokemons.exibirTodos();

        scanner.close();
    }
}
