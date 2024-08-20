package soma_numeros;
import java.util.Scanner;

public class Soma_dois_numeros {
	public static void main() {
		Scanner sc = new Scanner(System.in);
		
		int A = sc.nextInt();
		int B = sc.nextInt();
		int C = A + B;
		
		System.out.printf("%d", C);
		sc.close();
	}
}
