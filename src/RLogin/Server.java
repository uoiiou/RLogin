package RLogin;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static Socket socket;

    public static void main(String[] args){
        try {
            ServerSocket serverSocket = new ServerSocket(10001);
            System.out.println("Server started\n");

            socket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            System.out.println("From client:\n\tNull byte - " + dataInputStream.readUTF());
            System.out.println("\tUsers name on client side - " + dataInputStream.readUTF());
            System.out.println("\tUsers name on server side - " + dataInputStream.readUTF());
            System.out.println("\tType/speed terminal - " + dataInputStream.readUTF());

            dataOutputStream.writeUTF("\0");
            dataOutputStream.writeUTF("\nPassword: ");

            new ReadPassword().start();
            Thread.sleep(3000);

            if (ReadPassword.getGoon())
            {
                System.out.println("\nPassword from client:\n\t" + ReadPassword.getPassword());
                ReadPassword.stopThread();

                dataOutputStream.writeUTF("Terminal window size: ");

                try{
                    while(!socket.isClosed())
                    {
                        dataOutputStream.writeByte(dataInputStream.readByte());
                    }
                } catch (IOException e) {
                    System.out.println("(Server) - " + e);
                }
            }
            else
            {
                System.out.println("\nClient didn't enter a password within 60 seconds. Server closed");
                ReadPassword.stopThread();
            }

            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
            serverSocket.close();
            System.out.println("\nServer stoped");
        } catch (IOException | InterruptedException e) {
            System.out.println("(Server) - " + e);
        }
    }

    public static Socket getSocket(){
        return socket;
    }
}
