import java.util.Scanner;


public class Isiterativo {
    public static boolean soVogais(String s) {
        return s.matches("[aeiouAEIOU]+");
    }
    
    public static boolean soConsoantes(String s) {
        return s.matches("[bcdfghjklmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ]+");
    }
    
    public static boolean eInteiro(String s) {
        return s.matches("[-]?\\d+");
    }
    
    public static boolean eReal(String s) {
        return s.matches("[-]?\\d+(\\.\\d+)?");
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String entrada = sc.nextLine();
            if (entrada.equals("FIM")) break;
            
            boolean x1 = soVogais(entrada);
            boolean x2 = soConsoantes(entrada);
            boolean x3 = eInteiro(entrada);
            boolean x4 = eReal(entrada);
            
            System.out.println((x1 ? "SIM" : "NAO") + " " + 
                               (x2 ? "SIM" : "NAO") + " " + 
                               (x3 ? "SIM" : "NAO") + " " + 
                               (x4 ? "SIM" : "NAO"));
        }
        sc.close();
    }
}

