#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

bool ehpalindromo(const char string[])
{
  int inicio = 0, fim = strlen(string) - 1;


  while(inicio<fim)
  {
      if(string[inicio] != string[fim])
        {
            return 0;
        }
        inicio ++;
        fim --;
  }
  return 1;



}
int main()
{
    char string1[100];
    scanf(" %s",string1);

  while(string != "FIM")
  {
    if(ehpalindromo(string1))
        printf("SIM\n");
    else
        printf("NAO\n");
 }
        return 0;
}
