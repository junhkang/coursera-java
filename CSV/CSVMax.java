package CSV;

/**
 * Find the highest (hottest) temperature in any number of files of CSV weather data chosen by the user.
 * 
 * @author Duke Software Team 
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class CSVMax {


    public void testcoldestInDay () {
        FileResource fr = new FileResource();
        CSVRecord largest = coldestHourInFile(fr.getCSVParser());
        System.out.println("coldest temperature was " + largest.get("TemperatureF")
                   );
                }
                //" at " + largest.get("TimeEST")

                //  public CSVRecord hottestInManyDays() {
                    //       CSVRecord largestSoFar = null;
                    //       DirectoryResource dr = new DirectoryResource();
                    //       // iterate over files
                    //       for (File f : dr.selectedFiles()) {
                        //           FileResource fr = new FileResource(f);
                        //           // use method to get largest in file.
                        //          CSVRecord currentRow = hottestHourInFile(fr.getCSVParser());
          //          // use method to compare two records
          //          largestSoFar = getLargestOfTwo(currentRow, largestSoFar);
          //       }
          //      //The largestSoFar is the answer
          //      return largestSoFar;
          //  }

    public CSVRecord getSmallestOfTwo (CSVRecord currentRow, CSVRecord smallestSoFar) {
      if (smallestSoFar == null)    {
                smallestSoFar = currentRow;
            }
            else {
                double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                double smallestTemp = Double.parseDouble(smallestSoFar.get("TemperatureF"));
                if (currentTemp == -9999)   {
                    currentTemp = 9999;}
                if (currentTemp<smallestTemp) {      
                    smallestSoFar = currentRow;
                }             
            }
        return smallestSoFar;
    }

    //    public void testHottestInManyDays () {
        //        CSVRecord largest = hottestInManyDays();
        //        System.out.println("hottest temperature was " + largest.get("TemperatureF") +
        //                   " at " + largest.get("DateUTC"));
        //    }
    public CSVRecord coldestHourInFile(CSVParser parser) {
        CSVRecord smallestSoFar = null;
        for (CSVRecord currentRow : parser) {
            if (smallestSoFar == null)    {
                smallestSoFar = currentRow;
            }
            else {
                double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                double smallestTemp = Double.parseDouble(smallestSoFar.get("TemperatureF"));
                if (currentTemp == -9999)   {
                    currentTemp = 9999;}
                if (currentTemp<smallestTemp) {      
                    smallestSoFar = currentRow;
                }             
            }
        }
        return smallestSoFar;
    }
    public String getsmallestamongfile(DirectoryResource dr1) {
               CSVRecord smallestSoFar = null;
               
               for (File f : dr1.selectedFiles()) {
                   FileResource fr = new FileResource(f);
                   CSVRecord currentRow = coldestHourInFile(fr.getCSVParser());
                   smallestSoFar = getSmallestOfTwo(currentRow, smallestSoFar);
                 }
                 return smallestSoFar.get("TemperatureF");
              // + smallestSoFar.get("DateUTC") + " " + smallestSoFar.get("TemperatureF"));
        }
    public String fileWithColdestTemperature() {
        DirectoryResource drc = new DirectoryResource();
        
        String smallest = getsmallestamongfile(drc);
        File result = null;
        for (File f : drc.selectedFiles())   {
            FileResource fr = new FileResource(f);
            CSVRecord file = coldestHourInFile(fr.getCSVParser());
            String tempfile = file.get("TemperatureF");
                if (tempfile.equals(smallest))    {
                    result = f;
                }
            } return result.toString();
    }
    public void testFileWithColdestTemperature()    {
        FileResource fr = new FileResource(fileWithColdestTemperature());
        CSVRecord lowesttemp = coldestHourInFile(fr.getCSVParser());
        System.out.println("Coldest day was in file " + fileWithColdestTemperature());
        System.out.println("Coldest temperature on that day was "+lowesttemp.get("TemperatureF"));
        CSVParser parser = (fr.getCSVParser());
        
        for (CSVRecord currentRow : parser) {
                String currentTemp = currentRow.get("TemperatureF");
                String date = currentRow.get("DateUTC");
                System.out.println(date + " " + currentTemp);
            }
    }
    public CSVRecord lowestHumidityInFile(CSVParser parser){
        CSVRecord smallestSoFar = null;
        for (CSVRecord currentRow : parser) {
            String a = currentRow.get("Humidity");
            if (a.equals("N/A")){
                }
                else 
            if (smallestSoFar == null)    {
                smallestSoFar = currentRow;
            }
            else {
                double currentTemp = Double.parseDouble(currentRow.get("Humidity"));
                double smallestTemp = Double.parseDouble(smallestSoFar.get("Humidity"));
                if (currentTemp<smallestTemp) {      
                    smallestSoFar = currentRow;
                }             
            }
        }return smallestSoFar;
    }
    public void testLowestHumidityInFile()  {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord csv = lowestHumidityInFile(parser);
        System.out.println("Lowest Humidity was " + " " +csv.get("Humidity") 
        + " at " + csv.get("DateUTC"));
    }
    public CSVRecord LowestHuminfiles(DirectoryResource drc)  {
    CSVRecord start = null;
    for (File f : drc.selectedFiles())   {
        FileResource fr = new FileResource(f);
        CSVParser parser = fr.getCSVParser();
        CSVRecord csv = lowestHumidityInFile(parser);
        if (start == null)  {
            start = csv;}
            else {
                double csvd = Double.parseDouble(csv.get("Humidity"));    
                double startd = Double.parseDouble(start.get("Humidity"));
                if (csvd < startd)   {
                    start = csv;
                }
            }
        }
        return start;
    }
    public void printtotalminhum() {
        DirectoryResource dr = new DirectoryResource();
        CSVRecord csv = LowestHuminfiles(dr);
        String a = csv.get("DateUTC");
        System.out.println(a);
        }
    
    
    
    
    public double averageTemperatureInFile(CSVParser parser)  {
        double count = 0;
        double sum = 0;
        for (CSVRecord currentRow : parser)
        {double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
         sum = sum+currentTemp;
         count = count+1;
        }
        return (sum / count);
    }
    public void testAverageTemperatureInFile()  {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        double csv = averageTemperatureInFile(parser);   
        System.out.println("Average temperature in file is " + csv);
    }
    public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value)   {
        double count = 0;
        double sum = 0;
        for (CSVRecord currentRow : parser){
            if  (Double.parseDouble(currentRow.get("Humidity"))>value){
                {double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                 sum = sum+currentTemp;
                 count = count+1;
                }
            }
        }return (sum / count);
        }
    public void testaverageTemperatureWithHighHumidityInFile()  {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        double csv = averageTemperatureWithHighHumidityInFile(parser, 80);   
        System.out.println("Average temperature when high Humidity is" + csv);
        
    }
    }
