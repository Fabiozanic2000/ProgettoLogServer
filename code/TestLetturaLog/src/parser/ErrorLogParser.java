package parser;

import db.DbError;
import db.Dblog;
import io.krakens.grok.api.Grok;
import io.krakens.grok.api.GrokCompiler;
import io.krakens.grok.api.Match;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

public class ErrorLogParser {
    public void parse(File file, GeoIp geoip) throws FileNotFoundException {
        DbError db = new DbError("dberr");

        Scanner sc = new Scanner(file);
        String input;
        GrokCompiler grokCompiler = GrokCompiler.newInstance();
        grokCompiler.registerDefaultPatterns();

        //inserire pattern che deve compilare
        final Grok grok = grokCompiler.compile("");

        //StringBuffer sb = new StringBuffer();
        while (sc.hasNextLine()) {
            input = sc.nextLine(); //legga la riga
            Match gm = grok.match(input);
            Map<String, Object> capture = gm.capture();


        }
    }
}
