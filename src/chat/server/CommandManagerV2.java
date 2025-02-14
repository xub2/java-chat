package chat.server;

import java.io.IOException;
import java.util.List;

public class CommandManagerV2 implements CommandManager{

    public static final String DELIMITER = "\\|";
    private final SessionManager sessionManager;

    public CommandManagerV2(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void execute(String totalMessage, Session session) throws IOException {

        if (totalMessage.startsWith("/join")) {
            String[] split = totalMessage.split(DELIMITER);
            String username = split[1];
            session.setUserName(username);
            sessionManager.sendAll(username + "님이 입장했습니다.");
        }

        else if (totalMessage.startsWith("/message")) {
            // 클라이언트 전체에게 문자 전송
            String[] split = totalMessage.split(DELIMITER);
            String message = split[1];
            // [name] message 형태로 보내보자
            sessionManager.sendAll("[" + session.getUserName() + "] " + message);
        }

        else if (totalMessage.startsWith("/change")) {
            // /change|이름 변경
            String[] split = totalMessage.split(DELIMITER);
            String changeName = split[1];
            sessionManager.sendAll(session.getUserName() + "님이 " + changeName + "로 이름을 변경했습니다.");
            session.setUserName(changeName);
        }

        else if (totalMessage.startsWith("/users")) {
            List<String> usernames = sessionManager.getAllUsername();
            StringBuilder sb = new StringBuilder();
            sb.append("전체 접속자 : ").append(usernames.size()).append("\n");
            for (String username : usernames) {
                sb.append(" - ").append(username).append("\n");
            }
            session.send(sb.toString());
        } else if (totalMessage.startsWith("/exit")) {
            throw new IOException("exit");
        }

        else {
        session.send("처리할 수 없는 명령어 입니다 : " + totalMessage);
        }

    }
}
