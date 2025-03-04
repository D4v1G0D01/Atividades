package Estudo.Ativs;

public class Ativ7 {
    class CelulaPilha {
        int elemento;
        CelulaPilha prox;
    }
    
    class CelulaLista {
        CelulaPilha topo;
        CelulaLista prox;
    }
    
    class Lista {
        CelulaLista inicio;
        CelulaLista fim;


    }

public CelulaLista maiorPilha(){

    if(topo == null){
        return null;
    }

        CelulaLista maior = inicio;
        int maiortamanho = tamanhoPilha(maior.topo);
        CelulaLista atual = inicio.prox;

        while(atual != null){
            int tamanhoAtual = tamanhoPilha(atual.topo);

            if(tamanhoAtual > maiortamanho){
                maior = atual;
                maiortamanho = tamanhoAtual;
            }



        }


}

private int tamanhoPilha(CelulaPilha topo){

        int tamanho = 0;
        CelulaPilha atual = topo;


    while(atual != null){
        tamanho ++;
        atual = atual.prox;
    }
        return tamanho;


}



}
