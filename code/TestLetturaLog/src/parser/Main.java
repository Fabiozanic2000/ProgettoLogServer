package parser;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import db.Dblog;
import io.krakens.grok.api.Grok;
import io.krakens.grok.api.GrokCompiler;
import io.krakens.grok.api.Match;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, GeoIp2Exception, SQLException {
        // creo il parser dei file di log
        GrokCompiler grokCompiler = GrokCompiler.newInstance();
        grokCompiler.registerDefaultPatterns();

        //inserire pattern che deve compilare
        final Grok grok = grokCompiler.compile("%{IPORHOST:clientip} %{USER:ident} %{USER:auth} \\[%{HTTPDATE:timestamp}\\] \"(?:%{WORD:verb} %{NOTSPACE:request}(?: HTTP/%{NUMBER:httpversion})?|%{DATA:rawrequest})\" %{NUMBER:response} (?:%{NUMBER:bytes}|-)");

        //Classe per geolocalizzare indirizzo ip
        GeoIp ipp = new GeoIp();
        Azzera azzera = new Azzera();
        Dblog db = new Dblog("dblog");
        db.checkCreateDb();

        //leggo tutti i file della cartella
        File directoryPath = new File(".\\log_file");
        //li metto in un array e li leggo uno per uno
        File[] filesList = directoryPath.listFiles();
        System.out.println("List of files and directories in the specified directory:");
        Scanner sc;

        for (File file : filesList) { //leggo ogni file una riga alla volta
            if (file.getName().equals("gnetshop.err")) //evito il file degli errori perchè non lo sappiamo ancora parsare
                continue;

            //stampa informazioni sul file
            System.out.println("File name: " + file.getName());
            System.out.println("File path: " + file.getAbsolutePath());
            System.out.println("Size :" + file.getTotalSpace());

            //scanner del file
            sc = new Scanner(file);
            String input;

            //StringBuffer sb = new StringBuffer();
            while (sc.hasNextLine()) {
                input = sc.nextLine(); //legga la riga

                //fa il match della stringa in input con il pattern da matchare
                Match gm = grok.match(input);
                Map<String, Object> capture = gm.capture();
                //System.out.print(capture.toString() + "  ");

                //prendo l'ip della riga
                /*String clientip = capture.get("clientip").toString();
                String request = capture.get("request").toString();
                String auth = capture.get("auth").toString();
                String ident = capture.get("ident").toString();
                String httpmethod = capture.get("verb").toString();
                String time = capture.get("TIME").toString();
                int response = Integer.parseInt(capture.get("response").toString());
                int bytes = Integer.parseInt(capture.get("bytes").toString());
                String timestamp = capture.get("timestamp").toString();
                String paese = ipp.getCountry(clientip);*/
                String data = capture.get("MONTHDAY").toString() + "." + capture.get("MONTH").toString() + "." + capture.get("YEAR").toString();


                String rawrequest;
                if (capture.get("rawrequest") == null)
                    rawrequest = "";
                else
                    rawrequest = capture.get("rawrequest").toString();

                /*db.insert(capture.get("request").toString(), capture.get("auth").toString(), capture.get("ident").toString(),
                        capture.get("verb").toString(), capture.get("TIME").toString(), Integer.parseInt(capture.get("response").toString()),
                        Integer.parseInt(capture.get("bytes").toString()), capture.get("clientip").toString(), rawrequest, data,
                        capture.get("timestamp").toString(), ipp.getCountry(capture.get("clientip").toString()));
                */
                //Chiamo la classe per azzerare il file così controllo se l'ho già letto


            }
        }
        System.out.println("Ho eseguito l'inserimento dei dati nel db ");
        for (File file : filesList){

            azzera.azzera(file);
        }
    }



    //System.out.println(ipp.getCountry((String) capture.get("clientip")));*/

    //TODO da sistemare
        /*final Grok grok = grokCompiler.compile("%SYSLOGBASE%");
        String error = "[Wed Aug 25 12:55:58.601261 2021] [:error] [pid 20282] [client 83.216.165.253:59075] [client 83.216.165.253] ModSecurity: Warning. Match of \"rx (?:\\\\\\\\x1f\\\\\\\\x8b\\\\\\\\x08|\\\\\\\\b(?:(?:i(?:nterplay|hdr|d3)|m(?:ovi|thd)|r(?:ar!|iff)|(?:ex|jf)if|f(?:lv|ws)|varg|cws)\\\\\\\\b|gif)|B(?:%pdf|\\\\\\\\.ra)\\\\\\\\b|^wOF(?:F|2))\" against \"RESPONSE_BODY\" required. [file \"/usr/share/modsecurity-crs/rules/RESPONSE-953-DATA-LEAKAGES-PHP.conf\"] [line \"105\"] [id \"953120\"] [msg \"PHP source code leakage\"] [data \"Matched Data: <? found within RESPONSE_BODY: \\\\x03\\\\xa4<\\\\x11U\\\\xb5\\\\x1f\\\\x0e\\\\xa0\\\\x91\\\\xb2p\\\\xfe~\\\\xff\\\\x9b\\\\xa9\\\\xd5\\\\xdd\\\\x97\\\\x13\\\\x0ay\\\\xb1\\\\xa5j\\\\x92\\\\xc2B\\\\xee\\\\xa1S\\\\xdb\\\\x9a^R=[\\\\x9c\\\\xd1\\\\xfb\\\\x04>$\\\\xc4 \\\\xc1\\\\x22@Y\\\\x8aJ\\\\xb7\\\\xfb\\\\x5c\\\\xee\\\\xe3\\\\x93\\\\xd9\\\\xf4\\\\xef\\\\x5cN\\\\xaf\\\\xea\\\\x22\\\\xf3#\\\\x0c\\\\x06\\\\x82\\\\x1b\\\\xf3\\\\xd5-+Y6\\\\x92\\\\xb4\\\\x93e\\\\x18\\\\xd9\\\\x92\\\\x8d\\\\x1ac\\\\xb9\\\\x92\\\\x800\\\\xc1\\\\xff\\\\xff^-\\\\xd3`+\\\\x04\\\\x05\\\\xe5\\\\x84\\\\x80-,\\\\x04T\\\\x98\\\\xc3\\\\xeb\\\\xbd\\\\xf7=\\\\xf0\\\\xa5/P\\\\x81\\\\xc6\\\\x9asb\\\\xd9\\\\x02\\\\xf2x\\\\x80J\\\\xca\\\\xb4{\\\\xef{_#}\\\\xc9Y\\\\xb9\\\\xe4\\\\xac\\\\xcb\\\\xccNm\\\\xb2\\\\xa7oin)\\\\xbd\\\\x10\\\\xe...\"] [severity \"ERROR\"] [ver \"OWASP_CRS/3.2.0\"] [tag \"application-multi\"] [tag \"language-php\"] [tag \"platform-multi\"] [tag \"attack-disclosure\"] [tag \"OWASP_CRS\"] [t [hostname \"shop.gnet.it\"] [uri \"/password\"] [unique_id \"YSY93rJJTBga6-8ecOI@VAAAAAE\"], referer: https://shop.gnet.it/password\n";
        System.out.println(error);
        Match gm = grok.match(error);
        final Map<String, Object> cap = gm.capture();
        System.out.println(cap.toString());*/

}

