#include <stdio.h>
#include <string.h>

int ehPalindromo(char palavra[]) {
    int i, j;
    int tamanho = strlen(palavra);

    for (i = 0, j = tamanho - 1; i < j; i++, j--) {
        if (palavra[i] != palavra[j]) {
            return 0; // Não é palíndromo
        }
    }

    return 1; // É palíndromo
}

int main() {
    char palavra[100];


    while (strcmp(palavra, "FIM") != 0) {
    scanf("%s", palavra);
        if (ehPalindromo(palavra)) {
            printf("SIM\n");
        } else {
            printf("NAO\n");
        }

      
        scanf("%s", palavra);
    }

    return 0;
}