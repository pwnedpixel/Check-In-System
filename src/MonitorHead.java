
import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Andy
 */
public class MonitorHead
{
    
    public static void main(String[] args)
    {
        DbInterface personDB = new DbInterface();
        MonitorGuiManager monitor = new MonitorGuiManager();
        monitor.run();
        boolean monitoring = true;
        LinkedList previous = new LinkedList();
        while (monitoring) {
            if (personDB.getStations()!=previous)
            {
                monitor.sendStations(personDB.getStations());
            }           
            try {
                Thread.sleep(250);
            } catch (Exception e) {
            }
            previous = personDB.getStations();
        }
    }   
}
