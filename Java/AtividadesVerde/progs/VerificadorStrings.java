import java.util.Scanner;

public class VerificadorStrings {

    public static boolean somenteVogais(String str) {
        return somenteVogaisRecursivo(str, 0); 
    }

    private static boolean somenteVogaisRecursivo(String str, int index) {
        if (index == str.length()) { 
            return true; 
        }

        char c = str.charAt(index);
        if (!"aeiouAEIOU".contains(String.valueOf(c))) {
            return false;
        }

        return somenteVogaisRecursivo(str, index + 1); 
    }

    public static boolean somenteConsoantes(String str) {
        return somenteConsoantesRecursivo(str, 0);
    }

    private static boolean somenteConsoantesRecursivo(String str, int index) {
        if (index == str.length()) {
            return true;
        }

        char c = str.charAt(index);
        if (!Character.isLetter(c) || "aeiouAEIOU".contains(String.valueOf(c))) {
            return false;
        }

        return somenteConsoantesRecursivo(str, index + 1);
    }
    public static boolean ehNumeroInteiro(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean ehNumeroReal(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String linha = scanner.nextLine();
            boolean soVogais = somenteVogais(linha);
            boolean soConsoantes = somenteConsoantes(linha);
            boolean ehInteiro = ehNumeroInteiro(linha);
            boolean ehReal = ehNumeroReal(linha);

            System.out.println((soVogais ? "SIM" : "NAO") + " " +
                               (soConsoantes ? "SIM" : "NAO") + " " +
                               (ehInteiro ? "SIM" : "NAO") + " " +
                               (ehReal ? "SIM" : "NAO"));
        }

        scanner.close();
    }
}