
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Random;

public class Servidor extends Thread{

    public final static int PORT = 8500;
    public Random rand;

    Servidor(Random r){
        this.rand = r;
    }

    public void run(){
    }

    public static void main(String args[]){
        Random RAND = new Random();
        int n, PORTOUT;

        try{

            DatagramSocket SERVER  = new DatagramSocket(PORT);
            byte[] OUTPUT = new byte[256];
            byte[] INPUT = new byte[256];

            Arrays.fill(INPUT, (byte) 0);
            DatagramPacket RECEIVEDATA = new DatagramPacket(INPUT, INPUT.length);
            SERVER.receive(RECEIVEDATA);
            n = ByteBuffer.wrap(RECEIVEDATA.getData()).getInt();

            InetAddress ADRESSOUT = RECEIVEDATA.getAddress();
            PORTOUT = RECEIVEDATA.getPort();

            n = RAND.nextInt();
            Arrays.fill(OUTPUT, (byte) 0);
            OUTPUT = ByteBuffer.allocate(4).putInt(n).array();
            DatagramPacket SENDDATA = new DatagramPacket(OUTPUT, OUTPUT.length, ADRESSOUT, PORTOUT);
            SERVER.send(SENDDATA);
            System.out.println(n);

            Arrays.fill(INPUT, (byte) 0);
            RECEIVEDATA = new DatagramPacket(INPUT, INPUT.length);
            SERVER.receive(RECEIVEDATA);
            n = ByteBuffer.wrap(RECEIVEDATA.getData()).getInt();
            System.out.println(n);

            n += 2;
            Arrays.fill(OUTPUT, (byte) 0);
            OUTPUT = ByteBuffer.allocate(4).putInt(n).array();
            SENDDATA = new DatagramPacket(OUTPUT, OUTPUT.length, ADRESSOUT, PORTOUT);
            SERVER.send(SENDDATA);
            System.out.println(n);
                
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
