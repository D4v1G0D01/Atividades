package Estudo.Ativs;

import java.util.Scanner;


public class Ativ2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
     
        int T = sc.nextInt();
        
        int count;

        for(int i = 0; i < T; i++)
        {
            int N = sc.nextInt();
            int[] numeros = new int[N];
            count = 0;
            for(int j = 0; j < N; j++)
            {
               numeros[j] = sc.nextInt();
            }
            BubbleSort(numeros,count);
            
            System.out.printf("%d \n", count);
        }

    sc.close();
}


public static int BubbleSort(int[] vetorBubbleSort, int count) {
    int temp;

    for (int i = 0; i < vetorBubbleSort.length - 1; i++) {
        for (int j = 0; j < vetorBubbleSort.length - i - 1; j++) { // Corrigido para começar em 0
            if (vetorBubbleSort[j] > vetorBubbleSort[j + 1]) {
                // Troca os elementos adjacentes se estiverem na ordem errada
                temp = vetorBubbleSort[j];
                vetorBubbleSort[j] = vetorBubbleSort[j + 1];
                vetorBubbleSort[j + 1] = temp;
                
                count++; // Conta a troca
            }
        }
    }
return count;
}
}