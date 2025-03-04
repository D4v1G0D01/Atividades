import java.util.Scanner;


public class Stringinvertida {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        String palavra = sc.nextLine();
        
        while(!palavra.equals("FIM")){
            
            char [] palavraC = palavra.toCharArray();
            int tam = palavra.length() - 1;
            
            for(int i = 0; i <= tam / 2; i++)
            {
                char temp = palavraC[i];
                palavraC[i] = palavraC[tam - i];
                palavraC[tam - i] = temp;
            }

            System.out.println( new String (palavraC));
            palavra = sc.nextLine();
        }

        sc.close();
    }
}
