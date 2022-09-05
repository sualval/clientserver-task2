import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Server {
    public static void main(String[] args) {

        try (final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open().bind(new InetSocketAddress(12345)); SocketChannel socketChannel = serverSocketChannel.accept()) {
            final ByteBuffer in = ByteBuffer.allocate(1 << 14);
            while (socketChannel.isConnected()) {
                int bytesBufFer = socketChannel.read(in);
                if (bytesBufFer == -1) {
                    break;

                }
                String text = new String(in.array(), 0, bytesBufFer, StandardCharsets.UTF_8);
                in.clear();
                socketChannel.write(ByteBuffer.wrap(("Result: " + text.replaceAll("\\s", "")).getBytes(StandardCharsets.UTF_8)));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
