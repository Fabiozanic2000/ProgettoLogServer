package parser;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import db.Dblog;
import io.krakens.grok.api.Grok;
import io.krakens.grok.api.GrokCompiler;
import io.krakens.grok.api.Match;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;
import java.util.TimerTask;

public class ParseLog extends TimerTask {
    @Override
    public void run() {
        // creo il parser dei file di log
        GrokCompiler grokCompiler = GrokCompiler.newInstance();
        grokCompiler.registerDefaultPatterns();

        //inserire pattern che deve compilare
        final Grok grok = grokCompiler.compile("%{IPORHOST:clientip} %{USER:ident} %{USER:auth} \\[%{HTTPDATE:timestamp}\\] \"(?:%{WORD:verb} %{NOTSPACE:request}(?: HTTP/%{NUMBER:httpversion})?|%{DATA:rawrequest})\" %{NUMBER:response} (?:%{NUMBER:bytes}|-)");

        //Classe per geolocalizzare indirizzo ip
        GeoIp geoip = new GeoIp();
        Azzera azzera = new Azzera();
        Dblog db = new Dblog("dblog");
        db.checkCreateDb();

        //leggo tutti i file della cartella
        File directoryPath = new File("." + File.separator + "code" + File.separator + "backend" + File.separator + "log_file");
        System.out.println(directoryPath.getAbsolutePath());

        //li metto in un array e li leggo uno per uno
        File[] filesList = directoryPath.listFiles();
        System.out.println("List of files and directories in the specified directory:");
        Scanner sc;
        System.out.println("Inizio a parsare i file di log");
        for (File file : filesList) { //leggo ogni file una riga alla volta

            if (!file.exists())
                continue;

            if (file.getName().contains(".err")) {
                ErrorLogParser elp = new ErrorLogParser();
                try {
                    elp.parse(file, geoip);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (GeoIp2Exception e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                continue;
            }
            //if (file.)

            //stampa informazioni sul file
            //System.out.println("File name: " + file.getName());
            //System.out.println("File path: " + file.getAbsolutePath());
            //System.out.println("Size :" + file.getTotalSpace());

            //scanner del file
            try {
                sc = new Scanner(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            String input;

            //StringBuffer sb = new StringBuffer();
            while (sc.hasNextLine()) {
                input = sc.nextLine(); //legga la riga

                //fa il match della stringa in input con il pattern da matchare
                Match gm = grok.match(input);
                Map<String, Object> capture = gm.capture();
                //System.out.print(capture.toString() + "  ");

                String data = capture.get("MONTHDAY").toString() + "." + capture.get("MONTH").toString() + "." + capture.get("YEAR").toString();

                String rawrequest;
                if (capture.get("rawrequest") == null)
                    rawrequest = "";
                else
                    rawrequest = capture.get("rawrequest").toString();

                try {
                    db.insert(capture.get("request").toString(), capture.get("auth").toString(), capture.get("ident").toString(),
                            capture.get("verb").toString(), capture.get("TIME").toString(), Integer.parseInt(capture.get("response").toString()),
                            Integer.parseInt(capture.get("bytes").toString()), capture.get("clientip").toString(), rawrequest, data,
                            capture.get("timestamp").toString(), geoip.getCountry(capture.get("clientip").toString()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (GeoIp2Exception e) {
                    throw new RuntimeException(e);
                }

                //Chiamo la classe per azzerare il file così controllo se l'ho già letto


            }
        }
        System.out.println("Ho eseguito l'inserimento dei dati nel db ");
        System.out.println("Inizio ad azzerare i file ");
        for (File file : filesList){
            //Chiamo il metodo per azzerare i file che viene passato come parametro
            try {
                azzera.azzera(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Ho finito di azzeerare i file ");
    }
}

