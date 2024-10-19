import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.nio.charset.Charset;

class Pokemon {

    // Atributos do Pokémon
    private int identificacao;
    private int geracao;
    private String nome;
    private String descricao;
    private List<String> tipos;
    private List<String> habilidades;
    private double peso;
    private double altura;
    private int taxaCaptura;
    private boolean ehLendario;
    private LocalDate dataCaptura;

    // Construtor padrão
    public Pokemon() {
        identificacao = 0;
        geracao = 0;
        nome = "";
        descricao = "";
        tipos = null;
        habilidades = null;
        peso = 0.0;
        altura = 0.0;
        taxaCaptura = 0;
        ehLendario = false;
        dataCaptura = null;
    }

    // Construtor com parâmetros
    public Pokemon(int identificacao, int geracao, String nome, String descricao, List<String> tipos, List<String> habilidades, 
                   double peso, double altura, int taxaCaptura, boolean ehLendario, LocalDate dataCaptura) {
        this.identificacao = identificacao;
        this.geracao = geracao;
        this.nome = nome;
        this.descricao = descricao;
        this.tipos = tipos;
        this.habilidades = habilidades;
        this.peso = peso;
        this.altura = altura;
        this.taxaCaptura = taxaCaptura;
        this.ehLendario = ehLendario;
        this.dataCaptura = dataCaptura;
    }

    // Métodos GETTERS e SETTERS

    public int getIdentificacao() {
        return identificacao;
    }
    public void setIdentificacao(int identificacao) {
        this.identificacao = identificacao;
    }

    public int getGeracao() {
        return geracao;
    }
    public void setGeracao(int geracao) {
        this.geracao = geracao;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<String> getTipos() {
        return tipos;
    }
    public void setTipos(List<String> tipos) {
        this.tipos = tipos;
    }

    public List<String> getHabilidades() {
        return habilidades;
    }
    public void setHabilidades(List<String> habilidades) {
        this.habilidades = habilidades;
    }

    public double getPeso() {
        return peso;
    }
    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }
    public void setAltura(double altura) {
        this.altura = altura;
    }

    public int getTaxaCaptura() {
        return taxaCaptura;
    }
    public void setTaxaCaptura(int taxaCaptura) {
        this.taxaCaptura = taxaCaptura;
    }

    public boolean isEhLendario() {
        return ehLendario;
    }
    public void setEhLendario(boolean ehLendario) {
        this.ehLendario = ehLendario;
    }

    public LocalDate getDataCaptura() {
        return dataCaptura;
    }
    public void setDataCaptura(LocalDate dataCaptura) {
        this.dataCaptura = dataCaptura;
    }

    // Métodos adicionais

    // Método ToString que formata os dados do Pokémon
    @Override
    public String toString() {
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return "[#" + identificacao + " -> " + nome + ": " + descricao + " - " + 
                formatarTipos(tipos) + " - " + formatarLista(habilidades) + " - " + 
                peso + "kg - " + altura + "m - " + taxaCaptura + "% - " + ehLendario + 
                " - " + geracao + " gen] - " + dataCaptura.format(formatoData);
    }

    // Método para formatar listas de habilidades
    public String formatarLista(List<String> lista) {
        return String.join(",", lista);
    }

    // Método para formatar os tipos de Pokémon
    public String formatarTipos(List<String> lista) {
        if (lista.get(1) != null) {
            return "['" + lista.get(0) + "', '" + lista.get(1) + "']";
        } else {
            return "['" + lista.get(0) + "']";
        }
    }

    // Método para imprimir o Pokémon
    public void imprimir() {
        System.out.println(toString());
    }

    // Método que verifica se o ID é igual ao informado
    public boolean verificarId(int numero) {
        return numero == identificacao;
    }

    // Método para clonar o Pokémon
    public Pokemon clonar() {
        return new Pokemon(identificacao, geracao, nome, descricao, tipos, habilidades, peso, altura, taxaCaptura, ehLendario, dataCaptura);
    }

}

