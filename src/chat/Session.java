package chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import static util.MyLogger.log;

public class Session implements Runnable {

    private final Socket socket;
    private String clientName;

    public Session(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            clientName = input.readUTF();
            log(clientName + "님이 접속하였습니다.");

            while (true) {
                // 클라이언트로부터 문자 받기
                String received = input.readUTF();
                log(received);

                // 클라이언트 종료시 서버도 종료
                if (received.equals("exit")) {
                    log(clientName + "님이 퇴장하였습니다.");
                    break;
                }
            }

            output.close();
            input.close();
            socket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
