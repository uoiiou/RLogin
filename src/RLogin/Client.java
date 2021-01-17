package RLogin;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args){
        try {
            Socket socket = new Socket("127.0.0.1", 10001);
            System.out.println("Client started\n");

            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            dataOutputStream.writeUTF("\0");
            dataOutputStream.writeUTF("uoi" + '\0');
            dataOutputStream.writeUTF("uoiroot" + '\0');
            dataOutputStream.writeUTF("vt100/9600" + '\0');

            System.out.println("From server:\n\tNull byte - " + dataInputStream.readUTF());
            System.out.println(dataInputStream.readUTF());

            Thread.sleep(5000);
            try {
                dataOutputStream.writeUTF("uoi1234567890");
                System.out.println("\t Client wrote - uoi1234567890");

                System.out.print("\n" + dataInputStream.readUTF() + "\n");

                System.out.println("\nEcho bytes:");
                int i = 0;
                while (i++ < 20)
                {
                    dataOutputStream.writeByte(i);
                    System.out.println("\t" + dataInputStream.readByte());
                }
            }catch (IOException e){
                System.out.println("(Client) - " + e);
            }

            dataOutputStream.close();
            dataInputStream.close();
            socket.close();
            System.out.println("\nClient stoped");
        } catch (IOException | InterruptedException e){
            System.out.println("(Client) - " + e);
        }
    }
}
