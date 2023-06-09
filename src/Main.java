import com.engeto.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //region  3 instances of Plant - testing if class constructors Plant work correctly
        //including error handling for watering and frequencyOfWatering
        Plant plantI = null;
        try {
            plantI = new Plant("Ibisek",
                                    "bile kvety",
                                    LocalDate.of(2022,3,1),
                                    LocalDate.of(2023,6,1),
                                    4);
            System.out.println(plantI.getWateringInfo());
        } catch (PlantException e) {
            System.err.println("Nastala chyba pri zadavani kvetiny: " + e.getLocalizedMessage());
        }

        Plant plantII = null;
        try {
            plantII = new Plant("Boruvka",
                                        LocalDate.of(2022,4,1),
                                        6);
            System.out.println(plantII.getWateringInfo());
        } catch (PlantException e) {
            System.err.println("Nastala chyba pri zadavani kvetiny: " + e.getLocalizedMessage());
        }

        Plant plantIII = null;
        try {
            plantIII = new Plant("Sasanka");
            System.out.println(plantIII.getWateringInfo());
        } catch (PlantException e) {
            System.err.println("Nastala chyba pri zadavani kvetiny: " + e.getLocalizedMessage());
        }


        //endregion

        //region testing if PlantException works correctly for frequencyOfWatering <= 0
        try {
            plantI.setFrequencyOfWatering(-1);
        } catch (PlantException e) {
            System.out.println("Nastala chyba: " + e.getLocalizedMessage());
        }

        //endregion

        //region testing if PlantException works correctly for watering < planted
        try {
            plantII.setWatering(LocalDate.of(2022,3,31));
        } catch (PlantException e) {
            System.out.println("Nastala chyba: " + e.getLocalizedMessage());
        }
        //endregion

        //region testing instance of ListOfPlants
        ListOfPlants listOfPlants = new ListOfPlants();

        //adding plantI to the listOfPlants
        listOfPlants.addNewPlant(plantI);
        System.out.println(listOfPlants);
        //adding plantII to the listOfPlants
        listOfPlants.addNewPlant(plantII);
        System.out.println(listOfPlants);
        //adding plantIII to the listOfPlants
        listOfPlants.addNewPlant(plantIII);
        System.out.println(listOfPlants);

        //getting plant on index 1; for index 3 and greater an error is thrown
        System.out.print("====Getting plant on index 1====");
        try {
            System.out.println(listOfPlants.getPlantFromListByIndex(1));
        } catch (PlantException e) {
            System.out.println("Nastala chyba: " + e.getLocalizedMessage());
        }

        //removing plantII from the listOfPlants
        System.out.print("====Removing plantII from the listOfPlants====");
        listOfPlants.removePlant(plantII);
        System.out.println(listOfPlants);

        //endregion


        //region reading list from file TASK 12
        try {
            listOfPlants.loadDataFromFile(Settings.inputFile(),Settings.delimeter());
        } catch (PlantException e) {
            System.out.println("Nastala chyba pri cteni dat ze souboru " + e.getLocalizedMessage());
        }

        //listing listOfPlants updated by data from file
        System.out.print("====Updated list of plants====");
        System.out.println(listOfPlants);
        //endregion

        //region testing getWateringInfo for all plants in listOfPlants TASK 13
        System.out.println("====Testing getWateringInfo for all plants in listOfPlants====");
        for (int i = 0; i < listOfPlants.getSizeOfList(); i++) {
            try {
                System.out.println(listOfPlants.getPlantFromListByIndex(i).getWateringInfo());
            } catch (PlantException e) {
                System.out.println("Tato chyba nikdy nevyskoci " + e.getLocalizedMessage());;
            }
        }
        //endregion

        //region TASK 14
        //adding plantII to the listOfPlants
        listOfPlants.addNewPlant(plantII);
        System.out.println(listOfPlants);
        //adding plantIII to the listOfPlants
        listOfPlants.addNewPlant(plantIII);
        System.out.println(listOfPlants);
        listOfPlants.removePlant(plantII);
        System.out.println(listOfPlants);
        //endregion

        //region writing to file newPlantsFile.txt  TASK 15
        System.out.println("====Testing writing to a new file for all plants in listOfPlants====");
        try {
            listOfPlants.writeDataToFile(Settings.outputFile(), Settings.delimeter());
        } catch (PlantException e) {
            System.out.println("Nepodarilo se zapsat data do souboru " + e.getLocalizedMessage());;
        }
        //endregion

        //region reading the new file content   TASK 16
        ListOfPlants newListOfPlants = new ListOfPlants();
        try {
            newListOfPlants.loadDataFromFile(Settings.outputFile(),Settings.delimeter());
        } catch (PlantException e) {
            System.out.println("Nastala chyba pri cteni dat ze souboru " + e.getLocalizedMessage());
        }

        //listing listOfPlants updated by data from file
        System.out.print("====Reading content of the new file====");
        System.out.println(newListOfPlants);
        //endregion

        //region Lekce 06
        //  serazeni seznamu rostlin podle nazvu rostliny vzestupne
        List<Plant> soretdPlantList = new ArrayList<>();
        for (int i = 0; i < newListOfPlants.getSizeOfList(); i++) {
            try {
                soretdPlantList.add(newListOfPlants.getPlantFromListByIndex(i));
            } catch (PlantException e) {
                System.out.println("Tato chyba nikdy nevyskoci " + e.getLocalizedMessage());;
            }
        }
        PlantNameComparator plantNameComparator = new PlantNameComparator();
        Collections.sort(soretdPlantList, plantNameComparator);
        System.out.print("====Vypis seznamu rostlin podle nazvu rostliny vzestupne====");
        soretdPlantList.forEach(System.out::println);

        //  serazeni seznamu rostlin podle posledni zalivky vzestupne
        plantNameComparator.setOrderBy(PlantNameComparator.Order.WATERING);
        Collections.sort(soretdPlantList, plantNameComparator);
        System.out.print("====Vypis seznamu rostlin podle posledni zalivky vzestupne====");
        soretdPlantList.forEach(System.out::println);
    }
}