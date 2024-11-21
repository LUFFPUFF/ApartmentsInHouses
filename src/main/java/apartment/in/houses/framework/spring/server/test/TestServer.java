package apartment.in.houses.framework.spring.server.test;

import apartment.in.houses.framework.spring.server.Dispatcher;
import apartment.in.houses.framework.spring.server.Server;

public class TestServer {
    public static void main(String[] args) {

        Dispatcher dispatcher = new Dispatcher();

        dispatcher.registerController(new TestController());

        Server server = new Server(8080, dispatcher);

        server.start();
    }
}
