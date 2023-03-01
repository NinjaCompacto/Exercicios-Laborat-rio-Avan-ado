import java.util.Random;
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
                matA[i][j] = rand.nextInt(10);
                matB[i][j] = rand.nextInt(10);
            }
        }
    }

    public void imprimirMatrizes(int escolha){

        if (escolha == 1){
            for(int i = 0 ; i < tamanho ; i ++){
                for(int j = 0 ; j < tamanho ; j ++){
                    if (j != tamanho-1){
                        System.out.print(matA[i][j] + " ");
                    }
                    else{
                        System.out.println(matA[i][j]);
                    }
                }
            }
        }
        else if (escolha == 2){
            for(int i = 0 ; i < tamanho ; i ++){
                for(int j = 0 ; j < tamanho ; j ++){
                    if (j != tamanho-1){
                        System.out.print(matB[i][j] + " ");
                    }
                    else{
                        System.out.println(matB[i][j]);
                    }
                }
            }
        }
        else{
            for(int i = 0 ; i < tamanho ; i ++){
                for(int j = 0 ; j < tamanho ; j ++){
                    if (j != tamanho-1){
                        System.out.print(matC[i][j] + " ");
                    }
                    else{
                        System.out.println(matC[i][j]);
                    }
                }
            }
        }

    }
}

class multiplication implements Runnable{

    public Mat matrizes;
    public int linhaA;
    public int colunaB;

    multiplication(Mat matrizes, int linhaA, int colunaB){
        this.matrizes = matrizes;
        this.linhaA = linhaA;
        this.colunaB = colunaB;
    }

    public void run(){
          int result = 0;
          for (int i = 0; i  < matrizes.tamanho ; i++){
            result += matrizes.matA[linhaA][i] * matrizes.matB[i][colunaB];
          }
          matrizes.matC[linhaA][colunaB] = result;
    }

}

public class Matriz {
    
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        Mat matrizes = new Mat(2);
        matrizes.preencherMatrizes();


        System.out.println("MATRIZ A:");
        matrizes.imprimirMatrizes(1);
        System.out.println(" ");
        System.out.println("MATRIZ B:");
        matrizes.imprimirMatrizes(2);

        for (int i = 0 ; i < matrizes.tamanho ; i++){
            for (int j = 0 ; j < matrizes.tamanho ; j++){
                executor.submit(new multiplication(matrizes,i,j));   
            }
        }

        executor.shutdown();

        System.out.println("TODAS AS THREADS FORAM SUBMETIDAS !");
        try{
            executor.awaitTermination(1, TimeUnit.DAYS);
        }catch(Exception e){}

        System.out.println(" ");
        System.out.println("MATRIZ C:");
        matrizes.imprimirMatrizes(3);

        

    }   

}
