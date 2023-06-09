package com.engeto;

import java.time.LocalDate;

public class Plant {
    private String name;
    private String notes;
    private LocalDate planted;
    private LocalDate watering;
    private int frequencyOfWatering;

    //regionconstructors

    public Plant(String name, String notes, LocalDate planted, LocalDate watering, int frequencyOfWatering) throws PlantException {
        this.name = name;
        this.notes = notes;
        this.planted = planted;
        this.setWatering(watering);
        this.setFrequencyOfWatering(frequencyOfWatering);
    }

    public Plant(String name, LocalDate planted, int frequencyOfWatering) throws PlantException {
        this(name,"",planted,LocalDate.now(),frequencyOfWatering);
    }

    public Plant(String name) throws PlantException {
        this(name,"",LocalDate.now(),LocalDate.now(),7);
    }

    //endregion


    //region getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getPlanted() {
        return planted;
    }

    public void setPlanted(LocalDate planted) {
        this.planted = planted;
    }

    public LocalDate getWatering() {
        return watering;
    }

    public void setWatering(LocalDate watering) throws PlantException {
        if (watering.isBefore(this.planted)) {
            throw new PlantException("Datum posledni zalivky nesmi byt starsi nez datum zasazeni rostliny "
                                        + this.planted + " (zadano:" + watering + ")");
        }
        this.watering = watering;
    }

    public int getFrequencyOfWatering() {
        return frequencyOfWatering;
    }

    public void setFrequencyOfWatering(int frequencyOfWatering) throws PlantException {
        if (frequencyOfWatering <= 0){
            throw new PlantException("Frekvence zalivky ve dnech musi byt vetsi nez nula (zadano:"
                                        + frequencyOfWatering + ")");
        }
        this.frequencyOfWatering = frequencyOfWatering;
    }

    //endregion

    public String getWateringInfo(){
        return "Name of the plant: " + this.name
                + "\nDate of last watering: " + this.watering
                + "\nRecommended date of next watering: " + this.watering.plusDays(this.frequencyOfWatering);
    }

    @Override
    public String toString() {
        return name +
                " " + notes +
                " " + frequencyOfWatering +
                " " + watering +
                " " + planted;
    }
}
