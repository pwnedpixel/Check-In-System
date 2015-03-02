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
        monitor.updateMessage("Not Monitoring...");
        boolean monitoring = true;
        while (monitoring) {
            if (personDB.getWarningStatus() == 1) {
                monitor.updateMessage("EMERGENCY!");
                System.out.println("Emerg");
            } else {
                monitor.updateMessage("No Warnings");
                System.out.println("noemerg");
            }
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
            System.out.println("hi"+Math.random());
        }
    }   
}
