package com.engeto;

import java.util.Comparator;

public class PlantNameComparator implements Comparator<Plant> {
    public enum Order {NAME, WATERING}
    private Order orderBy = Order.NAME;

    public void setOrderBy(Order orderBy) {
        this.orderBy = orderBy;
    }

    @Override
    public int compare(Plant o1, Plant o2) {
        int result;
        switch (orderBy){
            case NAME :
                result = o1.getName().compareTo(o2.getName());
                break;
            case WATERING:
                result = o1.getWatering().compareTo(o2.getWatering());
                break;
            default:
                result = o1.getName().compareTo(o2.getName());
        }
        return result;
    }
}
