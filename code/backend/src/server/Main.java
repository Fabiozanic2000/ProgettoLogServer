package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import db.UtentiDb;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws Exception {
        UtentiDb db = new UtentiDb("utentidb");
        db.checkCreateDb();


        HttpServer server = HttpServer.create(new InetSocketAddress(9000), 0); //crea il server in ascolto sull porta 9000
        System.out
                .println("Server listening on port 9000");

        //UtentiDb db = new UtentiDb("utentidb");

        server.createContext("/login", new Login(db)); //metodo per eseguire login con cookie se esiste utente nel db
        server.createContext("/signup", new Signup(db)); //registrazione utente
        server.createContext("/verifica", new Verifica(db)); //logged = true
        server.createContext("/home", new RispostaPost()); //Pagina di benvenuto
        server.createContext("/logout", new Logout()); //Pagina di benvenuto
        server.createContext("/elimina", new Elimina(db));
        server.createContext("/nero", new Pippo());
        server.setExecutor(null); //crea un esecutore di default
        server.start(); //fa partire il server
    }

    static class Pippo  implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            t.getResponseHeaders()
                    .set("Set-Cookie", "id=nero; HttpOnly; Expires=900");

            String response = "ciao";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody(); //chiude la comunicazione
            //os.write(response.getBytes());
            os.write(response
                    .getBytes(StandardCharsets.UTF_8));
            os.close();
        }
    }

    static class RispostaPost implements HttpHandler {
        Integer rCode = 0;

        @Override
        public void handle(HttpExchange t) throws IOException {
            System.out
                    .println("Sono nella classe RispostaPost");
            URI requestedUri = t.getRequestURI(); //prende l'uri passato
            System.out
                    .println(requestedUri);
            //String metodo = t.getRequestMethod(); //ottengo il metodo se è post o get
            String response = "";
            rCode = 0;
            if ("GET".equals(t.getRequestMethod())) {
                System.out
                        .println("Invocato metodo get, ERRORE");
                rCode = 404;
                response = errore();
            } else if ("POST".equals(t.getRequestMethod())) {
                UtentiDb db = new UtentiDb("utentidb");
                try {
                    if (requestedUri.compareTo(new URI("/verifica")) == 0) {
                        response = "/verifica";
                        rCode = 200;
                    } else if (requestedUri.compareTo(new URI("/home")) == 0) {
                        response = "/home";
                        rCode = 200;
                    } else {
                        System.out
                                .println("URI non trovato");
                        rCode = 404;
                        response = errore();
                    }
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }


            // serve per far comunicare client e server
            t.getResponseHeaders()
                    .add("Access-Control-Allow-Origin", "*");
            t.getResponseHeaders()
                    .add("Access-Control-Allow-Headers","origin, content-type, accept, authorization");
            t.getResponseHeaders()
                    .add("Access-Control-Allow-Credentials", "true");
            t.getResponseHeaders()
                    .add("Access-Control-Allow-Methods", "POST");

            t.sendResponseHeaders(rCode, response.length());
            OutputStream os = t.getResponseBody(); //chiude la comunicazione
            os.write(response.getBytes());
            os.close();
        }

        private String errore() {
            return switch (rCode) {
                case 401 -> "401 Login andato male";
                case 404 -> "404 MALE MALE";
                default -> "";
            };
        }
    }
}