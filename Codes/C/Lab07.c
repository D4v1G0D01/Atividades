#include <stdio.h>
#include <stdlib.h>

// Função que conta o número de inversões em um array
int contarInversoes(int arr[], int temp[], int left, int right);
int merge(int arr[], int temp[], int left, int mid, int right);

int main() {
    int N;

    for(int i = 0; i < 3; i++) 
    {  

        scanf("%d", &N);
        

        int competidoresLargada[N];
        int competidoresChegada[N];
        int posicao[N+1]; // Mapeia a posição final de cada competidor
        int temp[N]; // Array temporário para o merge sort

        // Lê os competidores na ordem do grid de largada
        for (int i = 0; i < N; i++) {
            scanf("%d", &competidoresLargada[i]);
        }

        // Lê os competidores na ordem do grid de chegada
        for (int i = 0; i < N; i++) {
            scanf("%d", &competidoresChegada[i]);
        }

        // Mapeando o grid de chegada para a posição da largada
        for (int i = 0; i < N; i++) {
            posicao[competidoresChegada[i]] = i; // Posição final de cada competidor
        }

        // Substitui o array de largada pelas posições do grid de chegada
        for (int i = 0; i < N; i++) {
            competidoresLargada[i] = posicao[competidoresLargada[i]];
        }

        // Conta o número de inversões (ultrapassagens)
        int ultrapassagens = contarInversoes(competidoresLargada, temp, 0, N - 1);

        // Imprime o número mínimo de ultrapassagens
        printf("%d\n", ultrapassagens);
    }

    return 0;
}

// Função que conta o número de inversões em um array usando merge sort
int contarInversoes(int arr[], int temp[], int left, int right) {
    int mid, inversoes = 0;
    if (right > left) {
        mid = (right + left) / 2;

        // Contagem de inversões na parte esquerda e direita
        inversoes += contarInversoes(arr, temp, left, mid);
        inversoes += contarInversoes(arr, temp, mid + 1, right);

        // Contagem de inversões durante o merge
        inversoes += merge(arr, temp, left, mid + 1, right);
    }
    return inversoes;
}

// Função que realiza o merge e conta as inversões
int merge(int arr[], int temp[], int left, int mid, int right) {
    int i = left;   // Índice da parte esquerda
    int j = mid;    // Índice da parte direita
    int k = left;   // Índice do array temporário
    int inversoes = 0;

    // Combina as duas metades e conta as inversões
    while (i <= mid - 1 && j <= right) {
        if (arr[i] <= arr[j]) {
            temp[k++] = arr[i++];
        } else {
            temp[k++] = arr[j++];
            inversoes += (mid - i); // Todos os elementos à esquerda de i formam uma inversão
        }
    }

    // Copia os elementos restantes da parte esquerda
    while (i <= mid - 1)
        temp[k++] = arr[i++];

    // Copia os elementos restantes da parte direita
    while (j <= right)
        temp[k++] = arr[j++];

    // Copia de volta para o array original
    for (i = left; i <= right; i++)
        arr[i] = temp[i];

    return inversoes;
}
