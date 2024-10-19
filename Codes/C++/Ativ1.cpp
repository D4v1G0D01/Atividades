#include <iostream>
#include <string>

#define MAX 5 

class Quadrado{
private: float lado;
 std::string cor;

public: 
    void setLado(float l){
       lado=l;
    }
    float getLado(){
        return lado;
    }
     void setCor(std::string c){
      cor=c;
    }
    std::string getCor(){
        return cor;
    }

    float perimetro(){
        return lado*4;
    }

    float area(){
        return lado*lado;
    }

    void leia(){
        std::cout<<"Lado: ";
        std::cin>> lado;
        std::cout<<"Cor:";
        std::cin.ignore();
        std::getline(std::cin, cor);
    }

    void escreva(){
        std::cout<<"Lado:"<< lado << std::endl;
        std::cout<<"Cor: "<< cor << std::endl;
        std::cout<<"Perimetro"<< perimetro()<< std::endl;
        std::cout<<"Area"<< area()<<std::endl;
    }

};

int main()
{
    Quadrado q1, *pq2 = new Quadrado;
    q1.setLado(5);
    q1.setCor("Azul");
    pq2 ->setLado(4);
    pq2 ->setCor("Rosa");

    Quadrado q3, *pq4 = new Quadrado;
    q3.leia();
    pq4->leia();

    Quadrado *quadrados[MAX];
    int posicaoDoQuadrado=0;

    int opcao;
  do
  {
        std::cout << "\nMenu:\n";
        std::cout << "0 - Sair\n";
        std::cout << "1 - Inserir Quadrado\n";
        std::cout << "2 - Listar Quadrados\n";
        std::cout << "Opção: ";
        std::cin >> opcao;
     switch (opcao)
     {
     case 1: 
        if(posicaoDoQuadrado<MAX)
        {
          quadrados[posicaoDoQuadrado] = new Quadrado;
          quadrados[posicaoDoQuadrado]->leia();
          posicaoDoQuadrado++;
         }
         else{
         std::cout<<"Nao cabem mais quadrados :/ ";
        }
        break;

        case 2:
        for(int i=0; i<posicaoDoQuadrado; i++)
        {
            std::cout<<"\nQuadrado "<<i+1<<": \n";
            quadrados[i]-> escreva();
        }

        break;
         default:
             std::cout<<"Opcao invalida!\n";
         break;
        } 
       
   
  }while(opcao != 0);


    delete pq2;
    delete pq4;
    for (int i = 0; i < posicaoDoQuadrado; i++) {
        delete quadrados[i];
    }

return 0;
}

















