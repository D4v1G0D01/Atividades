#include <stdio.h>
#include <string.h>
#include <stdbool.h>
int ehPalindromo(char palavra[]) {
    int i, j;
    int tamanho = strlen(palavra) - 1;
    bool teste = true;
    for (i = 0, j = tamanho; i < j; i++, j--) {
        if (palavra[i] != palavra[j]) {
          
            teste = false;
            return teste; // Não é palíndromo
        }
    }
    
    return teste; // É palíndromo
}

int main() {
    char palavra[100];

    scanf("%s", palavra);
    
    while (strcmp(palavra, "FIM") != 0) {
        if (ehPalindromo(palavra)) {
            printf("SIM\n");
        } else {
            printf("NAO\n");
        }
        scanf("%s", palavra);

      
    }

    return 0;
}