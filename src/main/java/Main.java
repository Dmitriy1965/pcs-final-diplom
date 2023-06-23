import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws Exception {

        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));

        try (ServerSocket serverSocket = new ServerSocket(8989);) {
            while (true) { // в цикле(!) принимаем подключения

                try (Socket clientSocket = serverSocket.accept(); // ждем подключения
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                ) {
                    out.println("Ваш запрос");
                 //   System.out.println("New connection accepted");
                    final String searchQuery = in.readLine();
                   //   System.out.println(searchQuery);
                    String searchResult =  engine.search(searchQuery);
                    //  System.out.println(searchResult);

                   out.println(String.format(searchResult));
                  //  System.out.println(searchResult);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}