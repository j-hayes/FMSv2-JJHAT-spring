package Main.Entities.Facility;

import java.util.ArrayList;
import java.util.List;

public class Facility {

	private int id;

    private String name;

    private List<Unit> units;

	private int buildingNumber;

	private int capacity;

	public Facility() {
        units = new ArrayList<Unit>();
	}

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public List<Unit> getUnits(){
        return this.units;
    }

    public void setUnits(List<Unit> unit){
        this.units = unit;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getBuildingNumber() {
        return buildingNumber;
    }


    public void setBuildingNumber(int buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public void setCapacity(int capacity){
        this.capacity = capacity;
    }

    public int getCapacity(){
        return capacity;
    }

    public int getTotalCapacity() {
        int cap = 0;
        if(units != null && !units.isEmpty())
        for (Unit unit:units){
           cap += unit.getCapacity();
        }
        return cap+capacity;
    }


}
