import java.util.ArrayList;
import java.util.Scanner;

public class Sorvete {

    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);

        int numSabores; 
        int numFuncionarios; 

        ArrayList<int[]> turnos; // Lista para armazenar os turnos de trabalho
        int numeroTeste = 1;

        numSabores = leitor.nextInt();
        numFuncionarios = leitor.nextInt();

        while (numSabores != 0 || numFuncionarios != 0) {
            turnos = new ArrayList<>();

            // Leitura dos turnos de trabalho
            for (int i = 0; i < numFuncionarios; i++) {
                int[] turno = new int[2];
                turno[0] = leitor.nextInt(); // Início do turno
                turno[1] = leitor.nextInt(); // Fim do turno
                turnos.add(turno);
            }

            ordenarPorInsercao(turnos);

            // Processo de unificação dos turnos
            int inicioTurno = turnos.get(0)[0];
            int fimTurno = turnos.get(0)[1];

            System.out.println("Teste " + numeroTeste++);

            for (int i = 1; i < numFuncionarios; i++) {
                int[] turnoAtual = turnos.get(i);

                if (turnoAtual[0] <= fimTurno) {
                    // Os turnos se sobrepõem, ajustar o fim do turno
                    fimTurno = Math.max(fimTurno, turnoAtual[1]);
                } else {
                    // Não se sobrepõem, exibe o intervalo atual
                    System.out.println(inicioTurno + " " + fimTurno);
                    inicioTurno = turnoAtual[0];
                    fimTurno = turnoAtual[1];
                }
            }

            // Exibe o último turno
            System.out.println(inicioTurno + " " + fimTurno);

            // Ler o próximo teste
            numSabores = leitor.nextInt();
            numFuncionarios = leitor.nextInt();
        }

        leitor.close();
    }

    public static void ordenarPorInsercao(ArrayList<int[]> lista) {
        for (int i = 1; i < lista.size(); i++) {
            int[] chave = lista.get(i);
            int j = i - 1;

            // Move os elementos maiores que a chave uma posição à frente
            while (j >= 0 && (lista.get(j)[0] > chave[0] || 
                            (lista.get(j)[0] == chave[0] && lista.get(j)[1] > chave[1]))) {
                lista.set(j + 1, lista.get(j));
                j--;
            }
            lista.set(j + 1, chave);
        }
    }
}