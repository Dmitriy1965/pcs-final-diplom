import com.fasterxml.jackson.databind.ObjectMapper;

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
                    final String searchQuery = in.readLine().toLowerCase();

                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        String json = objectMapper.writeValueAsString(engine.search(searchQuery));

                        if (json.equals("null")) {
                            out.println("Запрос не найден");
                        } else {
                            out.println(json);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}