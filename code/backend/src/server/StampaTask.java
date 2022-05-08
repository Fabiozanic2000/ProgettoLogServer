package server;

import java.util.TimerTask;

public class StampaTask extends TimerTask {
    int c = 0;
    @Override
    public void run() {
        System.out.println("Ciao"+ c++);
    }
}
