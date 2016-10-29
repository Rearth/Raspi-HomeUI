/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rearth.Helpers;

import com.github.fedy2.weather.YahooWeatherService;
import com.github.fedy2.weather.data.Channel;
import com.github.fedy2.weather.data.Condition;
import com.github.fedy2.weather.data.Forecast;
import com.github.fedy2.weather.data.unit.DegreeUnit;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.xml.bind.JAXBException;
import rearth.Helpers.TimeService.relativeDays;

/**
 *
 * @author Darkp
 */
public class Weather {
    
    final String WOEID = "646299";
    final String WOEIDalternative = "677069";
    
    public Image getImage() {
        
        return getImage(0);
    }
    public Image getImage(int day) {
        
        String ImageName = "";
        String FilePath =      "/home/pi/Desktop/Java/Images/Weather/" + nameState(Codes[day]) + ".png";
        File tester = new File(FilePath);
        if (tester.exists()) {
            System.out.println("Weather image found: " + nameState(Codes[day]));
        } else  {
            System.out.println("No Weather image for \"" + nameState(Codes[day]) + "\" found, using default");
            FilePath = "/home/pi/Desktop/Java/Images/Weather/default.png";
        }
        
        Image image = new Image("file:" + FilePath);
        
        return image;
    }
    
    private int Temperatur[] = {0, 0, 0};
    private String State[] = {"Error", "Error", "Error"};
    private int Codes[] = {0, 0, 0};
    
    
    private static final Weather Instance = new Weather();
    
    private Weather() {
        
    }
    
    public static Weather getinstance() {
        return Instance;
    }
    
    public void updateWidget(Label[] Labels, ImageView[] imageA) {
        
        if (Temperatur[0] < 10) {
            for (Label label : Labels) {
                label.setLayoutX(label.getLayoutX() - 30);
            }
            for (ImageView label : imageA) {
                label.setLayoutX(label.getLayoutX() - 30);
            }
        }
        Labels[0].setText(Integer.toString(Temperatur[0]) + "°");
        Labels[1].setText(State[0]);
        Labels[2].setText(Integer.toString(Temperatur[1]) + "°");
        Labels[3].setText(Integer.toString(Temperatur[2]) + "°");
        imageA[0].setImage(getImage());
        imageA[1].setImage(getImage(1));
        imageA[2].setImage(getImage(2));
        
    }
    
    
    public void init() throws IOException {
        
        System.out.println("-----------------Starting Weather-------------------");
        
        try {
            YahooWeatherService service = new YahooWeatherService();
            Channel channel = null;
            try {
            channel = service.getForecast(WOEID, DegreeUnit.CELSIUS);
            } catch(IllegalStateException ex) {
                System.err.println("Error getting weather Data: No result from the Service!");
                System.err.println("Trying again with Nagold");
                try {
                    channel = service.getForecast(WOEIDalternative, DegreeUnit.CELSIUS);
                } catch (IllegalStateException ex2) {
                    System.err.println("Nagold also not Working");
                    return;
                }
            }
            List<Forecast> forecast= channel.getItem().getForecasts();
            Condition condition = channel.getItem().getCondition();
            Forecast forecastTomorrow = forecast.get(1);
            Forecast forecast2Days = forecast.get(2);
            
            Temperatur[0] = condition.getTemp();
            Temperatur[1] = forecastTomorrow.getHigh();
            Temperatur[2] = forecast2Days.getHigh();
            
            State[0] = nameState(condition.getCode());
            State[1] = nameState(forecastTomorrow.getCode());
            State[2] = nameState(forecast2Days.getCode());
            
            Codes[0] = condition.getCode();
            Codes[1] = forecastTomorrow.getCode();
            Codes[2] = forecast2Days.getCode();
            
            System.out.println("Temperatur: " + Temperatur[0] + "°");
            System.out.println("Zustand: " + State[0] + " I " + condition.getText());
            System.out.println("Morgen: " + State[1] + " I " + Temperatur[1] + "°");
            System.out.println("Übermorgen: " + State[2] + " I " + Temperatur[2] + "°");
            
        } catch (JAXBException ex) {
            System.err.println("Cant get Weather Data" + ex);
        }
        
    }
    
    public int getTemperature() {
        return Temperatur[0];
    }
    public int getTemperature(relativeDays day) {
        switch (day) {
            case Heute:
                return Temperatur[0];
            case Morgen:
                return Temperatur[1];
            case Übermorgen:
                return Temperatur[2];
            default:
                return Temperatur[0];
            
        }
    }
    
    public String getState() {
        return "";
    }
    
    String nameState(int ID) {
        
        switch(ID) {
            case 0: return "Tornado";
            case 1: return "Sturm";
            case 2: return "Hurricane";
            case 3: return "Starke Gewitter";
            case 4: return "Gewitter";
            case 5: return "Schneeregen";
            case 6: return "Schneeregen";
            case 7: return "Schneeregen";
            case 8: return "Leichter Schneefall";
            case 9: return "Niesel";
            case 10: return "Graupel";
            case 11: return "Regen";
            case 12: return "Regen";
            case 13: return "Schneefall";
            case 14: return "Leichter Schneefall";
            case 15: return "Schnee/Wind";
            case 16: return "Schneefall";
            case 17: return "Hagel";
            case 18: return "Schneeregen";
            case 19: return "Staubig";
            case 20: return "Neblig";
            case 21: return "Dunst";
            case 22: return "Staubig";
            case 23: return "Stürmisch";
            case 24: return "Windig";
            case 25: return "Kalt";
            case 26: return "Bewölkt";
            case 27: return "Stark Bewölkt";
            case 28: return "Stark Bewölkt";
            case 29: return "Leicht Bewölkt";
            case 30: return "Leicht Bewölkt";
            case 31: return "Klar";
            case 32: return "Sonnig";
            case 33: return "Überwiegend Klar";
            case 34: return "Überwiegend Sonning";
            case 35: return "Schnee / Hagel";
            case 36: return "Heiß";
            case 37: return "Gewitter";
            case 38: return "Gewitter";
            case 39: return "Gewitter";
            case 40: return "Regen";
            case 41: return "Starker Schneefall";
            case 42: return "Schneefall";
            case 43: return "Starker Schneefall";
            case 44: return "Leicht Bewölkt";
            case 45: return "Regen und Gewitter";
            case 46: return "Schneefall";
            case 47: return "Regen und Gewitter";
            case 3200: return "No Data";
            default:
                return "Error";
        }
    }
    
}
