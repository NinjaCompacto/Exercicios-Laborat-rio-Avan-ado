import java.util.concurrent.*;
import java.util.Random;

class Conta {
    private int saldo;

    Conta (int saldoInicial){
        this.saldo = saldoInicial;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }
    public int getSaldo() {
        return this.saldo;
    }
}

class DepositoConta extends Thread{
    private Conta conta;
    private int valor;
    private Semaphore semaphore;
    private int tempo;

    DepositoConta( Semaphore semaphore,Conta conta, int valor, int tempo) {
        this.conta = conta;
        this.semaphore = semaphore;
        this.valor = valor;
        this.tempo = tempo;
    }

    public void run(){
        try{
            semaphore.acquire();
            int saldoAtual = conta.getSaldo() + valor;
            conta.setSaldo(saldoAtual);
            System.out.println("Saldo: "+ conta.getSaldo());
            Thread.sleep(tempo);
            semaphore.release();
        }catch(Exception e){}
    }
}

class RetiradaConta extends Thread{
    private Conta conta;
    private Semaphore semaphore;
    private int valor;
    private int tempo;

    RetiradaConta (Conta conta, Semaphore sem, int valor, int tempo) {
        this.conta = conta;
        this.semaphore = sem;
        this.valor = valor;
        this.tempo = tempo;
    }

    public void run(){
        try{
            semaphore.acquire();
            int saldoAtual = conta.getSaldo() -  valor;
            conta.setSaldo(saldoAtual);
            System.out.println("Saldo: " + conta.getSaldo());
            Thread.sleep(tempo);
            semaphore.release();
        }catch(Exception e){}
    }
}


public class ContaBancaria {
    public static void main (String[] args){
        Random valor = new Random();
        Random tempo = new Random();
        Semaphore semaphore = new Semaphore(1,true);
        Conta conta = new Conta(100);

        for(int i = 0; i < 4; i++){
            new DepositoConta(semaphore, conta, valor.nextInt(10000000),tempo.nextInt(500)).start();
            new RetiradaConta(conta, semaphore, valor.nextInt(10000000), tempo.nextInt(500)).start();
        }
    }
}
