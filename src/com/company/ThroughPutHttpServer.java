package com.company;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ThroughPutHttpServer {

    private static final String INPUT_FILE = "resources/war_and_peace.txt";
    private static final int NUMBER_OF_THREADS = 1;

    public static void main (String[] args) {
        String text = null;
        try {
            text = new String(Files.readAllBytes(Paths.get(INPUT_FILE)));
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
            ioException.printStackTrace();
        }

        if( text != null && !text.isEmpty()){
            try {
                startHttpServer(text);
            } catch (IOException ioException) {
                System.out.println("Exception starting Server");
                ioException.printStackTrace();
            }
        }

    }

    private static void startHttpServer (String text) throws IOException {
         HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
         server.createContext("/search", new WordCountHandler(text));
         Executor executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
         server.setExecutor(executor);
         server.start();
        System.out.println("Server Started");
    }

    private static class WordCountHandler implements HttpHandler {

        private final String text;

        private WordCountHandler (String text) {
            this.text = text;
        }


        @Override
        public void handle (HttpExchange httpExchange) throws IOException {
            final String query = httpExchange.getRequestURI().getQuery();
            final String[] keyValue = query.split("=");
            String action = keyValue[0];
            String word = keyValue[1];

            if(!action.equals("word")){
                httpExchange.sendResponseHeaders(400, 0);
            }

            long count = countWord(word);

            final byte[] responseBytes = Long.toString(count).getBytes();
            httpExchange.sendResponseHeaders(200, responseBytes.length);
            OutputStream outStream = httpExchange.getResponseBody();
            outStream.write(responseBytes);
            outStream.close();
        }

        private long countWord (String word) {
            int count =0;
            int index =0 ;

            while(index >=0){
                index = text.indexOf(word, index);

                if(index >=0){
                    count++;
                    index++;
                }
            }
            return count;
        }
    }

}
