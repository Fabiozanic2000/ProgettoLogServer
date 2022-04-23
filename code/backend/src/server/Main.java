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
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws Exception {
        UtentiDb db = new UtentiDb("utentidb");
        db.checkCreateDb();
        //Connection c = db.connect();

        HttpServer server = HttpServer.create(new InetSocketAddress(9000), 0); //crea il server in ascolto sull porta 9000
        System.out.println("Server listening on port 9000");

        server.createContext("/login", new RispostaPost()); //metodo per eseguire login con cookie se esiste utente nel db
        server.createContext("/signup", new RispostaPost()); //registrazione utente
        server.createContext("/verifica", new RispostaPost()); //logged = true
        server.createContext("/home", new RispostaPost()); //Pagina di benvenuto
        server.setExecutor(null); //crea un esecutore di default
        server.start(); //fa partire il server
    }


    static class RispostaPost implements HttpHandler {

        @Override
        public void handle(HttpExchange t) throws IOException {
            System.out.println("Sono nella classe RispostaPost");
            URI requestedUri = t.getRequestURI(); //prende l'uri passato
            System.out.println(requestedUri);
            //String metodo = t.getRequestMethod(); //ottengo il metodo se è post o get
            String response = "";
            int rCode = 0;
            if ("GET".equals(t.getRequestMethod())) {
                System.out.println("Invocato metodo get, ERRORE");
                response = errore();
                rCode = 404;
            } else if ("POST".equals(t.getRequestMethod())) {
                //URI myUri = null;
                try {
                    //myUri = new URI("/login");

                    if (requestedUri.compareTo(new URI("/login")) == 0) {
                        response = "/login";
                        rCode = 200;
                    } else if (requestedUri.compareTo(new URI("/signup")) == 0) {
                        System.out.println("Mamma");
                        UtentiDb db = new UtentiDb("utentidb");
                        String nome = "Riccardo";
                        String cognome = "Forni";
                        String email = "rickyforni2@gmail.com";
                        String password = "123456789";
                        String professione = "tecnico";
                        if (db.signup(nome, cognome, email,password, professione))
                            System.out.println("Utente inserito correttamente");
                        else
                            System.out.println("Utente già presente nel db");

                        response = "/signup";
                        rCode = 200;
                    } else if (requestedUri.compareTo(new URI("/verifica")) == 0) {
                        response = "/verifica";
                        rCode = 200;
                    } else if (requestedUri.compareTo(new URI("/home")) == 0) {
                        response = "/home";
                        rCode = 200;
                    }
                    else {
                        System.out.println("URI non trovato");
                        rCode = 404;
                        response = errore();
                    }
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                    System.exit(1);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            t.sendResponseHeaders(rCode, response.length());
            OutputStream os = t.getResponseBody(); //chiude la comunicazione
            os.write(response.getBytes());
            os.close();
        }

        private String errore() {
            return "404 Pagina non trovata ";
        }
    }
}