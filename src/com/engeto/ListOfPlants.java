package com.engeto;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class ListOfPlants {
    private List<Plant> plantList = new ArrayList<>();

    //method for adding a new plant to the list
    public void addNewPlant(Plant newPlant){
        plantList.add(newPlant);
    }

    //method for getting an existing plant from the list by provided index
    public Plant getPlantFromListByIndex(int index) throws PlantException {
        if (index >= plantList.size()){
            throw new PlantException("Index musi byt v pripustnem rozmezi danem velikosti seznamu, tedy od nuly do "
                    + (plantList.size() -1) + " (zadano:" + index + ")");
        }
        return plantList.get(index);
    }

    //method for removing an existing plant from the list
    public void removePlant(Plant plant){
        plantList.remove(plant);
    }

    public void loadDataFromFile(String inputFile, String delimeter) throws PlantException {
        int lineNumber = 1;
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(inputFile)))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                //System.out.println(line);
                String[] items = line.split(delimeter);
                Plant newPlant = new Plant(items[0],
                        items[1],
                        LocalDate.parse(items[4]),
                        LocalDate.parse(items[3]),
                        Integer.valueOf(items[2]));
                plantList.add(newPlant);
                lineNumber++;
            }
        } catch (FileNotFoundException e) {
            throw new PlantException("Soubor " + inputFile + " nebyl nalezen " + e.getLocalizedMessage());
        } catch (DateTimeParseException e) {        //handling wrong date format
            throw new PlantException("Datum na radce " + lineNumber + " je ve spatnem formatu " + e.getLocalizedMessage());
        }catch (NumberFormatException e) {      //handling wrong int format in frequency
            throw new PlantException("Znak na radce " + lineNumber + " je ve spatnem formatu " + e.getLocalizedMessage());
        }
    }

    public void writeDataToFile(String fileToWrite, String delimeter) throws PlantException {
            try (PrintWriter outputWriter = new PrintWriter(new BufferedWriter(new FileWriter(fileToWrite)))) {
                for (Plant plant : plantList) {
                    outputWriter.println(plant.getName()
                            + delimeter + plant.getNotes()
                            + delimeter + plant.getFrequencyOfWatering()
                            + delimeter + plant.getWatering()
                            + delimeter + plant.getPlanted());
                }
            //outputWriter.flush();
        } catch (IOException e) {
            throw new PlantException("Soubor " + fileToWrite + " nebyl nalezen " + e.getLocalizedMessage());
        }
    }

    public int getSizeOfList(){
        return  plantList.size();
    }

    public Set<LocalDate> setOfPlantedDates(){
        Set<LocalDate> setResult = new HashSet<LocalDate>();
        for (Plant plant : plantList){
            setResult.add(plant.getPlanted());
        }
        return setResult;
    }

    @Override
    public String toString() {
        String listItem ="";
        for (Plant plant : plantList){
            listItem = listItem + "\n" +
                        plant.getName() +
                        " " + plant.getNotes() +
                        " " + plant.getFrequencyOfWatering() +
                        " " + plant.getWatering() +
                        " " + plant.getPlanted();
        }
        return listItem;
    }
}
