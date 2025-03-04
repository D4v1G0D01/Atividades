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
    
    // (Os métodos de acesso e modificação continuam os mesmos)

    void lerDados(String csvLine) {
        // Método de leitura do CSV
    }

    private LocalDate parseDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(dateStr, formatter);
    }

    String exibirDados() {
        // Método para exibir os dados do Pokémon
    }
}

class PilhaPokemon {
    private Pokemon[] pilha;
    private int topo;

    public PilhaPokemon(int capacidadeMaxima) {
        this.pilha = new Pokemon[capacidadeMaxima];
        this.topo = 0;
    }

    public void empilhar(Pokemon pokemon) throws Exception {
        if (topo >= pilha.length) {
            throw new Exception("Erro ao empilhar! A pilha está cheia.");
        }
        pilha[topo++] = pokemon;
    }

    public Pokemon desempilhar() throws Exception {
        if (topo == 0) {
            throw new Exception("Erro ao desempilhar! A pilha está vazia.");
        }
        return pilha[--topo];
    }

    public void exibirTodos() {
        for (int i = topo - 1; i >= 0; i--) {
            System.out.println(pilha[i].exibirDados());
        }
    }
}

public class Pokemons {
    public static void main(String[] args) {
        String caminhoArquivo = "/tmp/pokemon.csv";
        ArrayList<Pokemon> pokedex = new ArrayList<>();

        // Leitura do arquivo CSV
        try (Scanner scanner = new Scanner(new File(caminhoArquivo))) {
            scanner.nextLine(); // Ignora o cabeçalho
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
        PilhaPokemon pilhaPokemon = new PilhaPokemon(100);  // Definindo a capacidade máxima como 100

        String entrada = scanner.nextLine();
        while (!entrada.equals("FIM")) {
            if (entrada.startsWith("I")) { // Inserir (Empilhar)
                int indicePokemon = Integer.parseInt(entrada.split(" ")[1]) - 1;
                if (indicePokemon >= 0 && indicePokemon < pokedex.size()) {
                    try {
                        pilhaPokemon.empilhar(pokedex.get(indicePokemon));
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                } else {
                    System.out.println("Índice inválido para Pokémon.");
                }
            } else if (entrada.equals("R")) { // Remover (Desempilhar)
                try {
                    Pokemon removido = pilhaPokemon.desempilhar();
                    System.out.println("(R) " + removido.getName());
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
            entrada = scanner.nextLine();
        }

        System.out.println("Elementos na pilha:");
        pilhaPokemon.exibirTodos();

        scanner.close();
    }
}
