package apartment.in.houses.framework.spring.web.server;

public class Test {

    public static void main(String[] args) {

        Server server = new Server();

        try {
            server.startServer();
            System.out.println("Сервер успешно запущен на порту ");
        } catch (Exception e) {
            System.err.println("Ошибка при запуске сервера: " + e.getMessage());
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                server.stopServer();
                System.out.println("Сервер успешно остановлен.");
            } catch (Exception e) {
                System.err.println("Ошибка при остановке сервера: " + e.getMessage());
            }
        }));
    }
}
