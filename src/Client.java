import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

    public static void main(String[]args) throws IOException {

        String serverAddress = "localhost"; // Replace with your server address
        int serverPort = 12345; // Replace with your server port

        try {
            // Read image data from file
            String fileName = "src/owl_image.jpg";
            System.out.println(fileName);
            File imageFile = new File(fileName);
            BufferedImage image = ImageIO.read(imageFile);

            // Connect to server
            Socket socket = new Socket(serverAddress, serverPort);
            OutputStream outputStream = socket.getOutputStream();

            // Write image data to output stream
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", byteArrayOutputStream);
            byte[] imageData = byteArrayOutputStream.toByteArray();
            outputStream.write(imageData);

            // Close connections
            outputStream.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


