import java.util.Random;
import java.util.Scanner;

public class Aleatorio {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = "";
        while (!input.equals("FIM")) {
            // Lê a entrada do usuário
           input = sc.nextLine();

            // Executa o método e imprime o resultado
            System.out.println(replaceRandomChar(input));
        }

        sc.close();
    }

    public static String replaceRandomChar(String input) {
        Random gerador = new Random();
        gerador.setSeed(4);

        // Sorteia duas letras minúsculas aleatórias
        char firstLetter = (char) ('a' + (Math.abs(gerador.nextInt()) % 26));
        char secondLetter = (char) ('a' + (Math.abs(gerador.nextInt()) % 26));

        // Substitui todas as ocorrências da primeira letra pela segunda
        return input.replace(firstLetter, secondLetter);
    }
}
