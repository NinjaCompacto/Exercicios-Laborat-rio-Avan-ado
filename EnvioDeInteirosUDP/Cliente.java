
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

public class Cliente {

    public final static int PORT = 8500;

    public static void main(String args[]){

        int n, PORTOUT;

        try{

            byte[] OUTPUT = new byte[256];
            byte[] INPUT = new byte[256];

            String SERVERNAME = args.length != 0 ? args[0] : "localhost";
            
            DatagramSocket CLIENT = new DatagramSocket();
            InetAddress ADRESSOUT = InetAddress.getByName(SERVERNAME);

            OUTPUT = ByteBuffer.allocate(4).putInt(0).array();
            DatagramPacket SENDPACKET = new DatagramPacket(OUTPUT, OUTPUT.length, ADRESSOUT, PORT);
            CLIENT.send(SENDPACKET);

            DatagramPacket RECEIVEPACKET = new DatagramPacket(INPUT, INPUT.length);
            CLIENT.receive(RECEIVEPACKET);
            n = ByteBuffer.wrap(RECEIVEPACKET.getData()).getInt();
            System.out.println(n);
            
            n++;
            OUTPUT = ByteBuffer.allocate(4).putInt(n).array();
            SENDPACKET = new DatagramPacket(OUTPUT, OUTPUT.length, ADRESSOUT, PORT);
            CLIENT.send(SENDPACKET);
            System.out.println(n);

            RECEIVEPACKET = new DatagramPacket(INPUT, INPUT.length);
            CLIENT.receive(RECEIVEPACKET);
            n = ByteBuffer.wrap(RECEIVEPACKET.getData()).getInt();
            System.out.println(n);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
