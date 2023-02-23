
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Random;

public class Cliente {

    public final static int PORT = 8500;

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

    public static void main(String args[]){

        int n, PORTOUT, i;
        Random RAND = new Random();

        try{

            int [] array1 = new int[20];
            int [] array2 = new int[20];

            for(i = 0 ; i < 20 ; i++){
                array1[i] = RAND.nextInt(100);
                array2[i] = RAND.nextInt(100);
            }

            byte[] OUTPUT = new byte[4];
            byte[] INPUT = new byte[4];

            String SERVERNAME = args.length != 0 ? args[0] : "localhost";

            DatagramSocket CLIENT = new DatagramSocket();
            InetAddress ADRESSOUT = InetAddress.getByName(SERVERNAME);

            Arrays.fill(OUTPUT, (byte)0);
            OUTPUT = ByteBuffer.allocate(4).putInt(20).array();
            DatagramPacket SENDDATA = new DatagramPacket(OUTPUT, OUTPUT.length, ADRESSOUT, PORT);
            CLIENT.send(SENDDATA);

            for(i = 0 ; i < 20 ; i++){
                Arrays.fill(OUTPUT, (byte)0);
                OUTPUT = ByteBuffer.allocate(4).putInt(array1[i]).array();
                SENDDATA = new DatagramPacket(OUTPUT, OUTPUT.length, ADRESSOUT, PORT);
                CLIENT.send(SENDDATA);
            }

            for(i = 0 ; i < 20 ; i++){
                Arrays.fill(OUTPUT, (byte)0);
                OUTPUT = ByteBuffer.allocate(4).putInt(array2[i]).array();
                SENDDATA = new DatagramPacket(OUTPUT, OUTPUT.length, ADRESSOUT, PORT);
                CLIENT.send(SENDDATA);
            }

            System.out.println("Arrays enviados:");
            PRINTARRAYS(array1, array2);

            DatagramPacket RECEIVEDATA = new DatagramPacket(INPUT, INPUT.length);
            CLIENT.receive(RECEIVEDATA);
            n = ByteBuffer.wrap(RECEIVEDATA.getData()).getInt();
            System.out.println("Produto escalar: " + n);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
