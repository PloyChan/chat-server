package org.example;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) throws Exception{
        ServerSocket server = new ServerSocket(5678);
        while(true){
            final Socket client = server.accept();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        handleRequest(client);
                    }catch (Exception e) {
                        e.getStackTrace();
                    }
                }
            }).start();
        }

    }
    public static void handleRequest(Socket client) throws Exception{
        System.out.println("accept client "+client.getInetAddress());
        InputStream in = client.getInputStream();
        InputStreamReader reader = new InputStreamReader(in);
        BufferedReader buff = new BufferedReader(reader);
        List<String> lists = new ArrayList<>();

        while(true){
            String result = buff.readLine();
            lists.add(result);
            if(result.equals("")){
                break;
            }
        }

        OutputStream out = client.getOutputStream();
        String[] getCommand = lists.get(0).split(" ");

        if(getCommand[0].startsWith("GET")){
            out.write("HTTP/1.1 200 OK\n".getBytes());
            out.write("content-type: text/html; charset=UTF-8\n\n".getBytes());

            FileInputStream file = new FileInputStream("/home/ploy/IdeaProjects/chatserver/src/"+getCommand[1]);
            while(true){
                int result = file.read();
                if(result == -1){
                    file.close();
                    break;
                }
                out.write((char)result);
            }
            out.flush();
        }
        client.close();

    }
}
