package Estudo.Ativs;
import java.util.Scanner;
import java.util.ArrayList;


//Classe duende
class Duende{
    String nome;
    int idade;

    public Duende(String nome, int idade){
        this.nome = nome;
        this.idade = idade;
    }
    public int getNome(){
        return nome;
    }
    public int getIdade(){
        return idade;
    }
}


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList <Duende> duendes = new ArrayList<>();

        int N = sc.nextInt();

        for(int i = 0; i < N; i++)
        {
            String nome = sc.nextLine();
            int idade = sc.nextInt();

            duendes.add(new Duende(nome, idade));
        }   

    }
}
