package org.example;

import java.io.*;
import java.net.Socket;

public class ClientServer
{
    public static void main( String[] args ) throws Exception
    {
        Socket socket = new Socket("192.168.1.20",5678);
        OutputStream out = socket.getOutputStream();
        out.write("GET index.html\n\n".getBytes());

        InputStream in = socket.getInputStream();
        InputStreamReader inReader = new InputStreamReader(in);
        BufferedReader buffReader = new BufferedReader(inReader);

        while(true){
            String result = buffReader.readLine();
            if(result == null){
                break;
            }
            System.out.println(result);
        }
    }
}
