package chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import static util.MyLogger.log;

public class Client {

    private static final int PORT = 13423;
    static Scanner sc = new Scanner(System.in);
    private static String name;

    public static void main(String[] args) throws IOException {
        log("클라이언트 시작");

        Socket socket = new Socket("localhost", PORT);
        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());

        System.out.print("사용하실 이름을 입력해주세요 : ");
        name = sc.nextLine();

        log("채팅 서버 연결 : " + socket);

        while (true) {
            System.out.print("[" + name + "] : "); // 닉넴으로 수정해야함 -> 닉넴 변수가 필요
            String toSend = sc.nextLine();
            // 서버에 문자 보내기
            output.writeUTF(toSend);

            if (toSend.equals("exit")) {
                break;
            }
        }

        // 자원 정리 로직 추후 구현
        input.close();
        output.close();
        socket.close();
    }

    public void close() {
    }
}
