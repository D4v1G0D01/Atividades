import java.util.Scanner;


public class Palindromorecursivo{


    private static boolean ehPalindromo(String palavra, int comeco, int fim)
    {
        char[] palavraC = palavra.toCharArray();
    
        if(comeco >= fim)
         return true;//É palíndromo
        
        if(palavraC[comeco] != palavraC[fim - comeco])
         return false;//Não é palíndromo
    
         return (ehPalindromo(palavra, comeco + 1, fim));  

    }


    public static void main(String[] args) {
        Scanner sc = new Scanner (System.in);
        String palavra = sc.nextLine();

        while(!palavra.equals("FIM"))
        {   
            int comeco = 0;
            int fim = palavra.length() - 1;
            if(ehPalindromo(palavra, comeco, fim))
             System.out.println("SIM");
             else
             System.out.println("NAO");     
             palavra = sc.nextLine();       
        }

        sc.close();
    }

}