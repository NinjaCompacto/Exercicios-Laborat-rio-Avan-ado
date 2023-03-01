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
    
    
}
