import java.util.Random;

public class Ativ1 {

    public static int[] gerarVetor(int tamanho, int maxValor) {
        Random rand = new Random();
        int[] vetor = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = rand.nextInt(maxValor); //maxValor - 1
        }
        return vetor;
    }

    public static void imprimirVetor(int[] vetor) {
        for (int num : vetor) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // Defina o tamanho do vetor e o valor máximo dos números aleatórios
        int tamanhoVetor = 1000;
        int maxValor = 10000;

        int[] vetorOriginal = gerarVetor(tamanhoVetor, maxValor);

//=========================================================================================
        // Selection Sort
        int[] vetorSelectionSort = vetorOriginal.clone();
        long startTime = System.nanoTime();
        SelectionSort.sort(vetorSelectionSort);  // Descomente após implementar
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        System.out.println("Selection Sort - Tempo de execução: " + duration + "ms");

//=========================================================================================
        // Insertion Sort
        int[] vetorInsertionSort = vetorOriginal.clone();
        startTime = System.nanoTime();
        // InsertionSort.sort(vetorInsertionSort);  // Descomente após implementar
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000;
        System.out.println("Insertion Sort - Tempo de execução: " + duration + "ms");

//=========================================================================================
        // Bubble Sort
        int[] vetorBubbleSort = vetorOriginal.clone();
        startTime = System.nanoTime();
        // BubbleSort.sort(vetorBubbleSort);  // Descomente após implementar
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000;
        System.out.println("Bubble Sort - Tempo de execução: " + duration + "ms");

//=========================================================================================

        // Shell Sort
        int[] vetorShellSort = vetorOriginal.clone();
        startTime = System.nanoTime();
        // ShellSort.sort(vetorShellSort);  // Descomente após implementar
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000;
        System.out.println("Shell Sort - Tempo de execução: " + duration + "ms");
//=========================================================================================

        // Merge Sort
        int[] vetorMergeSort = vetorOriginal.clone();
        startTime = System.nanoTime();
        // MergeSort.sort(vetorMergeSort);  // Descomente após implementar
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000;
        System.out.println("Merge Sort - Tempo de execução: " + duration + "ms");
//=========================================================================================

        // Quick Sort
        int[] vetorQuickSort = vetorOriginal.clone();
        startTime = System.nanoTime();
        // QuickSort.sort(vetorQuickSort);  // Descomente após implementar
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000;
        System.out.println("Quick Sort - Tempo de execução: " + duration + "ms");
//=========================================================================================

        // Heap Sort
        int[] vetorHeapSort = vetorOriginal.clone();
        startTime = System.nanoTime();
        // HeapSort.sort(vetorHeapSort);  // Descomente após implementar
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000;
        System.out.println("Heap Sort - Tempo de execução: " + duration + "ms");
//=========================================================================================

        // Counting Sort
        int[] vetorCountingSort = vetorOriginal.clone();
        startTime = System.nanoTime();
        // CountingSort.sort(vetorCountingSort, maxValor);  // Descomente após implementar
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000;
        System.out.println("Counting Sort - Tempo de execução: " + duration + "ms");
//=========================================================================================

        // Radix Sort
        int[] vetorRadixSort = vetorOriginal.clone();
        startTime = System.nanoTime();
        // RadixSort.sort(vetorRadixSort);  // Descomente após implementar
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000;
        System.out.println("Radix Sort - Tempo de execução: " + duration + "ms");
    }

//=============================================================================================
//                                      Algoritmos de ordenação


public static void SelectionSort(int [] vetorSelectionSort){
    int temp;
   

    for(int i = 0; i < tamanho - 1; i++)
    {
        int min = i;

        for(int j = i + 1; j < tamanho + 1; j++)
        {
            if(vetorSelectionSort[i] > vetorSelectionSort[j])
            min = j;
        }

        temp = vetorSelectionSort[i];
        vetorSelectionSort[i] = vetorSelectionSort[min];
        vetorSelectionSort[min] = temp;
    }

    }

    public static void InsertionSort(int [] vetorInsertionSort){

    }
}
