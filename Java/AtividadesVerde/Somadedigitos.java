import java.util.Scanner;

public class Somadedigitos {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) { 
            String numeros = sc.nextLine();

            if (numeros.equals("FIM")) { // Se for "FIM", encerra o loop
                break;
            }

            int soma = 0;

            for (int i = 0; i < numeros.length(); i++) {
                if (Character.isDigit(numeros.charAt(i))) { // Garante que é um dígito
                    soma += numeros.charAt(i) - '0'; // Converte char para int
                }
            }

            System.out.println(soma);
        }
        
        sc.close();
    }
}
