package Main.Entities.Facility;

/**
 * User: alexthornburg
 * Date: 2/6/14
 * Time: 1:22 PM
 */

import Main.Entities.usage.UnitUsage;
import Main.Entities.usage.UnitUser;

import java.util.ArrayList;
import java.util.List;

public class Unit {

    private int capacity;

    private int unitNumber;

    private int id;

    private int facilityId;

    private List<UnitUsage> usages;

    private List<UnitUser> users;

    public Unit(){
        usages = new ArrayList<UnitUsage>();
    }

    public int getCapacity(){
        return this.capacity;
    }

    public void setCapacity(int capacity){
        this.capacity = capacity;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public List<UnitUsage> getUsages(){
        return usages;
    }

    public void setUsages(List<UnitUsage> usages){
        this.usages = usages;
    }

    public int getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(int unitNumber) {
        this.unitNumber = unitNumber;
    }
/*
    public List<UnitUser> getUsers(){
        return users;
    }

    public void setUsers(List<UnitUser> users){
        this.users = users;
    }
*/
    public int getFacilityId(){
        return facilityId;
    }

    public void setFacilityId(int facilityId){
        this.facilityId = facilityId;
    }


    public List<UnitUser> getUsers() {
        List<UnitUser> users = new ArrayList<UnitUser>();

        for(UnitUsage usage: usages)
        {
            users.add(usage.getUnitUser());
        }
        return users;
    }


}
