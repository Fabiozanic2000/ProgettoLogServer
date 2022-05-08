package parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Azzera {
    public void azzera(File file) throws IOException {
        /*BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write("");
        writer.close();*/
        if (file.delete())
            System.out.println(file.getName() + "è stato eliminato");
        else
            System.out.println(file.getName() + "esiste ancora");
    }
}
