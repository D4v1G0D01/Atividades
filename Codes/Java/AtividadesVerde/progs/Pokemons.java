import java.util.*;
import java.util.stream.Collectors;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class Pokemons {
    public static void main(String[] args) {
        // Caminho do arquivo CSV com dados dos Pokémon
        String arquivoCaminho = "/tmp/pokemon.csv";
        List<Pokemon> listaPokemons = new ArrayList<>();

        // Leitura do arquivo CSV
        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivoCaminho))) {
            String linha = leitor.readLine(); // Lê o cabeçalho
            linha = leitor.readLine(); // Lê a primeira linha de dados

            // Processa cada linha do arquivo
            while (linha != null) {
                Pokemon pokemon = new Pokemon();
                pokemon.ler(linha); // Lê os dados do Pokémon
                listaPokemons.add(pokemon); // Adiciona à lista
                linha = leitor.readLine(); // Lê a próxima linha
            }
        } catch (IOException e) {
            // Tratamento de exceção (não faz nada, mas poderia imprimir um erro)
        }  

        // Leitura de entradas do usuário
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String entradaUsuario = scanner.nextLine();

            // Termina o loop se o usuário digitar "FIM"
            if (entradaUsuario.equalsIgnoreCase("FIM")) {
                break;
            }

            try {
                int idPokemon = Integer.parseInt(entradaUsuario);
                boolean encontrado = false;

                // Procura o Pokémon pelo ID
                for (Pokemon pokemon : listaPokemons) {
                    if (pokemon.getId() == idPokemon) {
                        pokemon.imprimir(); // Imprime as informações do Pokémon encontrado
                        encontrado = true;
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                // Tratamento de exceção para entradas não numéricas
            }
        }

        scanner.close(); // Fecha o scanner
    }
}

class Pokemon {
    private int id; // Identificador do Pokémon
    private int geracao; // Geração do Pokémon
    private String nome; // Nome do Pokémon
    private String descricao; // Descrição do Pokémon
    private List<String> tipos; // Tipos do Pokémon
    private List<String> habilidades; // Habilidades do Pokémon
    private double peso; // Peso do Pokémon
    private double altura; // Altura do Pokémon
    private int taxaCaptura; // Taxa de captura do Pokémon
    private boolean eLegendario; // Indica se é um Pokémon lendário
    private LocalDate dataCaptura; // Data de captura do Pokémon

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

    // Getters e Setters
    public int getId() { return id; }
    public int getGeracao() { return geracao; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public List<String> getTipos() { return tipos; }
    public List<String> getHabilidades() { return habilidades; }
    public double getPeso() { return peso; }
    public double getAltura() { return altura; }
    public int getTaxaCaptura() { return taxaCaptura; }
    public boolean isELegendario() { return eLegendario; }
    public LocalDate getDataCaptura() { return dataCaptura; }

    public void setId(int id) { this.id = id; }
    public void setGeracao(int geracao) { this.geracao = geracao; }
    public void setNome(String nome) { this.nome = nome; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setTipos(List<String> tipos) { this.tipos = tipos; }
    public void setHabilidades(List<String> habilidades) { this.habilidades = habilidades; }
    public void setPeso(double peso) { this.peso = peso; }
    public void setAltura(double altura) { this.altura = altura; }
    public void setTaxaCaptura(int taxaCaptura) { this.taxaCaptura = taxaCaptura; }
    public void setELegendario(boolean eLegendario) { this.eLegendario = eLegendario; }
    public void setDataCaptura(LocalDate dataCaptura) { this.dataCaptura = dataCaptura; }

    // Lê os dados de uma linha
    public void ler(String linha) {
        String[] dados = linha.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");

        setId(dados[0].isEmpty() ? 0 : Integer.parseInt(dados[0]));
        setGeracao(dados[1].isEmpty() ? 0 : Integer.parseInt(dados[1]));
        setNome(dados[2]);
        setDescricao(dados[3]);

        ArrayList<String> listaTipos = new ArrayList<>();
        if (!dados[4].isEmpty()) listaTipos.add(dados[4].trim());
        if (!dados[5].isEmpty()) listaTipos.add(dados[5].trim());
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

    // Método para parsear habilidades
    private ArrayList<String> parseHabilidades(String habilidadesStr) {
        habilidadesStr = habilidadesStr.replace("[", "").replace("]", "").trim();
        if (habilidadesStr.isEmpty()) {
            return new ArrayList<>();
        }
        String[] habilidadesArray = habilidadesStr.split(",\\s*");
        return new ArrayList<>(Arrays.asList(habilidadesArray));
    }

    // Método para parsear a data
    private LocalDate parseData(String dataStr) {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(dataStr, formatador);
    }

    // Método para imprimir os dados do Pokémon
    public void imprimir() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("[#%d -> %s: %s - ", id, nome, descricao));

        String tiposStr = String.join(", ", tipos.stream()
                                           .map(tipo -> "'" + tipo + "'")
                                           .collect(Collectors.toList()));
        sb.append("[").append(tiposStr).append("] - ");

        String habilidadesStr = String.join(", ", habilidades.stream()
                                                   .map(this::limparHabilidades)
                                                   .collect(Collectors.toList()));
        sb.append("[").append(habilidadesStr).append("] - ");
    
        sb.append(String.format("%.1fkg - %.1fm - %d%% - %s - %d gen] - %s%n",
            peso, altura, taxaCaptura, eLegendario ? "true" : "false", geracao, dataCaptura.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));

        System.out.print(sb.toString());
    }

    // Método para limpar o nome da habilidade
    private String limparHabilidades(String habilidade) {
        habilidade = habilidade.trim();
        habilidade = habilidade.replaceAll("^\"|\"$|'|'$", "");
        habilidade = habilidade.replaceAll("[^a-zA-Z0-9 ]", "");
        if (!habilidade.isEmpty()) {
            return "'" + habilidade + "'";
        } else {
            return "";
        }
    }

    @Override
    public Pokemon clone() {
        List<String> tiposClone = new ArrayList<>(tipos);
        List<String> habilidadesClone = new ArrayList<>(habilidades);
        return new Pokemon(id, geracao, nome, descricao, tiposClone, habilidadesClone, peso, altura, taxaCaptura, eLegendario, dataCaptura);
    }
}
