import java.util.Scanner;




    

public class IsRecursivo {

    public static boolean soVogais(String s) {
        if (s.isEmpty()) return true;
        char c = s.charAt(0);
        if ("aeiouAEIOU".indexOf(c) == -1) return false;
        return soVogais(s.substring(1));
    }
    
    public static boolean soConsoantes(String s) {
        if (s.isEmpty()) return true;
        char c = s.charAt(0);
        if ("bcdfghjklmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ".indexOf(c) == -1) return false;
        return soConsoantes(s.substring(1));
    }
    
    public static boolean eInteiro(String s) {
        if (s.isEmpty()) return false;
        if (s.charAt(0) == '-' && s.length() > 1) return eInteiro(s.substring(1));
        if (!Character.isDigit(s.charAt(0))) return false;
        return s.length() == 1 || eInteiro(s.substring(1));
    }
    
    public static boolean eReal(String s) {
        if (s.isEmpty()) return false;
        if (s.charAt(0) == '-' && s.length() > 1) return eReal(s.substring(1));
        int countPontos = s.length() - s.replace(".", "").length();
        if (countPontos > 1) return false;
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

