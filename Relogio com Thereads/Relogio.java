class ContadorTempo{
    private int tick;

    ContadorTempo(int valor){
        this.setTick(valor);
    }
    ContadorTempo(){
        this.setTick(0);
    }


    public void setTick (int valor){
        this.tick  = valor;
    }
    public int getTick (){
        return tick;
    }
    public void nextTick (){
        this.setTick(this.getTick()+1);
    }
}

public class Relogio {

    private ContadorTempo contadorTempo;
    private int quantidadeTicks;
    private int valorInicial;
    

    Relogio(){
        this.setQuantidadeTicks(0);
        this.setValorInicial(0);
    }

    Relogio(int quantidadeTicks, int valor){
        ContadorTempo contTemp = new ContadorTempo(valor);
        this.setcontadorTempo(contTemp);
        this.setQuantidadeTicks(quantidadeTicks);
        this.setValorInicial(valor);
    }


    public  void setcontadorTempo(ContadorTempo obj){
        this.contadorTempo = obj;
    }
    public  ContadorTempo getcontadorTempo(){
        return this.contadorTempo;
    }
    public void setQuantidadeTicks(int valor){
        this.quantidadeTicks = valor;
    }
    public int getQuantidadeTicks(){
        return this.quantidadeTicks;
    }
    public void setValorInicial(int valor){
        this.valorInicial = valor;
    }
    public int getValorInicial(){
        return this.valorInicial;
    }
    
    // classe Thread
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
