import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class Pokemon {
    private int id;
    private int geracao;
    private String nome;
    private String descricao;
    private List<String> tipos;
    private List<String> habilidades;
    private double peso;
    private double altura;
    private int taxaCaptura;
    private boolean eLegendario;
    private LocalDate dataCaptura;

    public Pokemon() {}

    public Pokemon(int id, int geracao, String nome, String descricao, List<String> tipos, List<String> habilidades,
                   double peso, double altura, int taxaCaptura, boolean eLegendario, LocalDate dataCaptura) {
        setId(id);
        setGeracao(geracao);
        setNome(nome);
        setDescricao(descricao);
        setTipos(tipos);
        setHabilidades(habilidades);
        setPeso(peso);
        setAltura(altura);
        setTaxaCaptura(taxaCaptura);
        setELegendario(eLegendario);
        setDataCaptura(dataCaptura);
    }

    public void ler(String linha) {
        String[] dados = linha.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");

        setId(dados[0].isEmpty() ? 0 : Integer.parseInt(dados[0]));
        setGeracao(dados[1].isEmpty() ? 0 : Integer.parseInt(dados[1]));
        setNome(dados[2]);
        setDescricao(dados[3]);

        // Melhoria: Lidando com múltiplos tipos usando ';'
        ArrayList<String> listaTipos = new ArrayList<>();
        String[] tiposArray = dados[4].split(";");
        for (String tipo : tiposArray) {
            if (!tipo.trim().isEmpty()) {
                listaTipos.add(tipo.trim());
            }
        }
        setTipos(listaTipos);

        ArrayList<String> listaHabilidades = parseHabilidades(dados[6]);
        setHabilidades(listaHabilidades);

        setPeso(dados[7].isEmpty() ? 0.0 : Double.parseDouble(dados[7]));
        setAltura(dados[8].isEmpty() ? 0.0 : Double.parseDouble(dados[8]));
        setTaxaCaptura(dados[9].isEmpty() ? 0 : Integer.parseInt(dados[9]));
        setELegendario(dados[10].equals("1") || dados[10].equalsIgnoreCase("true"));

        if (!dados[11].isEmpty()) {
            LocalDate data = parseData(dados[11]);
            setDataCaptura(data);
        }
    }

    // Método auxiliar para formatar as habilidades
    public ArrayList<String> parseHabilidades(String dados) {
        ArrayList<String> habilidades = new ArrayList<>();
        if (!dados.isEmpty()) {
            String[] partes = dados.split(";");
            for (String parte : partes) {
                habilidades.add(parte.trim());
            }
        }
        return habilidades;
    }

    // Método auxiliar para converter uma string de data em LocalDate
    public LocalDate parseData(String dataStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(dataStr, formatter);
    }

    // Método para comparação de nomes (usado na ordenação)
    public int compararNome(Pokemon outro) {
        return this.nome.compareTo(outro.nome);
    }

    // Método para imprimir os atributos do Pokémon
    public void imprimir() {
        String formatado = String.format(
                "#%d -> %s: %s - %s - %s - %.1fkg - %.1fm - %d%% - %b - %d gen] - %s",
                id,
                nome,
                descricao,
                String.join(", ", tipos), // Melhoria: imprimindo lista de tipos sem colchetes
                String.join(", ", habilidades), // Melhoria: imprimindo lista de habilidades sem colchetes
                peso,
                altura,
                taxaCaptura,
                eLegendario,
                geracao,
                dataCaptura.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        );
        System.out.println(formatado);
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getGeracao() { return geracao; }
    public void setGeracao(int geracao) { this.geracao = geracao; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public List<String> getTipos() { return tipos; }
    public void setTipos(List<String> tipos) { this.tipos = tipos; }

    public List<String> getHabilidades() { return habilidades; }
    public void setHabilidades(List<String> habilidades) { this.habilidades = habilidades; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }

    public double getAltura() { return altura; }
    public void setAltura(double altura) { this.altura = altura; }

    public int getTaxaCaptura() { return taxaCaptura; }
    public void setTaxaCaptura(int taxaCaptura) { this.taxaCaptura = taxaCaptura; }

    public boolean isELegendario() { return eLegendario; }
    public void setELegendario(boolean eLegendario) { this.eLegendario = eLegendario; }

    public LocalDate getDataCaptura() { return dataCaptura; }
    public void setDataCaptura(LocalDate dataCaptura) { this.dataCaptura = dataCaptura; }
}

class Metrics {
    int comparacoes = 0;
    int movimentacoes = 0;
}

public class OrdenacaoSelecao {
    public static void main(String[] args) {
        // Caminho do arquivo CSV com dados dos Pokémon
        String arquivoCaminho = "/tmp/pokemon.csv"; // Pode ser configurável via linha de comando
        List<Pokemon> listaPokemons = new ArrayList<>();

        // Leitura do arquivo CSV
        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivoCaminho))) {
            String linha = leitor.readLine(); // Lê o cabeçalho
            linha = leitor.readLine(); // Lê a primeira linha de dados
            while (linha != null) {
                Pokemon pokemon = new Pokemon();
                pokemon.ler(linha);
                listaPokemons.add(pokemon);
                linha = leitor.readLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo '" + arquivoCaminho + "': " + e.getMessage());
        }

        // Converte a lista para um vetor
        Pokemon[] pokemons = listaPokemons.toArray(new Pokemon[listaPokemons.size()]);

        // Ordena o vetor usando o algoritmo de seleção
        long inicio = System.currentTimeMillis();
        Metrics metrics = new Metrics();
        selecao(pokemons, metrics);
        long fim = System.currentTimeMillis();
        long tempoExecucao = fim - inicio;

        // Imprime os Pokémons ordenados
        for (Pokemon pokemon : pokemons) {
            pokemon.imprimir();
        }

        // Gera o arquivo de log
        String matricula = "848324"; // Substitua pela sua matrícula
        try (FileWriter escritor = new FileWriter("log_selecao_" + matricula + "_" + System.currentTimeMillis() + ".txt")) {
            escritor.write(matricula + "\t" + metrics.comparacoes + "\t" + metrics.movimentacoes + "\t" + tempoExecucao);
        } catch (IOException e) {
            System.err.println("Erro ao gerar arquivo de log: " + e.getMessage());
        }
    }

    // Implementação do algoritmo de ordenação por seleção
    public static void selecao(Pokemon[] pokemons, Metrics metrics) {
        int n = pokemons.length;
        boolean houveTroca;
        for (int i = 0; i < n - 1; i++) {
            houveTroca = false;
            int indiceMenor = i;
            for (int j = i + 1; j < n; j++) {
                metrics.comparacoes++;
                if (pokemons[j].compararNome(pokemons[indiceMenor]) < 0) {
                    indiceMenor = j;
                }
            }
            if (indiceMenor != i) {
                houveTroca = true;
                metrics.movimentacoes++;
                Pokemon temp = pokemons[indiceMenor];
                pokemons[indiceMenor] = pokemons[i];
                pokemons[i] = temp;
            }
            if (!houveTroca) break;  // Sai do loop se não houve troca
        }
    }
}
