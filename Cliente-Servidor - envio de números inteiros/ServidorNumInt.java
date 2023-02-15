import java.net.*;
import java.io.*;
import java.util.Random;

public class ServidorNumInt {
    public final static int PORT = 2222;

    public static void main (String args[]) throws Exception {
        ServerSocket server = new ServerSocket(PORT);
        Random gerador = new Random();
        int numero = gerador.nextInt();

        while(true){
            Socket cliente = server.accept();
            System.out.println("Cliente conectado");
            ObjectOutputStream out = new ObjectOutputStream(cliente.getOutputStream());
            ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
            
            // manda um numero aleatorio
            out.writeObject(numero);
            System.out.println("O servidor mandou o numero: " + numero);

            // recebe o numero enviado pelo cliente, incrementa 2 e envia para o cliente de volta
            numero =(int) entrada.readObject();
            System.out.println("O servidor recebeu o numero: " + numero);
            numero += 2;
            out.writeObject(numero);
            System.out.println("O servidor mandou o numero: " + numero);
            
            entrada.close();
            out.close();
            cliente.close();
        }
    }
}
