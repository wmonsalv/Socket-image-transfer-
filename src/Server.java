import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {
        int portNumber = 12345;
        ServerSocket serverSocket = new ServerSocket(portNumber);
        System.out.println("Server started on port " + portNumber);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket);

            // get the input stream from the client socket
            InputStream inputStream = clientSocket.getInputStream();

            // read the image data from the input stream
            byte[] imageData = readImageDataFromStream(inputStream);

            // convert the image to black and white
            BufferedImage image = convertToBlackAndWhite(imageData);

            // save the image to a file
            String fileName = "received_image.jpg";
            saveImageToFile(image, fileName);

            // close the client socket
            clientSocket.close();
        }
    }

    private static byte[] readImageDataFromStream(InputStream inputStream) throws IOException {
        // read the image data from the input stream into a byte array
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        byte[] imageData = outputStream.toByteArray();
        return imageData;
    }

    private static BufferedImage convertToBlackAndWhite(byte[] imageData) throws IOException {
        // convert the image data to a BufferedImage
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
        BufferedImage image = ImageIO.read(inputStream);

        // convert the image to black and white
        int width = image.getWidth();
        int height = image.getHeight();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = new Color(image.getRGB(x, y));
                int grayscale = (int) (0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue());
                Color newColor = new Color(grayscale, grayscale, grayscale);
                image.setRGB(x, y, newColor.getRGB());
            }
        }

        return image;
    }

    private static void saveImageToFile(BufferedImage image, String fileName) throws IOException {
        // save the image to a file
        File outputFile = new File(fileName);
        ImageIO.write(image, "jpg", outputFile);
        System.out.println("Saved image to file: " + fileName);
    }
}
