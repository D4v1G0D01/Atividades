import java.util.Scanner;
import java.util.Stack;

public class Estacionamento {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int N = scanner.nextInt();
        int K = scanner.nextInt();
        
        while (N != 0 && K != 0) {

            Stack<Integer> estacionamento = new Stack<>();
            boolean possivel = true;

            for (int i = 0; i < N; i++) {
                int chegada = scanner.nextInt();
                int saida = scanner.nextInt();

                // Remover todos os carros que já saíram antes da chegada atual
                while (!estacionamento.isEmpty() && estacionamento.peek() <= chegada) {
                    estacionamento.pop();
                }

                // Verificar se o estacionamento está cheio
                if (estacionamento.size() >= K) {
                    possivel = false;
                } else {
                    // Verificar se algum carro na pilha tem horário de saída antes do carro atual
                    if (!estacionamento.isEmpty() && estacionamento.peek() < saida) {
                        possivel = false;
                    }
                    // Se for possível estacionar, adiciona o carro na pilha
                    estacionamento.push(saida);
                }
            }

            System.out.println(possivel ? "Sim" : "Nao");

            N = scanner.nextInt();
            K = scanner.nextInt();
        }
        
        scanner.close();
    }
}