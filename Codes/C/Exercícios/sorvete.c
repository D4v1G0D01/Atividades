#include <stdio.h>
#include <stdlib.h>

void ordenarPorInsercao(int paresDeValores[][2], int tamanho) {
    for (int i = 1; i < tamanho; i++) {
        int valorAtual[2] = {paresDeValores[i][0], paresDeValores[i][1]};
        int j = i - 1;

        // Desloca elementos maiores que o valorAtual para a direita
        while (j >= 0 && (paresDeValores[j][0] > valorAtual[0] || 
                          (paresDeValores[j][0] == valorAtual[0] && paresDeValores[j][1] > valorAtual[1]))) {
            paresDeValores[j + 1][0] = paresDeValores[j][0];
            paresDeValores[j + 1][1] = paresDeValores[j][1];
            j--;
        }
        paresDeValores[j + 1][0] = valorAtual[0];
        paresDeValores[j + 1][1] = valorAtual[1];
    }
}

int main() {
    int numPessoas, numIntervalos, numTeste = 1;

    scanf("%d %d", &numPessoas, &numIntervalos);

    while (numPessoas != 0 || numIntervalos != 0) {
        int intervalos[numIntervalos][2];

        // Leitura dos intervalos de cada pessoa
        for (int i = 0; i < numIntervalos; i++) {
            scanf("%d %d", &intervalos[i][0], &intervalos[i][1]);
        }

        // Ordenação dos intervalos
        ordenarPorInsercao(intervalos, numIntervalos);

        int inicioIntervalo = intervalos[0][0];
        int fimIntervalo = intervalos[0][1];

        printf("Teste %d\n", numTeste++);

        // Combinação de intervalos sobrepostos
        for (int i = 1; i < numIntervalos; i++) {
            if (intervalos[i][0] <= fimIntervalo) {
                // Ajusta o fim do intervalo se houver sobreposição
                fimIntervalo = intervalos[i][1] > fimIntervalo ? intervalos[i][1] : fimIntervalo;
            } else {
                // Imprime o intervalo combinado
                printf("%d %d\n", inicioIntervalo, fimIntervalo);
                inicioIntervalo = intervalos[i][0];
                fimIntervalo = intervalos[i][1];
            }
        }

        // Imprime o último intervalo
        printf("%d %d\n", inicioIntervalo, fimIntervalo);

        // Leitura dos próximos valores de numPessoas e numIntervalos
        scanf("%d %d", &numPessoas, &numIntervalos);
    }

    return 0;
}