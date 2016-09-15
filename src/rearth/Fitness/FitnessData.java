/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rearth.Fitness;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import rearth.Helpers.TimeService;
import rearth.Helpers.TimeService.Datum;
import rearth.Helpers.TimeService.Zeit;

/**
 *
 * @author Darkp
 */
public class FitnessData {
    
    private final String folderPath;
    private final String FitnessDataFile;
    private final File FitnessData;
    
    public enum Types {
        Joggen, Radeln, Workout
    }
    
    public enum length {
        kurz, mittel, lang
    }
    
    public void addActivity(Types typ, length dauer, Datum datum, Zeit zeit) {
        
        DataObject object = new DataObject(typ, dauer, datum, zeit);
        object.save();
        Activities.add(object);
        sortByDateTime(Activities);
        
        
    }
    
    public void addActivity(Types typ, length dauer, Datum datum) {
        addActivity(typ, dauer, datum, new Zeit());
    }
    
    
    
    private FitnessData() {
        
        String folderPathA = new File("").getAbsolutePath();
        folderPath = folderPathA + "/FitnessData";
        File folder = new File(folderPath);
        
        if(!folder.exists()) {
            folder.mkdir();
            System.out.println("Created FitnessData folder: " + folderPath);
        }
        
        FitnessDataFile = folderPath + "/Activities.txt";
        FitnessData = new File((FitnessDataFile));
        
        if (!FitnessData.exists()) {
            try {
                FitnessData.createNewFile();
                System.out.println("Created FitnessData file: " + FitnessDataFile);
            } catch (IOException ex) {
                Logger.getLogger(FitnessData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        initData(readFile(FitnessData));
        sortByDateTime(Activities);
        System.out.println(Activities);
    }
    
    private void sortByDateTime(ArrayList<DataObject> List) {
        
        Collections.sort(List);
        
    }
    
    private final ArrayList<DataObject> Activities = new ArrayList<>();
    
    private static final FitnessData instance = new FitnessData();
    
    private void initData(ArrayList<String> Data) {
        
        for (String Object : Data) {
            
            Types typ;
            length duration;
            Datum date;
            Zeit time;
            
            String Text[] = Object.split("/");
            
            typ = Types.valueOf(Text[0]);
            duration = length.valueOf(Text[1]);
            String dateParts[] = Text[2].split(":");
            //System.out.println(Arrays.toString(dateParts) + " I "+ Text[2]);
            date = new Datum(new int[]{Integer.valueOf(dateParts[0]), Integer.valueOf(dateParts[1]), Integer.valueOf(dateParts[2])});
            time = new Zeit(Text[3].split(":")[0], Text[3].split(":")[1]);
            
            DataObject object = new DataObject(typ, duration, date, time);
            Activities.add(object);
            
            
        }
        
    }
    
    private ArrayList<String> readFile(File file) {
        
        BufferedReader br;
        ArrayList<String> Text = new ArrayList<>();
        String curText;
        
        try {
            br = new BufferedReader(new FileReader(file));
            
            while ((curText = br.readLine()) != null) {
                Text.add(curText);
            }
            
            br.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FitnessData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FitnessData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        return Text;
    }
    
    public static FitnessData getInstance() {
        return instance;
    }
    
    class DataObject implements Comparable<DataObject>{
        
        Types type;
        length länge;
        Datum datum;
        Zeit zeit;
        
        void save() {
            
            String toWrite = System.getProperty("line.separator") + type + "/" + länge + "/" + datum.toString(TimeService.DateFormat.Normal) + "/" + zeit.toString(true);
            System.out.println("Adding Activity: " + toWrite);
            
            try {
                Files.write(Paths.get(FitnessDataFile), toWrite.getBytes(), StandardOpenOption.APPEND);
            } catch (IOException ex) {
                Logger.getLogger(FitnessData.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        DataObject(Types type, length länge, Datum datum, Zeit zeit) {
            this.type = type;
            this.länge = länge;
            this.datum = datum;
            this.zeit = zeit;
        }

        @Override
        public String toString() {
            return "DataObject{" + "type=" + type + ", l\u00e4nge=" + länge + ", datum=" + datum + ", zeit=" + zeit + '}';
        }

        @Override
        public int compareTo(DataObject t) {
            
            datum.getDate().setHours(zeit.getHours());
            datum.getDate().setMinutes(zeit.getMinutes());
            t.datum.getDate().setHours(t.zeit.getHours());
            t.datum.getDate().setMinutes(t.zeit.getMinutes());
            
            return datum.getDate().compareTo(t.datum.getDate());
            
        }
        
        
        
        
    }
    
}