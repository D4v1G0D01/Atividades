import java.util.Scanner;


public class Palindromo{


    private static boolean ehPalindromo(String palavra)
    {
        char[] palavraC = palavra.toCharArray();
        int fim = palavra.length() - 1;
        for(int i = 0; i <= fim; i++)
        {
            if(palavraC[i] != palavraC[fim - i])
            return false;
        }
        return true;
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner (System.in);
        String palavra = sc.nextLine();

        while(!palavra.equals("FIM"))
        {
            if(ehPalindromo(palavra))
             System.out.println("SIM");
             else
             System.out.println("NAO");     
             palavra = sc.nextLine();       
        }

        sc.close();
    }

}