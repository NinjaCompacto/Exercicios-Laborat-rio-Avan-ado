import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.Random;

class Deposito {

    private int qtItens;
    private int capMax;

    Deposito(){
        this.setQtItens(0);
        this.setCapMax(0);
    }
    
    Deposito(int qtItens, int capMax){
        this.setQtItens(qtItens);
        this.setCapMax(capMax);
    }

    public synchronized void armazenar () throws InterruptedException{
        if (this.getQtItens()+1  < this.getCapMax()){
            this.setQtItens(this.getQtItens()+1);
        }
        else{
            System.out.println("Impossivel armazenar caixa ! O Deposito está cheio !");
        }
    }

    public synchronized void retirar () throws InterruptedException{
        if (this.getQtItens() > 0){
            this.setQtItens(this.getQtItens()-1);
        }
        else{
            System.out.println("Impossivel retirar ! O deposito estar vazio !");
        }
    }


    public void setQtItens (int valor){
        this.qtItens = valor;
    }
    public int getQtItens (){
        return this.qtItens;
    }

    public void setCapMax (int valor){
        this.capMax = valor;
    }
    public int getCapMax (){
        return this.capMax;
    }

}

public class DepCaixas {

    static class Produtor implements Runnable {
        private Deposito deposito;
        private int tempo;
        

        Produtor(Deposito dep,int temp ){
            this.setDeposito(dep);
            this.setTempo(temp);
        }

        public void run() {

            try{
                Thread.sleep(this.getTempo());
            }catch(Exception e){}

            try{
                this.getDeposito().armazenar();
            }catch(Exception e){}

            System.out.println("CAIXA ARMAZENADA ! Quantidade de caixas: " + this.getDeposito().getQtItens());

            try{
                Thread.sleep(this.getTempo());
            }catch(Exception e){}
            
        }

        public void setDeposito(Deposito dep){
            this.deposito = dep;
        }
        public Deposito getDeposito(){
            return this.deposito;
        }

        public void setTempo(int temp){
            this.tempo = temp;
        }
        public int getTempo(){
            return this.tempo;
        }

    }
   
    static class Consumidor implements Runnable {
        private int tempo;
        private Deposito deposito;

        Consumidor (Deposito dep, int temp){
            this.setDeposito(dep);
            this.setTempo(temp);
        }

        public void run(){

            try{
                Thread.sleep(this.getTempo());
            }catch(Exception e){}

                try{
                    this.getDeposito().retirar();
                }catch(Exception e){}

                System.out.println("CAIXA RETIRADA ! Quantidade de Caixas: " + this.getDeposito().getQtItens());
                try{
                    Thread.sleep(this.getTempo());
                }catch(Exception e){}
               
            
            
        }

        public void setDeposito(Deposito dep){
            this.deposito = dep;
        }
        public Deposito getDeposito(){
            return this.deposito;
        }

        public void setTempo(int temp){
            this.tempo = temp;
        }
        public int getTempo(){
            return this.tempo;
        }
        
    }

    public static void main(String[] args){

        ExecutorService executor = Executors.newFixedThreadPool(5);
        Deposito dep = new Deposito(10, 10000);
        Random rand = new Random();

        for (int i = 0 ;  i < 20 ; i++){
            int temp = rand.nextInt(200);
            executor.submit(new Consumidor(dep,temp));
            temp = rand.nextInt(200);
            executor.submit(new Produtor(dep,temp));
        }
        
        executor.shutdown();

        try{
            executor.awaitTermination(1, TimeUnit.DAYS);
        }catch(Exception e){}
        
       System.out.println("TODAS AS THREADS FORAM EXECUTADAS");
    }
}
