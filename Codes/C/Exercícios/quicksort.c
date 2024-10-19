#include <stdio.h>
void swap(int *x, int *y){ 
   int temp;
   temp=*x;
   *x=*y;
   *y=temp;
}
void Quicksort(int vetor[], int começo, int fim)
{
    int i = começo, j = fim, pivo = vetor[(começo + fim) / 2];

    while(i <= j)
    {
        while(vetor[i] < pivo)
        i++
        while(vetor[j] > pivo)
        j--;

        if(i <= j)
        {
            swap(&vetor[i], &vetor[j]);
            i++;
            j--;
       } 
    Quicksort(começo, j);
    if(i < fim)
    Quicksort(i,fim);
    }

}



int main()
{
    int n = 10;
    int começo = 0;
    int fim = n - 1;
    int vetor[n];
    for(int i = 0; i < n; i++)
    {
        scanf("%d", &vetor[i]);
    }
    
    
    Quicksort(vetor,começo, fim)  
    
return 0;
}