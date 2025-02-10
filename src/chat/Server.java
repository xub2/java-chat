package chat;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import static util.MyLogger.log;

public class Server {

    private static final int PORT = 13423;
    private static HashMap<Socket, String> clientInfo = new HashMap<>();

    public static void main(String[] args) throws IOException {

        log("채팅 서버 시작");
        ServerSocket serverSocket = new ServerSocket(PORT);
        log("Listening to " + PORT + " Port ...");
        log("클라이언트의 연결을 기다리고 있습니다...");

        while (true) {
            Socket socket = serverSocket.accept(); // 연결은 OS가 해주되, 자바로 불러오는 것을 accept 메서드로 소켓을 설정
            log("클라이언트 연결 됨 - 클라이언트 정보 : " + socket);

            DataInputStream input = new DataInputStream(socket.getInputStream());
            String clientName = input.readUTF();
            clientInfo.put(socket, clientName);

            Session session = new Session(socket);
            Thread thread = new Thread(session);
            thread.start();
        }

    }

}
