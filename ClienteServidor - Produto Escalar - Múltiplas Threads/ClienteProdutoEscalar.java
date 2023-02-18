import java.net.*;
import java.io.*;
import java.util.Scanner;

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

     public  int getResultado_produto() {
        return resultado_produto;
    }

    public  synchronized void setResultado_produto(int resultado_produto) {
        this.resultado_produto = resultado_produto;
    }
 
 }

public class ClienteProdutoEscalar {
    protected final static int PORT = 2222;
    public static void main(String[] args) throws Exception{
        String hostname = args.length > 0 ? args[0] : "localhost";
        Socket server = new Socket(hostname,PORT);
        ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
        ObjectInputStream entrada = new ObjectInputStream(server.getInputStream());

        // Leitura e craicao dos vetores
        int[] vet1 = new int[20];
        int[] vet2 = new int[20];
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < 20; i++){
            vet1[i] = scanner.nextInt();
        }
        for (int i = 0; i < 20; i++){
            vet2[i] = scanner.nextInt();
        }

        Produto produto = new Produto(vet1,vet2);
        ////////////////////////////////

        // objeto produto enviado para o servidor para o calculo do produto escalar
        out.writeObject(produto);
        System.out.println("Vetores enviados !");

        // resultado do produto enviado pelo servidor
        produto = (Produto) entrada.readObject();
        System.out.println(produto.getResultado_produto());
        
        
    }
    
}
