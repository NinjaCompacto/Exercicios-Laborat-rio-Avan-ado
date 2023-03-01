
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Passageiro implements Runnable{
    private CyclicBarrier  barrier;
    int name;

    Passageiro(int name,CyclicBarrier barrier) {
        this.barrier = barrier;
        this.name = name;
    }

    public void run (){
        
        try{
            System.out.println("O passageiro " + Thread.currentThread().getName() + " entrou no carinho !" );
            barrier.await();
            Thread.currentThread().setName(String.valueOf(name));
        }catch(Exception e){}
    }

}

class Carrinho implements Runnable{
   
    public void run(){
        System.out.println("O carrinho esta cheio !");
        try{
            Thread.sleep(5000);
            System.out.println("O carrinho vazio !");
        }catch(Exception e){}
    }

}



public class MontanhaRussa {


    public static void main(String[] args){
        CyclicBarrier barrier = new CyclicBarrier(5,new Carrinho());
        ExecutorService  es =  Executors.newFixedThreadPool(20);
        

        for (int i = 0 ; i < 20 ; i ++){
            es.submit(new Passageiro(i,barrier));
        }

        es.shutdown();

        try{
            es.awaitTermination(1, TimeUnit.DAYS);
        }catch(Exception e){}

        System.out.println("A FILA ESTA VAZIA !");
    }

    
}