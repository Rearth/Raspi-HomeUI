/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rearth.Helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Darkp
 */
public class TimeService {
    
    public enum Wochentage {
        Sonntag, Montag, Dienstag, Mittwoch, Donnerstag, Freitag, Samstag
    }
    
    public enum relativeDays {
        Heute, Morgen, Übermorgen
    }
    
    public enum Monate {
        Januar, Februar, März, April, Mai, Juni, Juli, August, September, Oktober, November, Dezember
    }
    
    public static enum DateFormat {
        KalenderInfo, ActivityInfo, Normal
    }
    
    public static int getDayOfWeek() {              //starting at 1 = Sonntag; 7 = Samstag
        
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        
    }
    
    public static int getDayOfWeek(Date date) {
        
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        
        return dayOfWeek;
    }
    
    public static class Zeit {
        
        private int Hours = 0;
        private int Minutes = 0;

        public int getHours() {
            return Hours;
        }

        public void setHours(int Hours) {
            this.Hours = Hours;
        }

        public int getMinutes() {
            return Minutes;
        }

        public void setMinutes(int Minutes) {
            this.Minutes = Minutes;
        }
        

        @Override
        public String toString() {
            return "Zeit{" + "Hours=" + Hours + ", Minutes=" + Minutes + '}';
        }
        
        public String toString(boolean Formated) {
            if (Formated) {
                String hours = (Hours < 10) ? "0" + Integer.toString(Hours) : Integer.toString(Hours);
                String minutes = (Minutes < 10) ? "0" + Integer.toString(Minutes) : Integer.toString(Minutes);
                return hours + ":" + minutes;
            } else  {
                return this.toString();
            }
        }
        
        
        public void update() {
            Date CurDate = new Date();
            SimpleDateFormat hours = new SimpleDateFormat("HH");
            Hours = Integer.parseInt(hours.format(CurDate));
        
            SimpleDateFormat minutes = new SimpleDateFormat("mm");
            Minutes = Integer.parseInt(minutes.format(CurDate));
        }
        
        public Zeit() {
            
            Date CurDate = new Date();
            SimpleDateFormat hours = new SimpleDateFormat("HH");
            Hours = Integer.parseInt(hours.format(CurDate));
        
            SimpleDateFormat minutes = new SimpleDateFormat("mm");
            Minutes = Integer.parseInt(minutes.format(CurDate));
        }
        
        public Zeit(int Hours, int Minutes) {
            this.Hours = Hours;
            this.Minutes = Minutes;
        }
        
        public Zeit(String Hours, String Minutes) {
            this.Hours = Integer.valueOf(Hours);
            this.Minutes = Integer.valueOf(Minutes);
        }
        
        
    }
    
    public static class Datum {
        
        private Date date;
        
        public Date getDate() {
            return date;
        }
        
        public void update() {
            date = new Date();
        }
        
        public Datum() {
           date = new Date(); 
        }
        
        public Wochentage getDayID() {
            return Wochentage.values()[getDayOfWeek(date) - 1];
        }
        
        public Datum(int a, int b, int c) {
            this(new int[]{a, b, c});
        }
        
        public Datum(int[] Date) {
            
            
            SimpleDateFormat formater = new SimpleDateFormat( "dd/MM/yyyy" );

            String tag = Integer.toString(Date[0]);
            if(Date[0] <= 9) {
                tag = "0" + tag;
            }
            String monat = Integer.toString(Date[1]);
            if (Date[1] <= 9) {
                monat = "0" + monat;
            }

            String Date1 = tag + "/" +  monat + "/" + Integer.toString(Date[2]);

            try {
                this.date = formater.parse(Date1);
            } catch (ParseException ex) {
                Logger.getLogger(TimeService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        public Datum(Date date) {
            this.date = date;
        }
        
        public boolean isInLastWeek() {

            int days = howLongAgo();
            
            return days <= 6;
        }
        
        public int howLongAgo() {
            
            Date olddate = new Date();
            olddate.setDate(date.getDate());
            olddate.setMonth(date.getMonth());
            olddate.setYear(date.getYear());
            
            long difference =  new Date().getTime() - olddate.getTime();
            
            return (int) (difference / (24 * 1000 * 60 * 60));
        }
                
        public String getDay() {
            return Wochentage.values()[getDayOfWeek(date)- 1].name();
        }
        
        public String getMonth() {
            return Monate.values()[Integer.valueOf(new SimpleDateFormat( "MM" ).format(date)) - 1].name();
        }
        
        public int getDayNumber() {
             Calendar c = Calendar.getInstance();
             c.setTime(date);
             
             return c.get(Calendar.DAY_OF_MONTH);
        }
        
        public int getMonthNumber() {
             Calendar c = Calendar.getInstance();
             c.setTime(date);
             
             return c.get(Calendar.MONTH);
        }

        @Override
        public String toString() {
            return "Datum{" + "date=" + date + '}';
        }
        
        public String toString(DateFormat format) {         //KalenderInfo, ActivityInfo, Normal
            
            switch(format) {
                case KalenderInfo:                                        
                    return this.getDay() + ", " + this.getDayNumber() + "." +  this.getMonth();
                case ActivityInfo:
                    if (howLongAgo() == 0) {
                        return "Heute";
                    } else if (howLongAgo() == 1) {
                        return "Gestern";
                    } else if (isInLastWeek()) {
                        return getDay();
                    } else {
                        return Integer.toString(getDayNumber()) + "." + getMonth();
                    }
                case Normal:
                    SimpleDateFormat simpleformat = new SimpleDateFormat( "dd:MM:yyyy" );
                    return simpleformat.format(date);
                default:
                    return this.toString();
                              
            }
            
            
        }
        
        
        
    }
    
    /*public static String getniceFormat(int Datum[]) {
        
        String Text = "Error";
        
        
        
        if (inLastWeek(Datum)) {
            //Text = " am " + TAGE[getDayOfWeekByDate(Datum)];
            if (howLongAgo(Datum) == 0) {
                Text = " Heute";
            } else if (howLongAgo(Datum) == 1) {
                Text = " Gestern";
            } else if (howLongAgo(Datum) == 2) {
                Text = " Vorgestern";
            }
        } else {
            Text = " vor " + Integer.toString(howLongAgo(Datum)) + " Tagen (" + Integer.toString(Datum[0]) + "." + Datum[1] +  ")";
        }
        
        return Text;
    }*/
    
        
    static int howLongAgo(int Datum[]) {
        
        SimpleDateFormat format = new SimpleDateFormat( "dd/MM/yyyy" );
        Date date = new Date();
        
        String Date1 = format.format(date);
        
        String tag = Integer.toString(Datum[0]);
        if(Datum[0] <= 9) {
            tag = "0" + tag;
        }
        String monat = Integer.toString(Datum[1]);
        if (Datum[1] <= 9) {
            monat = "0" + monat;
        }
        
        String Date2 = tag + "/" +  monat + "/" + Integer.toString(Datum[2]);
        
        Date DateObj1 = null;
        Date DateObj2 = null;
        try {
            DateObj1 = format.parse(Date1);
            DateObj2 = format.parse(Date2);
        } catch (ParseException ex) {
            Logger.getLogger(TimeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //System.out.println("Date1: " + Date1);
        //System.out.println("Date2: " + Date2);
        
        long difference = DateObj1.getTime() - DateObj2.getTime();
        
        int differenceDays = (int) (difference / (24* 1000 * 60 * 60));

        //System.out.println("Tage: " + differenceDays);
        
        return differenceDays;
    }
    
}

