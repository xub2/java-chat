package util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static util.MyLogger.log;

public class SocketCloseUtil {

    public static void closeAll(Socket socket, InputStream input, OutputStream output) {
        close(input);
        close(output);
        close(socket);
    }

    private static void close(InputStream input) {
        if (input != null) {
            try {
                input.close();
            } catch (IOException e) {
                log(e.getMessage());
            }
        }
    }

    private static void close(OutputStream output) {
        try {
            output.close();
        } catch (IOException e) {
            log(e.getMessage());
        }

    }

    private static void close(Socket socket) {
        try {
            socket.close();
        } catch (IOException e) {
            log(e.getMessage());
        }
    }


}
