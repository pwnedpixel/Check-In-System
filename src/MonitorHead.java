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
        while (monitoring) {
            monitor.sendStations(personDB.getStations());
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
            System.out.println("hi"+Math.random());
        }
    }   
}
