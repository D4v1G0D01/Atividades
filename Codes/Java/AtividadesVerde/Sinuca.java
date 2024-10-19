import java.util.Scanner;

public class Sinuca 
{
    public static void main (String[] args)
    {
        Scanner sc = new Scanner(System.in);

        while(sc.hasNext())
        {
            int N = sc.nextInt();
            int[] bolas = new int[N];
            for(int i = 0; i < N; i++)
            {
                bolas[i] = sc.nextInt(); 
            }

            while(N > 1)
            {
                for(int i = 0; i < N - 1; i++)
                {
                    if(bolas[i] == bolas[i + 1])
                    {
                        bolas[i] = 1;
                    }
                    else bolas[i] = -1;
                }
                N--; 
            }

            if(bolas[0] == 1)
            {
                System.out.println("preta");
            }
            else System.out.println("branca");
        }
        sc.close();
    }
}