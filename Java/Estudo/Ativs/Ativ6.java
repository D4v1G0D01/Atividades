package Estudo.Ativs;

//Implemente uma função chamada isArvoreBuscaBnaria que recebe
//como parâmentro a raiz de uma arvore binaria e retorna verdadeiro
//se a árvore for uma árvore de busca binária válida, e falso caso
//contrário.


public class Ativ6 {
    class Node {
        int valor;
        Node esquerda;
        Node direita;
    
        public Node(int valor) {
            this.valor = valor;
            this.esquerda = null;
            this.direita = null;
        }
    }
    
    public class ArvoreBinaria {
    
        public boolean isArvoreBuscaBinaria(Node raiz) {
            return verificaBST(raiz, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
    
        private boolean verificaBST(Node nodo, int min, int max) {
            // Caso base: nodo vazio é considerado uma BST válida
            if (nodo == null) {
                return true;
            }
    
            // Verifica se o valor do nodo está fora do intervalo permitido
            if (nodo.valor <= min || nodo.valor >= max) {
                return false;
            }
    
            // Verifica recursivamente a subárvore esquerda e direita
            return verificaBST(nodo.esquerda, min, nodo.valor) &&
                   verificaBST(nodo.direita, nodo.valor, max);
        }
    
        public static void main(String[] args) {
            ArvoreBinaria arvore = new ArvoreBinaria();
    
            // Criando uma árvore de busca binária
            Node raiz = new Node(10);
            raiz.esquerda = new Node(5);
            raiz.direita = new Node(15);
            raiz.esquerda.esquerda = new Node(2);
            raiz.esquerda.direita = new Node(7);
            raiz.direita.esquerda = new Node(12);
            raiz.direita.direita = new Node(20);
    
            System.out.println(arvore.isArvoreBuscaBinaria(raiz)); // Deve retornar true para BST válida
        }
    }
    
}
