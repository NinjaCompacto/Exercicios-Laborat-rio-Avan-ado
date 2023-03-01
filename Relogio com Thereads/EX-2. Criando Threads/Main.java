


public class Main {
    
    static class Runner extends Thread {
        //Constructor que recebe a classe Relogio
        Runner(Relogio obj){
            this.relogio = obj;
        }
        private Relogio relogio;

        public void run(){
            int cont = relogio.getQuantidadeTicks();
                while (cont >= 0){
                    System.out.println("Tick: " + relogio.getcontadorTempo().getTick() + " Thread: "+ Thread.currentThread().getName() );
                    
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){}
        
                    relogio.getcontadorTempo().nextTick();
                    cont--;
                }
        }
    }

    public static void main (String [] args){
        // alterar os parametros passados aqui para alterar a quatidade de ticks e o valor inicial de ticks
        Relogio relogio = new Relogio(10,10);
        Runner runner1 = new Runner(relogio);
        runner1.start();
    }
}
