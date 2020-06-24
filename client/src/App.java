import exchange.Client;
import exchange.LoginManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class App {
    private static Client client;
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Введите хост: ");
            String host = sc.nextLine().trim();
            System.out.print("Введите порт: ");
            String port = sc.nextLine().trim();
            String input;
            client = new Client(host, Integer.parseInt(port));
            if (client.connect()) {
                LoginManager.start(client);
                while (client.isConnected()) {
                    System.out.println("Введите команду: ");
                    input = client.getIn().readLine();
                    try {
                        execute(input);
                    }catch (StackOverflowError e){
                        System.out.println("Скрипт составоен неверно, пожалуйста уберите self-calls");
                    }
                }
                client.close();
            }
        } catch (Throwable e) {
            e.printStackTrace();
            System.err.println("Фатальная ошибка при работе клиента: " + e.getMessage());
        }
    }

    private static void execute(String input) throws IOException {
        if (input.split(" ")[0].contains("execute_script")){
            if (input.split(" ").length > 1){
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new FileReader(input.split(" ")[1]));
                    String line;

                    while ((line = br.readLine()) != null) {
                        if (line.split(" ")[0].contains("execute_script") && (line.split(" ").length > 1)){
                            execute(line);
                        }
                        client.get(line);
                        client.checkConnection(line);
                    }
                }catch (Exception e){
                    System.out.println("Файл-скрипт не найден\n");
                }
            }else System.out.println("Файл-скрипт не найден\n");
        }else {
            client.get(input);
            client.checkConnection(input);
        }
    }
}