import java.util.Scanner;

public class Ciframentodecesar {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in, "UTF-8");

        String palavra = sc.nextLine();
        int chave = 3; // Valor fixo da chave

        while (!palavra.equals("FIM")) {
            StringBuilder palavraCifrada = new StringBuilder();

            for (char c : palavra.toCharArray()) {
                if (Character.isLetter(c)) { 
                    palavraCifrada.append((char) (c + chave));
                } else {
                    palavraCifrada.append(c);
                }
            }

            System.out.println(palavraCifrada);
            palavra = sc.nextLine();
        }

        sc.close();
    }
}
