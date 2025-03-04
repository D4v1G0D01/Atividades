package Estudo.Ativs;

class No{
    int valor;
    No prox;

    public No(int valor){
        this.valor = valor;
        this.prox = null;
    }
}


public class Ativ8 {
    public class ListaEncadeada{
        private No inicio;

            //Métodos:
        
            //AdicionarAoFinal();
            //RemoverElemento();
            //Buscar();
            // ImprimirListas();
        
           



            //NÃO TENTE ENTENDER MUITO OS DETALHES DE NOMES DE VARIAVEIS, PROVAVELMENTE TEM CLASSES FALTANDO 
            //PARA FUNCIONAR CERTO
            //NÃO GOSTOU FAZ O L
//============================================================================================================//
        
         //IMPLEMENTAÇÕES DE MÉTODOS PARA TAD'S FLEXÍVEIS


            public void AdicionarAoFinal(int valor){
                No novoNo = new No(valor);

                if(inicio == null){
                    inicio = novoNo;
                }
                
                else {
                    No atual = inicio;

                    while(atual.prox != null){
                        atual = atual.prox;
                    }

                    atual.prox = novoNo;
                }
            }

            public void RemoverElemento(int valor){

                    if(inicio == null){
                        return;                        
                    }

                    if(inicio.valor == valor){
                        inicio = inicio.prox;
                    }

                    while(atual.prox != null && atual.prox.valor != valor){
                        atual = atual.prox;
                    }

                    if(atual.prox == null){
                        System.out.println("Valor não encontrado na lista.");
                    }
                    else{
                        atual.prox = atual.prox.prox;
                    }
            }

            public boolean buscar(int valor){
                No atual = inicio;

                while(atual != null){
                    if(atual.valor == valor){
                        return true; //Valor encontrado
                    }

                    atual = atual.prox;
                }
                return false; //Não encontrado

            }

//====================================================================================================
//QUESTAO PROVA ANTIGA, CONTAR PALAVRAS QUE COMEÇAM COM A MESMA LETRA E TEM O MESMO TAMANHO (length)

            int contarPalavras(String padrao){

                if(padrao == null || padrao.isEmpty()){
                    return 0;
                }

                char primeiraLetra = padrao.charAt(0);
                int tamanhoPadrao = padrao.length();


                No noLetra = buscarLetra(raiz, primeiraLetra);

                if(noLetra == null || noLetra.raiz == null){
                    return 0;
                }

                return contarPalavrasPorTamanho(noLetra.raiz, tamanhoPadrao);

            }

            private No buscarLetra(No atual, char letra){

                if(atual == null){
                    return null;
                }

               if(atual.letra == letra){
                return atual;
               }

            else if(atual.letra > letra){
                return buscarLetra(atual.esquerda, letra);
            }
            else{
                return buscarLetra(atual.direita, letra);
            }                

            }

        private int contarPalavrasPorTamanho(No2 atual, int tamanho){

            if(atual == null){
                return 0;
            }

            int contador = 0;

            if(atual.palavra.length() == tamanho){
                contador ++;
            }

            contador += contarPalavrasPorTamanho(atual.esquerda, tamanho);
            contador += contarPalavrasPorTamanho(atual.direita, tamanho);

            return contador;
        }
    }


    



}
