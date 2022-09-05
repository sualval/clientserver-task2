import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {

        try (final SocketChannel socketChannel = SocketChannel.open(); Scanner scanner = new Scanner(System.in)) {

            socketChannel.connect(new InetSocketAddress("localhost", 12345));
            final ByteBuffer in = ByteBuffer.allocate(1 << 14);
            String text;
            while (true) {
                System.out.println("Enter text : ");
                text = scanner.nextLine();
                if ("end".equals(text)) break;
                socketChannel.write(ByteBuffer.wrap(text.getBytes(StandardCharsets.UTF_8)));
                int bytesCount = socketChannel.read(in);
                System.out.println(new String(in.array(), 0, bytesCount, StandardCharsets.UTF_8).trim());
                in.clear();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
