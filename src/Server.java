import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[]args) throws IOException {

        int portNumber = 12345;
        ServerSocket serverSocket = new ServerSocket(portNumber);
        System.out.println("Server started on port " + portNumber);

        while (true) {
            // we're using a while loop to continuously listen for incoming client connections.
            // When a client connects, we're accepting the connection using the accept() method of the ServerSocket class.
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket);

            //Once we've accepted the connection, we're getting the input stream for the connection using the getInputStream() method of the Socket class.
            // We're then wrapping the input stream in a BufferedInputStream to improve performance when reading data.
            InputStream inputStream = clientSocket.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);


            //Next, we're using a ByteArrayOutputStream to read the image data from the client.
            // We're reading the data into a byte array using the read() method of the input stream,
            // and writing it to the ByteArrayOutputStream using the write() method.
            // We're repeating this process until we've read all of the data from the client.
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }

            // Convert bytes to image and save to file
            //Finally, we're converting the bytes to an image and saving it to a file using a FileOutputStream.
            // We're then closing the client connection using the close() method of the Socket class.
            byte[] imageData = byteArrayOutputStream.toByteArray();
            String fileName = "received_image.jpg";
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            fileOutputStream.write(imageData);
            fileOutputStream.close();

            System.out.println("Received and saved image from client");
            clientSocket.close();
        }

    }
}