public class OrdenacaoSelecao2 {

    static Pokemon[] pokedex = new Pokemon[801];
    static Pokemon[] selecionados = new Pokemon[801];
    static int qtdSelecionados = 0;
    static int comparacoes = 0;
    static long tempoExecucao;
    static DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Método para preencher a Pokedex a partir de um arquivo CSV
    public static void carregarPokedex() {
        String caminhoArquivo = "/tmp/pokemon.csv";

        try {
            RandomAccessFile arquivo = new RandomAccessFile(caminhoArquivo, "r");
            Charset charset = Charset.forName("UTF-8");

            arquivo.readLine(); // Ignora a primeira linha (cabeçalho)

            String linha;
            int i = 0;
            while ((linha = arquivo.readLine()) != null) {
                linha = new String(linha.getBytes("ISO-8859-1"), charset);
                String[] dadosHabilidades = linha.split("\"");
                String habilidades = dadosHabilidades[1];
                String[] primeiraParte = dadosHabilidades[0].split(",", -1);
                String[] segundaParte = dadosHabilidades[2].split(",", -1);

                int identificacao = Integer.parseInt(primeiraParte[0]);
                int geracao = Integer.parseInt(primeiraParte[1]);
                String nome = primeiraParte[2];
                String descricao = primeiraParte[3];

                List<String> tipos = new ArrayList<>();
                tipos.add(primeiraParte[4]);
                tipos.add(primeiraParte[5].isEmpty() ? null : primeiraParte[5]);

                List<String> listaHabilidades = new ArrayList<>();
                listaHabilidades.add(habilidades);

                double peso = segundaParte[1].isEmpty() ? 0 : Double.parseDouble(segundaParte[1]);
                double altura = segundaParte[2].isEmpty() ? 0 : Double.parseDouble(segundaParte[2]);
                int taxaCaptura = segundaParte[3].isEmpty() ? 0 : Integer.parseInt(segundaParte[3]);
                boolean ehLendario = segundaParte[4].charAt(0) != '0';
                LocalDate dataCaptura = LocalDate.parse(segundaParte[5], formatoData);

                Pokemon pokemon = new Pokemon(identificacao, geracao, nome, descricao, tipos, listaHabilidades, peso, altura, taxaCaptura, ehLendario, dataCaptura);
                pokedex[i] = pokemon;
                i++;
            }
            arquivo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para trocar dois elementos
    public static void trocar(int menor, int i) {
        Pokemon tmp = selecionados[menor];
        selecionados[menor] = selecionados[i];
        selecionados[i] = tmp;
    }

    // Método de ordenação por seleção
    public static void ordenarPorNome() {
        long inicio = System.nanoTime();

        for (int i = 0; i < qtdSelecionados; i++) {
            int menor = i;

            for (int j = i + 1; j < qtdSelecionados; j++) {
                comparacoes++;
                if (selecionados[menor].getNome().compareTo(selecionados[j].getNome()) > 0) {
                    menor = j;
                }
            }

            trocar(menor, i);
        }

        long fim = System.nanoTime();
        tempoExecucao = (fim - inicio);
    }

    // Método para criar o arquivo de log
    public static void criarArquivoLog() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("matrícula_selecao.txt"))) {
            writer.write("848324" + "\t" + tempoExecucao + "ns\t" + comparacoes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para verificar se a string é "FIM"
    public static boolean verificarFim(String str) {
        return str.equals("FIM");
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        carregarPokedex();
        Scanner scan = new Scanner(System.in);
        String entrada;

        do {
            entrada = scan.nextLine();

            if (!verificarFim(entrada)) {
                selecionados[qtdSelecionados] = pokedex[Integer.parseInt(entrada) - 1].clonar();
                qtdSelecionados++;
            }

        } while (!verificarFim(entrada));

        ordenarPorNome();

        for (int i = 0; i < qtdSelecionados; i++) {
            System.out.println(selecionados[i].toString());
        }

        criarArquivoLog();
        scan.close();
    }
}
