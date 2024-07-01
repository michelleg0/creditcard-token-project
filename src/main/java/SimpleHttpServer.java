package main.java;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import main.java.handler.IndexHandler;
import main.java.handler.SubmitHandler;

public class SimpleHttpServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new IndexHandler());
        server.createContext("/submit", new SubmitHandler());

        server.setExecutor(null);
        server.start();

        System.out.println("Server started http://localhost:8080/");
    }
}
