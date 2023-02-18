import java.net.*;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


class Produto implements Serializable{
    private int[] vet1;
    private int[] vet2;
    private int resultado_produto = 0;
    
     Produto(int[] vet1, int[] vet2){
         this.vet1 = vet1;
         this.vet2 = vet2;
     }
 
     public int[] getVet1(){
         return vet1;
     }
     public int[] getVet2(){
         return vet2;
     }

     public synchronized  int getResultado_produto() {
        return resultado_produto;
    }

    public  synchronized void setResultado_produto(int resultado_produto) {
        this.resultado_produto = resultado_produto;
    }
 
 }

 class Calcular implements Runnable{

    private int index;
    private Produto produto;

    Calcular(int index, Produto produto){
        this.index = index;
        this.produto = produto;
    }

    public Produto getProduto() {
        return this.produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void run() {
        int resultado = this.getProduto().getResultado_produto();
        resultado += this.getProduto().getVet1()[this.getIndex()] * this.getProduto().getVet2()[this.getIndex()];
        this.getProduto().setResultado_produto(resultado);
       
        
    }

 }

 public class ServidorProdutoEscalar{
    protected final static int PORT = 2222;

    public static void main(String[] args) throws Exception{
        ServerSocket server = new ServerSocket(PORT);
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Produto produto;

        while(true){
            Socket cliente = server.accept();
            System.out.println("Cliente Connectado !");
            ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
            ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());

            // recebe o obejto produto mandado pelo cliente
            produto = (Produto) entrada.readObject();
            System.out.println("Vetores recebidos !");

            for(int i=0; i< 20; i++){
                executor.submit(new Calcular(i, produto));
            }

            try{
                executor.awaitTermination(1, TimeUnit.SECONDS);
            }catch(Exception e){}

            saida.writeObject(produto);
            System.out.println(produto.getResultado_produto());

            

        }
    }

}