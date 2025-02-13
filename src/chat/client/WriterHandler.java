package chat.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static util.MyLogger.log;

public class WriterHandler implements Runnable{

    private static final String DELIMITER = "|";

    private final DataOutputStream output;
    private final Client client;

    private boolean closed = false;

    public WriterHandler(DataOutputStream output, Client client) {
        this.output = output;
        this.client = client;
    }

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);

        try {
            // 연결은 이미 됐는데, 사용자 이름을 입력 받고 접속한다는 로직
            String userName = inputUserName(sc);
            output.writeUTF("/join" + DELIMITER + userName);

            while (true) {
                String toSend = sc.nextLine();
                if (toSend.isEmpty()) {
                    continue;
                } // 아무것도 입력하지 않고 엔터를 누르면 다시 입력 받도록 설계

                // /exit 라는 문자를 서버로 보내고 반복문 종료 -> finally 문으로 이동
                if (toSend.equals("/exit")) {
                    output.writeUTF(toSend);
                    break;
                }

                // "/" 로 시작하면 명령어, 나머지는 일반 메시지

                if (toSend.startsWith("/")) {
                    output.writeUTF(toSend);
                } else {
                    output.writeUTF("/message" + DELIMITER + toSend);
                }
            }

        } catch (IOException | NoSuchElementException e) {
            log(e);
        }finally {
            client.close();
        }


    }

    private static String inputUserName(Scanner sc) {
        System.out.print("이름을 입력하세요 : ");
        String userName;
        do {
            userName = sc.nextLine();
        } while (userName.isEmpty());
        return userName;
    }

    public synchronized void close() {
        if (closed) {
            return;
        }

        try {
            System.in.close(); // Scanner 입력 중지 (사용자 입력 닫기)
        } catch (IOException e) {
            log(e);
        }

        closed = true;
        log("writeHandler 종료");
    }
}
