#include <stdio.h>

int main() {
    int n;
    double valor;

    // Lê o número de valores a serem lidos
    scanf("%d", &n);

    // Abre o arquivo para escrita binária
    FILE *arquivo = fopen("numeros.txt", "wb");
    if (arquivo == NULL) {
      
        return 1; // Indica erro
    }

    // Escreve os valores no arquivo
    for (int i = 0; i < n; i++) {
        scanf("%lf", &valor);
        fwrite(&valor, sizeof(double), 1, arquivo);
    }

    // Fecha o arquivo após a escrita
    fclose(arquivo);

    // Reabre o arquivo para leitura binária
    arquivo = fopen("numeros.txt", "rb");
    if (arquivo == NULL) {
        
        return 1; // Indica erro
    }

    // Posiciona o ponteiro no final do arquivo
    fseek(arquivo, 0, SEEK_END);
    long tamanhoArquivo = ftell(arquivo);

    // Lê e exibe os valores de trás para frente
    for (int i = 0; i < n; i++) {
        // Move o ponteiro para o início do próximo double (de trás para frente)
        fseek(arquivo, tamanhoArquivo - (i + 1) * sizeof(double), SEEK_SET);

        // Lê o double
        fread(&valor, sizeof(double), 1, arquivo);

        // Exibe o valor
        printf("%.3f\n", valor); 
    }

    // Fecha o arquivo após a leitura
    fclose(arquivo);

    return 0;
}