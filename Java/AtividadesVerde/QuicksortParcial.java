import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Monstro implements Comparable<Monstro>
{
    private String codigo;
    private int versao;
    private String apelido;
    private String detalhes;
    private String[] categorias;
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

    public int getVersao() 
    {
        return versao;
    }

    public void setVersao(int versao) 
    {
        this.versao = versao;
    }

    public String getApelido() 
    {
        return apelido;
    }

    public void setApelido(String apelido) 
    {
        this.apelido = apelido;
    }

    public String getDetalhes() 
    {
        return detalhes;
    }

    public void setDetalhes(String detalhes) 
    {
        this.detalhes = detalhes;
    }

    public String[] getCategorias() 
    {
        return categorias;
    }

    public void setCategorias(String[] categorias) 
    {
        this.categorias = categorias;
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

    void lerDados(String linhaCsv) 
    {
        String[] dados = linhaCsv.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
        
        setCodigo(dados[0].trim());
        setVersao(Integer.parseInt(dados[1].trim()));
        setApelido(dados[2].trim());
        setDetalhes(dados[3].trim());

        List<String> categoriasLista = new ArrayList<>();
        categoriasLista.add(dados[4].trim());
        if (!dados[5].trim().isEmpty()) categoriasLista.add(dados[5].trim());
        setCategorias(categoriasLista.toArray(new String[0]));

        String habilidadesStr = dados[6].replaceAll("[\\[\\]\"']", "").trim(); 
        setHabilidades(habilidadesStr.split(",\\s*"));

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

    String exibir() 
    {
        StringBuilder sb = new StringBuilder();
        sb.append("[#").append(getCodigo()).append(" -> ").append(getApelido()).append(": ").append(getDetalhes()).append(" - [");

        String[] categorias = getCategorias();
        for (int i = 0; i < categorias.length; i++) 
        {
            sb.append("'").append(categorias[i]).append("'");
            if (i < categorias.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("] - ");

        sb.append("[");
        String[] habilidades = getHabilidades();
        for (int i = 0; i < habilidades.length; i++) 
        {
            sb.append("'").append(habilidades[i]).append("'");
            if (i < habilidades.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("] - ");

        sb.append(getMassa()).append("kg - ");
        sb.append(getAltura()).append("m - ");
        sb.append(getTaxaCaptura()).append("% - ");
        sb.append(isEhLegendario() ? "true" : "false").append(" - ");
        sb.append(getVersao()).append(" gen] - ");
        sb.append(getDataCaptura().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        return sb.toString();
    }

    @Override
    public int compareTo(Monstro outro)
    {
        int resultado;
        if(getApelido().compareTo(outro.getApelido()) == 0)
        {
            resultado = 0;
        }
        else if (this.getApelido().compareTo(outro.getApelido()) > 0)
        {
            resultado = 1;
        }
        else
        {
            resultado = -1;
        }
        return resultado;
    } 
}

class OrdenadorMonstro 
{
    private Monstro[] lista;
    private int tamanho;

    public int comparacoes = 0;  
    public int movimentacoes = 0;  

    public OrdenadorMonstro(Monstro[] lista, int tamanho) {
        this.lista = lista;
        this.tamanho = tamanho;
    }

    public void ordenar(int k) 
    {
        k = Math.min(k, tamanho);
        quicksort(0, k - 1);
    }

    private void quicksort(int esquerda, int direita) 
    {
        if (esquerda < direita) 
        {
            int pivo = particionar(esquerda, direita);
            quicksort(esquerda, pivo - 1);
            quicksort(pivo + 1, direita);
        }
    }

    private int particionar(int esquerda, int direita) 
    {
        Monstro pivo = lista[direita]; 
        int i = esquerda - 1;

        for (int j = esquerda; j < direita; j++) 
        {
            comparacoes++;
            if (lista[j].getVersao() < pivo.getVersao() || 
                (lista[j].getVersao() == pivo.getVersao() && lista[j].getApelido().compareTo(pivo.getApelido()) < 0)) 
            {
                i++;
                trocar(i, j);
                movimentacoes++;
            }
        }

        trocar(i + 1, direita);
        movimentacoes++;
        return i + 1;
    }

    private void trocar(int i, int j) 
    {
        Monstro temp = lista[i];
        lista[i] = lista[j];
        lista[j] = temp;
    }

    public void exibir() 
    {
        for (int i = 0; i < tamanho; i++) 
        {
            if (lista[i] != null) 
            { 
                System.out.println(lista[i].exibir());
            }
        }
    }
}

public class QuickSortParcial
{
    public static Monstro buscarMonstroPorCodigo(String codigo, ArrayList<Monstro> lista) 
    {
        Monstro resultado = null;
        for (Monstro monstro : lista) 
        {
            if(monstro.getCodigo().equals(codigo))
            {
                resultado = monstro;
            }
        }
        return resultado;
    }

    public static void main(String[] args) 
    {
        int contador = 0;
        ArrayList<Monstro> listaMonstros = new ArrayList<Monstro>();
        long tempoInicial = System.currentTimeMillis();

        try (Scanner scanner = new Scanner(System.in);
             BufferedReader br = new BufferedReader(new FileReader("/tmp/pokemon.csv"))) 
        {
            ArrayList<Monstro> monstrosEncontrados = new ArrayList<>();
            String linha;

            br.readLine(); // Ignora a primeira linha (cabeçalho)

            while ((linha = br.readLine()) != null) 
            {
                Monstro monstro = new Monstro();
                monstro.lerDados(linha);
                monstrosEncontrados.add(monstro);
            }

            String codigo;
            do
            {
                contador++;
                codigo = scanner.nextLine();
                Monstro aux = buscarMonstroPorCodigo(codigo, monstrosEncontrados);
                if(aux != null) listaMonstros.add(aux);
            } while(!codigo.equals("FIM"));

        } 
        catch (IOException e) 
        {
            System.err.println("Erro de I/O: " + e.getMessage());
        }

        Monstro[] array = new Monstro[contador]; 
        int contador2 = 0;

        for(Monstro monstro : listaMonstros)
        {
            array[contador2++] = monstro;
        }

        OrdenadorMonstro ordenador = new OrdenadorMonstro(array, contador);

        int k = 11;  
        ordenador.ordenar(k);  

        long tempoFinal = System.currentTimeMillis(); 
        long tempoTotal = tempoFinal - tempoInicial; 

        try (FileWriter escritorLog = new FileWriter("848324_quicksortparcial.txt")) 
        {
            escritorLog.write("848324\t" + ordenador.comparacoes + "\t" + ordenador.movimentacoes + "\t" + tempoTotal + "ms\n");
        } 
        catch (IOException e) 
        {
            System.err.println("Erro ao escrever o arquivo de log: " + e.getMessage());
        }

        for(int i = 0; i < Math.min(10, contador - 1); i++) 
        {
            System.out.println(array[i].exibir());
        }
        
    }
}
