import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


class Mat{
    public int matA[][];
    public int matB[][];
    public int matC[][];
    public int tamanho;

    Mat(int N){
        matA=new int[N][N];
        matB=new int[N][N];
        matC=new int[N][N];
        tamanho=N;
    }

    public void preencherMatrizes(){
        Random rand = new Random();
        for(int i = 0 ; i < tamanho ; i ++){
            for(int j = 0 ; j < tamanho ; j ++){
                matA[i][j] = rand.nextInt(100);
                matB[i][j] = rand.nextInt(100);
            }
        }
    }

}

class ImprimeMatriz implements Runnable {
    public Mat matriz;
    public int tamanho;

    ImprimeMatriz (Mat matriz,int tamanho){
        this.matriz = matriz;
        this.tamanho = tamanho;
    }


    public void run() {

        System.out.println("Matriz A:");
        for(int i = 0 ; i < tamanho ; i ++){
            for(int j = 0 ; j < tamanho ; j ++){
                if (j != tamanho-1){
                    System.out.print(matriz.matA[i][j] + " ");
                }
                else{
                    System.out.println(matriz.matA[i][j]);
                }
            }
        }

        System.out.println("");
        System.out.println("Matriz B:");
        for(int i = 0 ; i < tamanho ; i ++){
            for(int j = 0 ; j < tamanho ; j ++){
                if (j != tamanho-1){
                    System.out.print(matriz.matB[i][j] + " ");
                }
                else{
                    System.out.println(matriz.matB[i][j]);
                }
            }
        }

        System.out.println("");
        System.out.println("Matriz C:");
        for(int i = 0 ; i < tamanho ; i ++){
            for(int j = 0 ; j < tamanho ; j ++){
                if (j != tamanho-1){
                    System.out.print(matriz.matC[i][j] + " ");
                }
                else{
                    System.out.println(matriz.matC[i][j]);
                }
            }
        }

        
       }

}

class multiplication implements Runnable{

    private CyclicBarrier cyclicBarrier;
    public Mat matrizes;
    public int linhaA;
    public int colunaB;

    multiplication(CyclicBarrier cy,Mat matrizes, int linhaA, int colunaB){
        this.cyclicBarrier = cy;
        this.matrizes = matrizes;
        this.linhaA = linhaA;
        this.colunaB = colunaB;
    }

    public void run(){
        int result = 0;
        try{
            for (int i = 0; i  < matrizes.tamanho ; i++){
                result += matrizes.matA[linhaA][i] * matrizes.matB[i][colunaB];
            }
            matrizes.matC[linhaA][colunaB] = result;
            System.out.println("A Thread " + String.valueOf(linhaA) + " " + String.valueOf(colunaB) + " terminou !");
            cyclicBarrier.await();
        }catch(Exception e){}
          
    }

}

public class Matriz {
    
    public static void main(String[] args) {
        int N = 100;

        Mat matrizes = new Mat(N);
        matrizes.preencherMatrizes();

        ExecutorService executor = Executors.newFixedThreadPool(N*N);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(N*N,new ImprimeMatriz(matrizes,N));

    
        long tempoIncial = System.currentTimeMillis();
        for (int i = 0 ; i < matrizes.tamanho ; i++){
            for (int j = 0 ; j < matrizes.tamanho ; j++){
                executor.submit(new multiplication(cyclicBarrier,matrizes,i,j));   
            }
        }

        executor.shutdown();

        System.out.println("TODAS AS THREADS FORAM SUBMETIDAS !");

        try{
            executor.awaitTermination(1, TimeUnit.DAYS);
        }catch(Exception e){}

        long tempoFinal = System.currentTimeMillis();
        System.out.println("TEMPO DE EXECUÇÃO: " + (tempoFinal - tempoIncial)/1000d + " seconds");
        System.out.println(" O Programa terminou ! ");
        

    }   

}
