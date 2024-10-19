import java.util.Scanner;

public class CiframentoCesar {

    public static String cifrarCesarRecursivo(String texto) {
        if (texto.isEmpty()) {
            return ""; // Caso base: string vazia
        } else {
            char primeiroCaractere = texto.charAt(0);
            char caractereCifrado = cifrarCaractere(primeiroCaractere);
            return caractereCifrado + cifrarCesarRecursivo(texto.substring(1));
        }
    }

    public static char cifrarCaractere(char caractere) {
        if (Character.isLetter(caractere)) {
            char base = Character.isLowerCase(caractere) ? 'a' : 'A';
            int deslocamento = (caractere - base + 3) % 26; // Deslocamento de 3
            return (char) (base + deslocamento);
        } else {
            return caractere; // Mantém caracteres não alfabéticos
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String linha = scanner.nextLine();
            String linhaCifrada = cifrarCesarRecursivo(linha);
            System.out.println(linhaCifrada);
        }

        scanner.close();
    }
}