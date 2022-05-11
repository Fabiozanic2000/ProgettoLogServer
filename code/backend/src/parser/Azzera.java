package parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Azzera {
    public void azzera(File file) throws IOException {
        //Creo un buffer per scrivere dentro al file
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(""); //scrivo una stringa vuota così sovrascrivo il file
        writer.close(); //chiudo il file
    }
}
