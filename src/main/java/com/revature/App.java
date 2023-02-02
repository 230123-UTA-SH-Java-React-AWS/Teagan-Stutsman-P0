package com.revature;

import java.net.InetSocketAddress;

import com.revature.controller.EmployeeController;
import com.revature.controller.TicketController;
import com.sun.net.httpserver.HttpServer;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) throws Exception{
        System.out.println("Starting backend server...");

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/", new EmployeeController());
        server.createContext("/tickets", new TicketController());

        server.setExecutor(null);
        server.start();

    }
}
