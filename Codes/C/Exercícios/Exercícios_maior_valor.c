#include <stdio.h> 
#include <stdlib.h>

int main() 
{ 
     float A, B, C;
        printf("Digite o primeiro valor real\n");
        scanf("%f", &A);
        printf("Digite o segundo valor real\n");
        scanf("%f", &B);
        printf("Digite o terceiro valor real\n");
        scanf("%f", &C);

        if (A>B && A>C)

         printf("%.1f e maior que %.1f e %.1f", A, B, C);

            else if (B>A && B>C)

                printf("%.1f e maior que %.1f e %.1f", B, A, C);

                else if(C>B && C>A)

                    printf("%.1f e maior que %.1f e %.f", C, B, A);


        
        
    return 0;
}