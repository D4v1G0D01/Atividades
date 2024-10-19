import java.util.Scanner;

public class VerificadorPalindromo {

    public static boolean ehPalindromoRecursivo(String str) {
        

        if (str.length() <= 1) {
            return true; // Caso base: string vazia ou com um caractere é palíndromo
        } else {
            if (str.charAt(0) != str.charAt(str.length() - 1)) {
                return false; // Primeiro e último caracteres diferentes
            } else {
                // Chama recursivamente para a substring interna
                return ehPalindromoRecursivo(str.substring(1, str.length() - 1));
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String linha = scanner.nextLine();
            boolean ehPalindromo = ehPalindromoRecursivo(linha);
            System.out.println(ehPalindromo ? "SIM" : "NAO");
        }

        scanner.close();
    }
}