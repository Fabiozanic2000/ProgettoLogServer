package parser;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import dberrori.DblogErrori;
import io.krakens.grok.api.Grok;
import io.krakens.grok.api.GrokCompiler;
import io.krakens.grok.api.Match;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ErrorLogParser {

    private String convertiMese(String mese) {
        return switch (mese) {
            case "Jan" -> "01";
            case "Feb" -> "02";
            case "Mar" -> "03";
            case "Apr" -> "04";
            case "May" -> "05";
            case "Jun" -> "06";
            case "Jul" -> "07";
            case "Aug" -> "08";
            case "Sep" -> "09";
            case "Oct" -> "10";
            case "Nov" -> "11";
            case "Dec" -> "12";
            default -> "";
        };
    }
    public long lastTime = -1;
    public ArrayList<String> ipSospetti = new ArrayList<String>();
    private void malevolo(Map<String, Object> capture) throws ParseException {
        int threshold = 2; //delta

        String lastLine = "";
        String dataora = capture.get("anno").toString() + "-" + convertiMese(capture.get("mese").toString()) +
                "-" + capture.get("giorno_del_mese").toString() + " " + capture.get("orario");
        //System.out.println("la data e " + dataora);

        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        long currentTime = dateFormat.parse(dataora).getTime()/1000;
        //System.out.println("ts : "+currentTime);

        if (lastTime != -1){
            long duration = (currentTime - lastTime);
            if (duration <= threshold){
                /*if (Collections.frequency(ipSospetti, capture.get("clientip")) == 0){
                    ipSospetti.add(capture.get("clientip").toString());
                }
                else if*/
                System.out.println("########");
                System.out.println("dataora: " + dataora);
                System.out.println(capture.get("clientip").toString());
                System.out.println("#######");


            }
        }
        lastTime = currentTime;
        System.out.println("lastTime" + lastTime);

    }
    public void parse(File file, GeoIp geoip) throws IOException, GeoIp2Exception, SQLException {
        DblogErrori db = new DblogErrori("dberr");
        db.checkCreateDb();

        Scanner sc = new Scanner(file);
        String input;
        GrokCompiler grokCompiler = GrokCompiler.newInstance();
        grokCompiler.registerDefaultPatterns();

        //inserire pattern che deve compilare
        final Grok grok = grokCompiler.compile("\\[%{DAY:giorno_della_settimana} %{MONTH:mese} %{MONTHDAY:giorno_del_mese} %{TIME:orario} %{YEAR:anno}\\] \\[:%{LOGLEVEL:tipo_errore}\\] \\[%{WORD:ignora} %{POSINT:pid}\\] \\[%{WORD:ignora} %{IP:clientip}:%{POSINT:porta_client}\\] \\[%{WORD:ignora} %{IP:ignora}\\] ModSecurity: %{WORD:errorcode}. %{GREEDYDATA:resto_del_mondo}");
        System.out
                .println("FILE degli errori");
        //StringBuffer sb = new StringBuffer();
        long delta = 5;
        while (sc.hasNextLine()) {
            try {

                input = sc.nextLine(); //legga la riga
                Match gm = grok.match(input);
                Map<String, Object> capture = gm.capture();


                malevolo(capture);


                //servono per il db dopo
                String data = capture.get("anno").toString() + "-" + convertiMese(capture.get("mese").toString()) +
                        "-" + capture.get("giorno_del_mese").toString() ;

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date data_unix = df.parse(data);
                long unix_time = data_unix.getTime() /1000;

                db.insert(capture.get("giorno_della_settimana").toString(),
                        capture.get("mese")
                                .toString(), //ritora Aug, Sep ...
                        Integer.parseInt(capture
                                .get("giorno_del_mese")
                                .toString()),
                        capture.get("orario")
                                .toString(),
                        Integer.parseInt(capture
                                .get("anno")
                                .toString()),
                        unix_time,
                        capture.get("tipo_errore")
                                .toString(),
                        Integer.parseInt(capture
                                .get("pid")
                                .toString()),
                        capture.get("clientip")
                                .toString(),
                        Integer.parseInt(capture
                                .get("porta_client")
                                .toString()),
                        capture.get("errorcode")
                                .toString(),
                        geoip.getCountry(capture
                                .get("clientip")
                                .toString()),
                        capture.get("resto_del_mondo")
                                .toString());
                //System.out.println(capture.toString());
                //System.out.println(c);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
