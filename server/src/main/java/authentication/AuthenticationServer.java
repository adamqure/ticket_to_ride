package authentication;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import handler.*;

public class AuthenticationServer {
    private static final AuthenticationServer instance = new AuthenticationServer();

    //private constructor to avoid client applications to use constructor
    private AuthenticationServer(){}

    public static AuthenticationServer getInstance(){
        return instance;
    }

    private static final int MAX_WAITING_CONNECTIONS = 12;

    /**
     * Starts the HTTP AuthenticationServer on port 8080. Is called in the Server class along with the SocketServer.
     *
     * @param portNumber Should be set to 8080.
     */
    public void run(String portNumber) {

        /* Server Initialization */

        System.out.println("Initializing HTTP Server" + "\n");

        HttpServer server;
        try {
            server = HttpServer.create(new InetSocketAddress(Integer.parseInt(portNumber)), MAX_WAITING_CONNECTIONS);
        }

        catch(IOException e) {
            e.printStackTrace();
            return;
        }

        server.setExecutor(null);

        /* Context Creation */

        System.out.println("Creating Contexts");

        server.createContext("/authenticate/register", new RegisterHandler());

        server.createContext("/authenticate/login", new LoginHandler());

        server.start();
    }
}

