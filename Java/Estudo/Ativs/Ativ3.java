package Estudo.Ativs;
import java.util.*;

public class elfo{
    public static void main(String args[]){
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext())
        {

            int T = scanner.nextInt();
            scanner.nextLine();
            int N = scanner.nextInt();
            int M = scanner.nextInt();
            scanner.nextLine();

            Rena[] renas = new Rena[N];
            int tam = 0;

            for(int a = 0; a < N; a++){
                String nome = scanner.next();
                int peso = scanner.nextInt();
                int idade = scanner.nextInt();
                float altura = scanner.nextFloat();
                scanner.nextLine();

                renas[tam++] = new Rena(peso, idade, altura, nome);
            }

            for(int i = 1; i < N; i++){
                Rena chave = renas[i];
                int j = i - 1;
                
                while((j >= 0) && (renas[j].getPeso() < chave.getPeso() || (renas[j].getPeso() == chave.getPeso() && renas[j].getIdade() > chave.getIdade()) || 
                (renas[j].getPeso() == chave.getPeso() && renas[j].getIdade() == chave.getIdade() && renas[j].getAltura() > chave.getAltura()) || 
                (renas[j].getPeso() == chave.getPeso() && renas[j].getIdade() == chave.getIdade() && renas[j].getAltura() == chave.getAltura() && renas[j].getNome().compareTo(chave.getNome()) > 0))){
                    
                    renas[j + 1] = renas[j];
                    j--;
                }
                renas[j + 1] = chave;
            }
            
            System.out.println("CENARIO {" + T + "}");

            for(int b = 0; b < M; b++){
                System.out.println(b+1 + " - " + renas[b].getNome());
            }


        }

    }
}

class Rena {
    private int peso;
    private int idade;
    private float altura;
    private String nome;

    // Construtor padrão (sem parâmetros)
    public Rena() {
    }

    // Construtor com todos os parâmetros
    public Rena(int peso, int idade, float altura, String nome) {
        this.peso = peso;
        this.idade = idade;
        this.altura = altura;
        this.nome = nome;
    }

    // Getters e Setters

    // Getter e Setter para o peso
    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    // Getter e Setter para a idade
    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    // Getter e Setter para a altura
    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    // Getter e Setter para o nome
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}