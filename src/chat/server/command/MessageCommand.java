package chat.server.command;

import chat.server.Session;
import chat.server.SessionManager;

public class MessageCommand implements Command {

    private final SessionManager sessionManager;

    public MessageCommand(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void execute(String[] args, Session session)  {
        String message = args[1];
        // [name] message 형태로 보내보자
        sessionManager.sendAll("[" + session.getUserName() + "] " + message);
    }
}
