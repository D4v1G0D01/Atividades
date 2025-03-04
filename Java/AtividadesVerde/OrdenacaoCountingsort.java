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
    private String codigo;
    private int geracao;
    private String titulo;
    private String detalhes;
    private String[] tipos;
    private String[] habilidades;
    private double massa;
    private double altura;
    private int taxaCaptura;
    private boolean ehLegendario;
    private LocalDate dataCaptura;

    public String getCodigo() 
    {
        return codigo;
    }

    public void setCodigo(String codigo) 
    {
        this.codigo = codigo;
    }

    public int getGeracao() 
    {
        return geracao;
    }

    public void setGeracao(int geracao) 
    {
        this.geracao = geracao;
    }

    public String getTitulo() 
    {
        return titulo;
    }

    public void setTitulo(String titulo) 
    {
        this.titulo = titulo;
    }

    public String getDetalhes() 
    {
        return detalhes;
    }

    public void setDetalhes(String detalhes) 
    {
        this.detalhes = detalhes;
    }

    public String[] getTipos() 
    {
        return tipos;
    }

    public void setTipos(String[] tipos) 
    {
        this.tipos = tipos;
    }

    public String[] getHabilidades() 
    {
        return habilidades;
    }

    public void setHabilidades(String[] habilidades) 
    {
        this.habilidades = habilidades;
    }

    public double getMassa() 
    {
        return massa;
    }

    public void setMassa(double massa) 
    {
        this.massa = massa;
    }

    public double getAltura() 
    {
        return altura;
    }

    public void setAltura(double altura) 
    {
        this.altura = altura;
    }

    public int getTaxaCaptura() 
    {
        return taxaCaptura;
    }

    public void setTaxaCaptura(int taxaCaptura) 
    {
        this.taxaCaptura = taxaCaptura;
    }

    public boolean isEhLegendario() 
    {
        return ehLegendario;
    }

    public void setEhLegendario(boolean ehLegendario) 
    {
        this.ehLegendario = ehLegendario;
    }

    public LocalDate getDataCaptura() 
    {
        return dataCaptura;
    }

    public void setDataCaptura(LocalDate dataCaptura) 
    {
        this.dataCaptura = dataCaptura;
    }

    void ler(String linhaCSV) 
    {
        String[] dados = linhaCSV.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
        
        setCodigo(dados[0].trim());
        setGeracao(Integer.parseInt(dados[1].trim()));
        setTitulo(dados[2].trim());
        setDetalhes(dados[3].trim());

        List<String> listaTipos = new ArrayList<>();
        listaTipos.add(dados[4].trim());
        if (!dados[5].trim().isEmpty()) listaTipos.add(dados[5].trim());
        setTipos(listaTipos.toArray(new String[0]));

        // Processar habilidades sem aspas
        String habilidadesStr = dados[6].replaceAll("[\\[\\]\"']", "").trim(); // Remove colchetes e aspas
        setHabilidades(habilidadesStr.split(",\\s*")); // Divide as habilidades e remove espaços extras

        setMassa(dados[7].trim().isEmpty() ? 0 : Double.parseDouble(dados[7].trim()));
        setAltura(dados[8].trim().isEmpty() ? 0 : Double.parseDouble(dados[8].trim()));
        setTaxaCaptura(dados[9].trim().isEmpty() ? 0 : Integer.parseInt(dados[9].trim()));
        setEhLegendario(dados[10].trim().equals("1") || dados[10].trim().equalsIgnoreCase("true"));

        LocalDate data = parseData(dados[11].trim());
        setDataCaptura(data);
    }

    private LocalDate parseData(String dataStr) 
    {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(dataStr, formatador);
    }

    String imprimir() 
    {
        StringBuilder sb = new StringBuilder();
        sb.append("[#").append(getCodigo()).append(" -> ").append(getTitulo()).append(": ").append(getDetalhes()).append(" - [");

        String[] tipos = getTipos();
        for (int i = 0; i < tipos.length; i++) 
        {
            sb.append("'").append(tipos[i]).append("'");
            if (i < tipos.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("] - ");

        sb.append("[");
        String[] habilidades = getHabilidades();
        for (int i = 0; i < habilidades.length; i++) 
        {
            sb.append("'").append(habilidades[i]).append("'"); // Mantém as aspas
            if (i < habilidades.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("] - ");

        sb.append(getMassa()).append("kg - ");
        sb.append(getAltura()).append("m - ");
        sb.append(getTaxaCaptura()).append("% - ");
        sb.append(isEhLegendario() ? "true" : "false").append(" - ");
        sb.append(getGeracao()).append(" gen] - ");
        sb.append(getDataCaptura().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        return sb.toString();
    }

    @Override
    public int compareTo(Pokemon outro) 
    {
        int resultado = this.getTitulo().compareTo(outro.getTitulo());
        if (resultado == 0) 
        {
            return Integer.compare(this.getTaxaCaptura(), outro.getTaxaCaptura());
        }
        return resultado;
    }
}


class OrdenacaoContagemPokemon 
{
    private Pokemon[] array;
    private int tamanho;
    public int comparacoes = 0; // Contador de comparações
    public int movimentacoes = 0; // Contador de movimentações

    public OrdenacaoContagemPokemon(Pokemon[] array, int tamanho) {
        this.array = array;
        this.tamanho = tamanho;
    }

    public void ordenar() {
        if (tamanho == 0) return; // Verifica se o array está vazio

        int max = Integer.MIN_VALUE;
        for (int i = 0; i < tamanho; i++) {
            if (array[i] != null) {
                max = Math.max(max, array[i].getTaxaCaptura());
            }
        }

        // Inicializa o array de contagem
        int[] contagem = new int[max + 1];
        for (int i = 0; i < tamanho; i++) {
            if (array[i] != null) {
                contagem[array[i].getTaxaCaptura()]++;
            }
        }

        // Ajusta o array de contagem para posições finais
        for (int i = 1; i <= max; i++) {
            contagem[i] += contagem[i - 1];
        }

        Pokemon[] saida = new Pokemon[tamanho];
        for (int i = tamanho - 1; i >= 0; i--) {
            if (array[i] != null) {
                int taxaCaptura = array[i].getTaxaCaptura();
                int indice = contagem[taxaCaptura] - 1;
                saida[indice] = array[i];
                contagem[taxaCaptura]--;
                movimentacoes++;
            }
        }

        // Ordenação adicional para resolver o empate pelo título
        for (int i = 0; i < tamanho - 1; i++) {
            for (int j = 0; j < tamanho - i - 1; j++) {
                if (saida[j] != null && saida[j + 1] != null) {
                    // Primeiro compara pela taxaCaptura
                    if (saida[j].getTaxaCaptura() == saida[j + 1].getTaxaCaptura()) {
                        comparacoes++;
                        // Se taxaCaptura for igual, comparar pelo título
                        if (saida[j].getTitulo().compareTo(saida[j + 1].getTitulo()) > 0) {
                            Pokemon temp = saida[j];
                            saida[j] = saida[j + 1];
                            saida[j + 1] = temp;
                            movimentacoes++;
                        }
                    }
                }
            }
        }

        // Copia o array ordenado de volta para o array original
        System.arraycopy(saida, 0, array, 0, tamanho);
    }

    public void imprimir() {
        for (int i = 0; i < tamanho; i++) {
            if (array[i] != null) { // Verifica se o Pokémon não é nulo
                System.out.println(array[i].imprimir());
            }
        }
    }
}


public class OrdenacaoCountingsort
{
    public static Pokemon obterPokemonPorId(String id, ArrayList<Pokemon> lista) {
        Pokemon retorno = null;
        for (Pokemon pokemon : lista) 
        {
            if(pokemon.getCodigo().equals(id)) 
            {
                retorno = pokemon;
            }
        }
        return retorno;
    }

    public static void main(String[] args) 
    {
        int contador = 0;
        ArrayList<Pokemon> listaPokemons = new ArrayList<>();

        long inicioTempo = System.currentTimeMillis(); // Início da contagem do tempo

        try (Scanner scanner = new Scanner(System.in);
             BufferedReader br = new BufferedReader(new FileReader("/tmp/pokemon.csv"))) 
        {

            ArrayList<Pokemon> pokemonsEncontrados = new ArrayList<>();
            String linha;

            br.readLine(); // Ignora a primeira linha (cabeçalho)

            while ((linha = br.readLine()) != null) 
            {
                Pokemon pokemon = new Pokemon();
                pokemon.ler(linha);
                pokemonsEncontrados.add(pokemon);
            }

            String id;
            do {
                contador++;
                id = scanner.nextLine();
                Pokemon pokemonAux = obterPokemonPorId(id, pokemonsEncontrados);

                if(pokemonAux != null) listaPokemons.add(pokemonAux);

            } while(!id.equals("FIM"));

        } 
        catch (IOException e) 
        {
            System.err.println("Erro de I/O: " + e.getMessage());
        }

        Pokemon[] array = new Pokemon[contador]; 
        int contador2 = 0;

        for(Pokemon poke : listaPokemons)
        {
            array[contador2++] = poke;
        }

        OrdenacaoContagemPokemon ordenacao = new OrdenacaoContagemPokemon(array, contador);

        ordenacao.ordenar();

        long fimTempo = System.currentTimeMillis(); // Fim contagem tempo
        long tempoTotal = fimTempo - inicioTempo; // Tempo total execução

        try (FileWriter logWriter = new FileWriter("848324_countingsort.txt")) 
        {
            logWriter.write("848324\t" + ordenacao.comparacoes + "\t" + ordenacao.movimentacoes + "\t" + tempoTotal + "ms\n");
        } 
        catch (IOException e) 
        {
            System.err.println("Erro ao escrever o arquivo de log: " + e.getMessage());
        }

        for(int i = 0; i < contador - 1; i++)
        {
            System.out.println(array[i].imprimir());
        }
    }
}
