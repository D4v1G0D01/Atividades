import java.util.Scanner;

public class Anagrama {


    private static boolean ehAnagrama(String palavra, String palavra2){

        char[] palavraC = palavra.toCharArray();
        char[] palavra2C = palavra2.toCharArray();
        int tam = palavra.length() - 1;
        int count = 0;
        int menorC = 0;
        if(palavra.length() < palavra2.length() || palavra.length() == palavra2.length())
        menorC = palavra.length();
        else
        menorC = palavra2.length();

        for(int i = 0; i <= tam; i++)
        {
           for(int j = 0; j <= tam; j++)
           {
            if(palavraC[i] == palavra2C[j])
            count ++;
           }
        }
        if (count == menorC)
        return true; // É anagrama
        else
        return false; // Não é anagrama

    }




    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String palavra = sc.nextLine();
        String palavra2 = sc.nextLine();

        while(!palavra.equals("FIM"))
        {
            if(ehAnagrama(palavra, palavra2))
            System.out.println("SIM");
            else 
            System.out.println("NAO");

             palavra = sc.nextLine();
             
             if(palavra.equals("FIM"))
             break;

             else
             palavra2 = sc.nextLine();
    
        }

        sc.close();
    }
}
