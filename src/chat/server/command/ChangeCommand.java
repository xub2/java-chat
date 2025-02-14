package chat.server.command;

import chat.server.Session;
import chat.server.SessionManager;


public class ChangeCommand implements Command{

    private final SessionManager sessionManager;

    public ChangeCommand(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void execute(String[] args, Session session) {
        // /change|이름 변경
        String changeName = args[1];
        sessionManager.sendAll(session.getUserName() + "님이 " + changeName + "로 이름을 변경했습니다.");
        session.setUserName(changeName);
    }
}
