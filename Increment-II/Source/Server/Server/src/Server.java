import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
public class Server {
    public static final int BUFFER_SIZE = 100;
    public static void main(String []args) throws Exception
    {
        ServerSocket serverSocket = new ServerSocket(3332);
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Accepted connection : " + socket);
            File file = new File("/Users/rohithkumarn/Downloads/mobilenet_v1_224_android_quant_2017_11_08/mobilenet_quant_v1_224.tflite");
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            oos.writeObject(file.getName());

            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[Server.BUFFER_SIZE];
            Integer bytesRead = 0;

            while ((bytesRead = fis.read(buffer)) > 0) {
                oos.writeObject(bytesRead);
                oos.writeObject(Arrays.copyOf(buffer, buffer.length));
            }

            oos.close();
            ois.close();
            System.out.println("File transfer complete");
        }
    }
}
