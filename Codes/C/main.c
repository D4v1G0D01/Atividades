#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int maiusculas(const char *str) {
   
     if (*str == '\0') {
 
            return 0;
        } else {
            int ehMaiuscula = isupper(*str) ? 1 : 0;
            return ehMaiuscula + maiusculas(str + 1); 
        }
    }
 
    
    
 
int main() {
  
  char linha[100];
    
    while (fgets(linha, sizeof(linha), stdin) != NULL) {
        if (linha[strlen(linha) - 1] == '\n') {
            linha[strlen(linha) - 1] = '\0';
        }
        if (strcmp(linha, "FIM") == 0) {
            break;
        }
        int N_maiusculas = maiusculas(linha);
            printf("%d\n", N_maiusculas);
        }
    
    return 0;
    
}