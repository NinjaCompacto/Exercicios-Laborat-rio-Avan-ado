import java.net.*;
import java.io.*;
import java.util.Random;


public class ClienteNumInt {
    public final static int PORT = 2222;
    public static void main(String[] args) throws Exception {
        String hostname = args.length > 0 ? args[0] : "localhost";
        Socket server = new Socket(hostname, PORT);
        ObjectInputStream entrada = new ObjectInputStream(server.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());

        // recebe o numero e imprime
        int numero = (int) entrada.readObject();
        System.out.println("Numero recebido: "+ numero);

        // incrementa 1 vez e manda de volta para o servidor
        numero++;
        out.writeObject(numero);
        System.out.println("Numero enviado: "+ numero);

        numero = (int) entrada.readObject();
        System.out.println("Numero recebido: "+ numero);

        out.close();
        entrada.close();
        server.close();
    }
}
