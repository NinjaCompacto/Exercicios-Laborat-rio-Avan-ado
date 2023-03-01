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