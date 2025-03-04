import java.io.*;
import java.util.*;

class Data {
    int dia, mes, ano;

    public Data(int dia, int mes, int ano) {
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
    }

    @Override
    public String toString() {
        return String.format("%02d/%02d/%04d", dia, mes, ano);
    }

    public static Data stringParaData(String strData) {
        if (strData == null || strData.isEmpty()) {
            return new Data(0, 0, 0); // Se não houver data
        }
        String[] partes = strData.split("/");
        return new Data(Integer.parseInt(partes[0]), Integer.parseInt(partes[1]), Integer.parseInt(partes[2]));
    }
}

class Pokemon {
    private int id, generation, captureRate;
    private String name, description;
    private List<String> types;
    private List<String> abilities;
    private double weight, height;
    private boolean isLegendary;
    private Data captureDate;

    // Construtores
    public Pokemon(int id, int generation, String name, String description, List<String> types, List<String> abilities,
                   double weight, double height, int captureRate, boolean isLegendary, Data captureDate) {
        this.id = id;
        this.generation = generation;
        this.name = name;
        this.description = description;
        this.types = new ArrayList<>(types); // Lista mutável
        this.abilities = new ArrayList<>(abilities); // Lista mutável
        this.weight = weight;
        this.height = height;
        this.captureRate = captureRate;
        this.isLegendary = isLegendary;
        this.captureDate = captureDate;
    }

    // Getters
    public String getName() {
        return this.name;
    }
}

public class PesquisaSequencial {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        List<Pokemon> pokedex = new ArrayList<>();
        int comparacoes = 0;
        long inicio = System.currentTimeMillis();

        // Leitura de Pokémons da primeira parte
        String input = sc.nextLine();
        while (!input.equals("FIM")) {
            try {
                String[] campos = input.split(","); // Simula a leitura de um CSV

                if (campos.length != 11) {
                    throw new IllegalArgumentException("Número incorreto de campos.");
                }

                int id = Integer.parseInt(campos[0]);
                int generation = Integer.parseInt(campos[1]);
                String name = campos[2];
                String description = campos[3];
                List<String> types = new ArrayList<>(Arrays.asList(campos[4].split(";")));
                List<String> abilities = new ArrayList<>(Arrays.asList(campos[5].split(";")));
                double weight = Double.parseDouble(campos[6]);
                double height = Double.parseDouble(campos[7]);
                int captureRate = Integer.parseInt(campos[8]);
                boolean isLegendary = Boolean.parseBoolean(campos[9]);
                Data captureDate = Data.stringParaData(campos[10]);

                Pokemon p = new Pokemon(id, generation, name, description, types, abilities, weight, height, captureRate, isLegendary, captureDate);
                pokedex.add(p);

            } catch (Exception e) {
                // Não imprime erros, apenas continua o laço
            }

            input = sc.nextLine();
        }

        // Leitura da segunda parte (pesquisa por nome)
        input = sc.nextLine();
        while (!input.equals("FIM")) {
            boolean encontrado = false;
            for (Pokemon p : pokedex) {
                comparacoes++;
                if (p.getName().equalsIgnoreCase(input)) {
                    System.out.println("SIM");
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                System.out.println("NAO");
            }
            input = sc.nextLine();
        }

        // Finalização e criação do log
        long fim = System.currentTimeMillis();
        long tempoExecucao = fim - inicio;
        String matricula = "848324"; 

        // Criação do arquivo de log
        try (FileWriter logFile = new FileWriter(matricula + "_sequencial.txt")) {
            logFile.write(matricula + "\t" + tempoExecucao + "\t" + comparacoes);
        } catch (IOException e) {
            System.out.println("Erro ao escrever o arquivo de log: " + e.getMessage());
        }
    }
}
