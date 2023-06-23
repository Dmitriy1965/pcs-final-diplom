

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        // try (ServerSocket serverSocket = new ServerSocket(7777);) { // порт можете выбрать любой в доступном диапазоне 0-65536. Но чтобы не нарваться на уже занятый - рекомендуем использовать около 8080
        try (Socket clientSocket = new Socket("localhost", 8989);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {

            String answer = in.readLine();
            System.out.println(answer);
            Scanner aa = new Scanner(System.in);
            //         String psw = aa.nextLine();

            String psw = "бизнес";
//            System.out.println(psw);

            out.println(psw);
            answer = in.readLine();

            System.out.println(answer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}













