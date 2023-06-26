

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        try (Socket clientSocket = new Socket("localhost", 8989);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {

            String answer = in.readLine();
            System.out.println(answer);

//            Scanner aa = new Scanner(System.in);
            String psw = "бизнес";

            out.println(psw);
            answer = in.readLine();


          System.out.println(answer);
//              System.out.println(answer.toString());


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
