package RLogin;

import java.io.DataInputStream;
import java.io.IOException;

public class ReadPassword extends Thread {
    private static boolean goon = false;
    private static String password = "";

    public void run(){
        try {
            DataInputStream dataInputStream = new DataInputStream(Server.getSocket().getInputStream());

            password = dataInputStream.readUTF();
            goon = true;
        } catch (IOException e) {
            System.out.print("");
        }
    }

    public static boolean getGoon(){
        return goon;
    }

    public static String getPassword(){
        return password;
    }

    public static void stopThread(){
        Thread.currentThread().interrupt();
    }
}
