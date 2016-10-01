/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rearth.Helpers;

import rearth.Helpers.TimeService.Datum;
import rearth.Helpers.TimeService.Wochentage;
import rearth.Helpers.TimeService.Zeit;
import rearth.HomeUI_DesignController;

/**
 *
 * @author Darkp
 */
public final class Stundenplan {
    
    private static final Stundenplan instance = new Stundenplan();
    
    private int activeDay;
    private boolean ishidden = false;
    
    public static Stundenplan getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return "Stundenplan{" + "activeDay=" + activeDay + ", ishidden=" + ishidden + '}';
    }
    
    public void show() {
        
        HomeUI_DesignController GUI = HomeUI_DesignController.getInstance();
        GUI.createStundenplan(Fächer[activeDay], Wochentage.values()[activeDay].toString(), ishidden);
        
    }
    
    
    public void updateDay(){
        
        Wochentage curDay = new Datum().getDayID();
        switch (curDay) {
            case Dienstag:
                activeDay = 2;
                break;
            case Mittwoch:
                activeDay = 3;
                break;
            case Donnerstag:
                activeDay = 4;
                break;
            case Freitag:
                activeDay = 5;
                break;
            case Montag:
            case Samstag:
            case Sonntag:
                activeDay = 1;
                break;
        }
        
        Zeit zeit = new Zeit();
        if ((zeit.getHours() - 10) >= Fächer[activeDay].length / 2 && curDay != Wochentage.Sonntag) {
            activeDay++;
        }
        if (activeDay >= 6) {
            activeDay = 1;
        }
        
        ishidden = ((curDay.equals(Wochentage.Freitag) && zeit.getHours() >= 10)) || curDay.equals(Wochentage.Samstag);
        System.out.println("hidded=" + ishidden);   
        show();
    }
    
    private final String[][] Fächer = new String[6][];
    
    Stundenplan() {
        activeDay = 1;
        Fächer[1] = new String[]{"Erdkunde", "Erdkunde", "Wirtschaft", "Wirtschaft", "Geschichte", "Geschichte"};
        Fächer[2] = new String[]{"Englisch", "Englisch", "Deutsch", "Deutsch", "Physik", "Physik", "nichts", "nichts", "Kunst", "Kunst"};
        Fächer[3] = new String[]{"Mathe", "Mathe", "Wirtschaft", "Wirtschaft", "Physik", "Physik", "nichts", "nichts", "nichts", "Sport", "Sport"};
        Fächer[4] = new String[]{"Deutsch", "Deutsch", "Religion", "Religion", "Biologie", "Biologie", "Mathe"};
        Fächer[5] = new String[]{"Mathe", "Mathe", "Englisch", "Englisch"};
        
        updateDay();
    }
    
}
