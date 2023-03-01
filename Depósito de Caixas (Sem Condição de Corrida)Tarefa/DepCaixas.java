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
        private final int MAX_PRODUCAO = 5;

        Produtor(Deposito dep,int temp ){
            this.setDeposito(dep);
            this.setTempo(temp);
        }

        public void run() {
            int producoes = 0;

            try{
                Thread.sleep(this.getTempo());
            }catch(InterruptedException e){}

            while (producoes <= MAX_PRODUCAO){
                try{
                    this.getDeposito().armazenar();
                }catch(InterruptedException e){}

                System.out.println("CAIXA ARMAZENADA ! Quantidade de caixas: " + this.getDeposito().getQtItens());
                producoes++;
                try{
                    Thread.sleep(this.getTempo());
                }catch(InterruptedException e){}
            }

            
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
   
    static class Consumidor extends Thread {
        private int tempo;
        private Deposito deposito;
        private final int MAX_CONSUMOS = 5;

        Consumidor (Deposito dep, int temp){
            this.setDeposito(dep);
            this.setTempo(temp);
        }

        public void run(){
            int contConsumos = 0;

            try{
                Thread.sleep(this.getTempo());
            }catch(InterruptedException e){}
            contConsumos++;

            while (contConsumos <= MAX_CONSUMOS){
                try{
                    this.getDeposito().retirar();
                }catch(InterruptedException e){}

                System.out.println("CAIXA RETIRADA ! Quantidade de Caixas: " + this.getDeposito().getQtItens());
                try{
                    Thread.sleep(this.getTempo());
                }catch(InterruptedException e){}
                contConsumos++;
            }
            
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
        Deposito dep = new Deposito(2, 100);
        Consumidor cons = new Consumidor(dep,2000);
        Thread produ = new Thread(new Produtor(dep,1000));
        
        produ.start();
        cons.start();
    }
}
