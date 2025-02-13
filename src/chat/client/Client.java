package chat.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static util.SocketCloseUtil.closeAll;
import static util.MyLogger.log;

public class Client {

    private final String host;
    private final int port;

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    private ReadHandler readHandler;
    private WriterHandler writerHandler;
    private boolean closed = false;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws IOException {
        log("클라이언트 시작");
        socket = new Socket(host, port);
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());

        readHandler = new ReadHandler(input, this);
        writerHandler = new WriterHandler(output, this);

        Thread readThread = new Thread(readHandler, "readHandler");
        Thread writeHandler = new Thread(writerHandler, "writeHandler");
        readThread.start();
        writeHandler.start();
    }

    public void close() {
        if (closed) {
            return;
        }

        // 자원정리
        writerHandler.close();
        readHandler.close();
        closeAll(socket, input, output);
        closed = true;
        log("연결 종료 : " + socket);
    }
}
