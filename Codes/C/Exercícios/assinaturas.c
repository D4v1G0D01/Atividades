#include <stdio.h>
#include <string.h>

int main()
{
    int N = 0, M, quantidade = 0;
    char assinaturaOriginal[51];
    char assinaturaAula[51];
    char nome[21], nomeAula[50];
   
   
    scanf("%d", M);
   while (scanf("%d", &N) == 1 && N != 0)
   {
    
    for(int i = 0; i < N; i++)
    {
        scanf("%s", nome[i]);
        scanf("%s", assinaturaOriginal[i]);
        
    }

       int tamanho = strlen(assinaturaAula);
        for(int j = 0; j < tamanho; j++)
        {
            scanf("%s", nomeAula[j]);
            scanf("%s", assinaturaAula[j]);

           if (!(toupper(assinaturaAula[j]) == toupper(assinaturaOriginal[j]))) {
            quantidade ++;
           }
        }
        

    printf("%d", quantidade);
    quantidade = 0;
   }

  return 0;
}