import java.util.concurrent.locks.*;
import java.util.concurrent.locks.Lock;
import java.util.Random;

public class Cigarro {
    // Cada lock representa um ingrediente. 
    // Para que o fumante possa fumar ele precisa de 2 ingredints restantes para completar
    static Lock tabacoFornecido = new ReentrantLock();
    static Lock papelFornecido = new ReentrantLock();
    static Lock fosforoFornecido = new ReentrantLock();

    // deixa todos os ingredients indisponiveis.
    public  static void acquire(Lock tabaco, Lock papel, Lock fosforo){
            while(true){
                boolean got1 = false;
                boolean got2 = false;
                boolean got3 = false;
                
                try{
                    got1 = tabaco.tryLock();
                    got2 = papel.tryLock();
                    got3 = fosforo.tryLock();
                }
                finally{
                    if(got1 && got2 && got3) return;
                    else if(got1) tabaco.unlock();
                    else if(got2) papel.unlock();
                    else if(got3) fosforo.unlock();
                }
            }
    }
    // Libera aleatoriamente 2 ingredientes por vez
    public synchronized static void release(Lock tabaco, Lock papel, Lock fosforo){
        Random ran = new Random();
        int num = ran.nextInt(2);

        switch (num){
            case 0:
            papel.unlock();
            fosforo.unlock();

            case 1:
            tabaco.unlock();
            fosforo.unlock();

            case 2:
            tabaco.unlock();
            papel.unlock();
        }
    }

    static class Fumante extends Thread{
        // representacao de ingredientes usando numeros:
        //tabaco:0, papel:1, fosforo:2
        public int ingrediente;
        Fumante (int num){
            ingrediente = num;
        }

       public void run(){
            switch(ingrediente){
                case 0:
                try{
                    acquire(tabacoFornecido,papelFornecido, fosforoFornecido);
                    System.out.println("Ingredientes fornecidos: Papel e fosforo");
                    System.out.println("FUMANDO !");
                    Thread.sleep(1000);
                    release(tabacoFornecido, papelFornecido, fosforoFornecido);
                }catch(Exception e){
                    System.out.println("Parou de Fumar !");
                }

                case 1:
                try{
                    acquire(tabacoFornecido,papelFornecido, fosforoFornecido);
                    System.out.println("Ingredientes fornecidos: tabaco e fosforo");
                    System.out.println("FUMANDO !");
                    Thread.sleep(1000);
                    release(tabacoFornecido, papelFornecido, fosforoFornecido);
                }catch(Exception e){
                    System.out.println("Parou de Fumar !");
                }

                case 2:
                try{
                    acquire(tabacoFornecido,papelFornecido, fosforoFornecido);
                    System.out.println("Ingredientes fornecidos: tabaco e papel");
                    System.out.println("FUMANDO !");
                    Thread.sleep(1000);
                    release(tabacoFornecido, papelFornecido, fosforoFornecido);
                }catch(Exception e){
                    System.out.println("Parou de Fumar !");
                }

            }
       } 
    }

    public static void main(String[] args){
        
        Fumante fumante1 = new Fumante(0);
        Fumante fumante2 = new Fumante(0);
        Fumante fumante3 = new Fumante(0);
        fumante1.start();
        fumante2.start();
        fumante3.start();

       
        System.out.println("FINISHED");
    }

}
