

import java.util.Random;
import java.util.Scanner;

public class Alteracaoaleatoria {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random gerador = new Random();
        gerador.setSeed(4);

       
        String palavra = sc.nextLine();

        while(!palavra.equals("FIM"))
        {
            char letra1 = (char) ('a' + (Math.abs(gerador.nextInt()) % 26)); // Primeira letra aleatória
            char letra2 = (char) ('a' + (Math.abs(gerador.nextInt()) % 26)); // Segunda letra aleatória
            while (letra1 == letra2) {
                letra2 = (char) ('a' + (Math.abs(gerador.nextInt()) % 26));
            }

            char [] palavraC = palavra.toCharArray(); 
            int tam = palavra.length() - 1;

            for(int i = 0; i < tam; i++)
            {
                if(palavraC[i] == letra1)
                palavraC[i] = letra2;
            }
            System.out.println(palavraC);
            palavra = sc.nextLine();
        }
        sc.close();
    }
}
