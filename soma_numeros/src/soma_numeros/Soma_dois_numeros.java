package soma_numeros;
import java.util.Scanner;

public class Soma_dois_numeros {
	public static void main() {
		Scanner sc = new Scanner(System.in);//Cria um novo 'Scanner' = 'sc'
		
		int A = sc.nextInt();//Lê a primeira variável
		int B = sc.nextInt();//Lê a segunda variável
		int C = A + B;//Da para 'C', o valor da soma de 'A'+ 'B'
		
		System.out.printf("%d", C);
		sc.close();//Fecha scanner
	}
}
