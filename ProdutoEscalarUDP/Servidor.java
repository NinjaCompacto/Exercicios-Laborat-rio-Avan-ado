
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Random;

public class Servidor extends Thread{

    public final static int porta = 8500;
    public Random rand;


    public void run(){
        int n, PORTOUT, i;

        try{

            DatagramSocket SERVER  = new DatagramSocket(porta);
            

            byte[] OUTPUT = new byte[256];
            byte[] INPUT = new byte[256];

            int [] array1 = new int[20];
            int [] array2 = new int[20];
            
            Arrays.fill(INPUT, (byte) 0);
            DatagramPacket RECEIVEDATA = new DatagramPacket(INPUT, INPUT.length);
            SERVER.receive(RECEIVEDATA);
            n = ByteBuffer.wrap(RECEIVEDATA.getData()).getInt();
            InetAddress ADRESSOUT = RECEIVEDATA.getAddress();
            PORTOUT = RECEIVEDATA.getPort();


            for(i = 0 ; i < n ; i++){
                Arrays.fill(INPUT, (byte) 0);
                RECEIVEDATA = new DatagramPacket(INPUT, INPUT.length);
                SERVER.receive(RECEIVEDATA);
                array1[i] = ByteBuffer.wrap(RECEIVEDATA.getData()).getInt();
            }

            for(i = 0 ; i < n ; i++){
                Arrays.fill(INPUT, (byte) 0);
                RECEIVEDATA = new DatagramPacket(INPUT, INPUT.length);
                SERVER.receive(RECEIVEDATA);
                array2[i] = ByteBuffer.wrap(RECEIVEDATA.getData()).getInt();
            }
            System.out.println("Arrays recebidos:");
            PRINTARRAYS(array1, array2);

            n = prodEscalar(array1, array2);
            Arrays.fill(OUTPUT, (byte) 0);
            OUTPUT = ByteBuffer.allocate(4).putInt(n).array();
            DatagramPacket SENDDATA = new DatagramPacket(OUTPUT, OUTPUT.length, ADRESSOUT, PORTOUT);
            SERVER.send(SENDDATA);
            System.out.println("Produto escalar: " + n);
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public static void PRINTARRAYS(int[] array1, int[] array2){
        int i;
        for(i = 0 ; i < 19 ; i++){
            System.out.print(array1[i] + " ,");
        }
        System.out.print(array1[19]);
        System.out.println();
        for(i = 0 ; i < 19 ; i++){
            System.out.print(array2[i] + " ,");
        }
        System.out.print(array2[19]);
        System.out.println();
    }


    public static int prodEscalar(int[] array1 , int[] array2){
        int i, n = 0;
        for(i = 0 ; i < 20 ; i++){
            n += (array1[i]*array2[i]) ;
        }
        return n;
    }

    public static void main(String args[]){

        new Servidor().start();

    }
}
