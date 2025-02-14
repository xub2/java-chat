package chat.server;

import chat.server.command.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommandManagerV4 implements CommandManager {

    private static final String DELIMITER = "\\|";
    private final SessionManager sessionManager;
    private final Map<String, Command> commands = new HashMap<>();
    private final DefaultCommand defaultCommand = new DefaultCommand();

    public CommandManagerV4(SessionManager sessionManager) {
        //커맨드 패턴 적용
        this.sessionManager = sessionManager;
        commands.put("/join", new JoinCommand(sessionManager));
        commands.put("/message", new MessageCommand(sessionManager));
        commands.put("/change", new ChangeCommand(sessionManager));
        commands.put("/users", new UsersCommand(sessionManager));
        commands.put("/exit", new ExitCommand());
    }


    @Override
    public void execute(String totalMessage, Session session) throws IOException {
        String[] args = totalMessage.split(DELIMITER);
        String key = args[0];

        //Null Object 패턴
        Command command = commands.getOrDefault(key, defaultCommand);
        command.execute(args, session);
    }
}
