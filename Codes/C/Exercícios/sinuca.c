#include <stdio.h>
#include <stdlib.h>

int main()
{
    int quantidadeBolas;
    int coresBolas[64];

    while(scanf("%i", &quantidadeBolas) == 1) // Lê e armazena um item corretamente por isso "==1"
    {
        for(int i = 0; i < quantidadeBolas; i++)
        {
            scanf("%i", &coresBolas[i]); // Lê as cores das bolas
        }

        while(quantidadeBolas > 1)
        {
            for(int i = 0; i < quantidadeBolas - 1; i++)
            {
                if(coresBolas[i] == coresBolas[i + 1])
                {
                    coresBolas[i] = 1; // Marca como "iguais"
                }
                else coresBolas[i] = -1; // Marca como "diferentes"
            }
            quantidadeBolas--; // Reduz o número de bolas a serem comparadas
        }

        if(coresBolas[0] == 1)
        {
            printf("preta\n"); // Todas as bolas são da mesma cor (pretas)
        }
        else printf("branca\n"); // Há bolas de cores diferentes
    }

    return 0;
}