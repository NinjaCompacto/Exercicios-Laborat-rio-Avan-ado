/* Este algoritmo usa o exemplo de CountDowLatch para realizar o gerenciamento de uma roda gigante
 * a qual so começa a funcionar quando atinge sua capacidade maxima
 */


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class FilaPessoas {

    public int quantidadePessoas;

    FilaPessoas (int quantidadePessoas) {
        this.quantidadePessoas = quantidadePessoas;
    }
}

class GerenciamentoRodaGigante implements Runnable{

    public FilaPessoas fila;
    private CountDownLatch latch;
    

    GerenciamentoRodaGigante (FilaPessoas fila, CountDownLatch latch){
        this.fila = fila;
        this.latch = latch;
    }

    public void run() {
        System.out.println("Pessoa entrando na RodaGigante...");
        try {
            Thread.sleep(1000);
        }catch(Exception e){}
        fila.quantidadePessoas--;
        System.out.println("FALTAVAM:" + latch.getCount()); 
        latch.countDown();
        System.out.println("AGORA FALTAM: " +latch.getCount()); 
        System.out.println("+1 Pessoa dentro da roda Gigante");

    }

}


public class RodaGigante {
    public static void main (String [] args){
        //Precisa de 4 pessoas para a roda gigante começar !
        final int CAPACIDADE_MAX_RODA_GIGANTE = 4;
        final int NUMERO_DE_PESSOAS_A_ENTRAR_POR_VEZ = 2;
        final int QUANTIDADE_DE_PESSOAS_NA_FILA = 4;


        CountDownLatch latch = new CountDownLatch(CAPACIDADE_MAX_RODA_GIGANTE);
        ExecutorService executor = Executors.newFixedThreadPool(NUMERO_DE_PESSOAS_A_ENTRAR_POR_VEZ);
        FilaPessoas fila = new FilaPessoas(QUANTIDADE_DE_PESSOAS_NA_FILA);

        System.out.println("ENTRANDO PESSOAS NA FILA PARA RODA GIGANTE !");
        for (int i = 0; i < QUANTIDADE_DE_PESSOAS_NA_FILA; i++) {
            executor.submit(new GerenciamentoRodaGigante(fila,latch));
        }
        executor.shutdown();

        try{
            latch.await();
        }catch (Exception e){}

        System.out.println("RODA GIGANTE GIRANDO !!!");
        
    }
}
