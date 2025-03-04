package Estudo.Ativs;
import java.util.Scanner;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;


public class Ativ4 {
    public static void main(String [] args){
        Scanner sc = new Scanner(System.in);

        //Variáveis e listas
        int pontocardeal;
        boolean continuar = true;

        Queue oeste = new LinkedList<>();
        Queue leste = new LinkedList<>();
        Queue norte = new LinkedList<>();
        Queue sul = new LinkedList<>();

        while (continuar) {
            int pontoCardeal = sc.nextInt();

            // Verifica a condição de parada
            if (pontoCardeal == 0) {
                continuar = false;
           }

           String aviao = sc.nextLine();

           switch (pontoCardeal) {
            case -1:
            oeste.add(aviao)
             break;
           
             case -2:
             sul.add(aviao);
             break;

             case -3: 
             norte.add(aviao);
             break;

             case -4: 
             leste.add(aviao);
           }

            ArrayList<String> filasaida = new ArrayList<>();

            while(!oeste.isEmpty())
            filasaida.add(oeste.poll());

            while(!norte.isEmpty() || !sul.isEmpty())
            {
                if(!norte.isEmpty())
                filasaida.add(norte.poll());
    
                if(!sul.isEmpty())
                filasaida.add(sul.poll());
            }

            while(!leste.isEmpty())
            {
                filasaida.add(leste.poll());
            }


            for(String aviao : filasaida)
            {
                System.out.printf(aviao +" ");
            }
    }
    sc.close();
}
}