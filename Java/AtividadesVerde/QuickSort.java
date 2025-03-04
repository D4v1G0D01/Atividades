import java.util.Scanner;
import java.util.random.*;


public class QuickSort {
    public static void main(String[] args){
        Random aleatorio = new Random();
        int [] numeros = new int[10];

        for(int i = 0; i < numeros.length; i++)
        {
            numeros[i] = aleatorio.nextInt(100);
        }

        System.out.println("Antes: ");
        printArray(numeros);
        
            QuickSortFirstPivot(numeros, 0, numeros.length - 1);
            QuickSortLastPivot(numeros, 0, numeros.length - 1);
            QuickSortRandomPivot(numeros, 0, numeros.length - 1);
            QuickSortMedianOfThree(numeros, 0, numeros.length - 1);


            System.out.println("Depois: ");
            printArray(numeros);

    }
    private static void QuickSortFirstPivot ( int [] array , int left , int right ){
        if(left >= righ)
        return;
        
        int pivo = array[0];
        int i = left;
        int j = right;

        while (i < j)
        {
            while(array[i] <= pivo && i < j)
            {
                i++;
            }
            while(array[j] >= pivo && i < j)
            {
                j--;
            }
            swap(array, i, j);
        }
        swap(array, i, right);
        
        QuickSortFirstPivot(array, left, i - 1);
        QuickSortFirstPivot(array, i + 1, right);
        
    }
    private static void QuickSortLastPivot ( int [] array , int left , int right ){
        if(left >= righ)
        return;
        
        int pivo = array[numeros.length - 1];
        int i = left;
        int j = right;

        while (i < j)
        {
            while(array[i] <= pivo && i < j)
            {
                i++;
            }
            while(array[j] >= pivo && i < j)
            {
                j--;
            }
            swap(array, i, j);
        }
        swap(array, i, right);
        
        QuickSortLastPivot(array, left, i - 1);
        QuickSortLastPivot(array, i + 1, right);

    }
    import java.util.Random;

public void QuickSortRandomPivot(int[] array, int left, int right) {
    if (left < right) {
        int pivotIndex = partitionRandomPivot(array, left, right);
        QuickSortRandomPivot(array, left, pivotIndex - 1);
        QuickSortRandomPivot(array, pivotIndex + 1, right);
    }
}

private int partitionRandomPivot(int[] array, int left, int right) {
    Random rand = new Random();
    int randomPivotIndex = left + rand.nextInt(right - left + 1); // Gera pivô aleatório
    swap(array, randomPivotIndex, right); // Coloca o pivô aleatório no final
    return partitionLastPivot(array, left, right); // Usa a função de último pivô
}

    private static void QuickSortMedianOfThree ( int [] array , int left , int right ){
        if(left >= righ)
        return;
        
        int pivo = array[(array[0] + array[numeros.length / 2] + array[numeros.length - 1]) / 3];
        int i = left;
        int j = right;

        while (i < j)
        {
            while(array[i] <= pivo && i < j)
            {
                i++;
            }
            while(array[j] >= pivo && i < j)
            {
                j--;
            }
            swap(array, i, j);
        }
        swap(array, i, right);
        
        QuickSortMedianOfThree(array, left, i - 1);
        QuickSortMedianOfThree(array, i + 1, right);
    }

    private static void swap(int [] vetor, int index1, int index2){
        
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }
    
}
   
