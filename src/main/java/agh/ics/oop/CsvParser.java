package agh.ics.oop;

import java.io.*;
import java.util.Scanner;


public class CsvParser {

    public static Config getConfigFromFile(String fpath){


        int[] configParams = new int[19];

        //konfiguracja prechowywana jako plik txt jednolinijkowy w postaci
        //x-x-x-x---x-x-x-x---x-x-x-x-x---x-x-x---x (kolejne x-y to kolejne zmienne w rekordzie)


        try{
            File file = new File(fpath);
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()){
                String line = reader.nextLine();
                String[] configTypes = line.split("---");
                int i =0;
                for(String str: configTypes){
                    String[] specificType = str.split("-");
                    for(String str1: specificType){
                        try{
                            configParams[i] = Integer.parseInt(str1);
                            i+=1;
                        } catch (NumberFormatException e) {
                            configParams[i] = 1;

                        }
                    }
                }

            }

            return new Config(configParams);
        } catch (FileNotFoundException e) {

            throw new RuntimeException(e);
        }
    }

    public static String csvRowFromString(Object[] strtab){
        StringBuilder ret = new StringBuilder();

        for(Object s: strtab){
            ret.append("\"").append(s.toString()).append("\",");
        }

        ret.append("\n");

        return ret.toString();
    }

    public static void createXlsFile(String fname){
        if(fname==null){
            return;
        }


        try{

            FileOutputStream outputStream = new FileOutputStream(fname);
            BufferedWriter writer = new BufferedWriter(new FileWriter(fname, true));
            writer.append(csvRowFromString(new String[]{"day", "aliveThatDay", "diedThatDay", "totalDeaths"}));
            writer.close();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public static void appendDailyStats(String fpath, MapStatistics stats, int dayNum){
        if(fpath == null){
            return;
        }
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(fpath, true));
            Integer[] arr = {dayNum, stats.getAliveAnimalCount(), stats.getDeathsToday(), stats.getDeathCount()};
            writer.append(csvRowFromString(arr));

            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
