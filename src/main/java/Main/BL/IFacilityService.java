package Main.BL;

import Main.Entities.Facility.Facility;
import Main.Entities.Facility.Unit;

import java.util.List;

public interface IFacilityService {


    List<Facility> listFacilities();

    Facility getFacilityInformation(int id);

    int requestAvailibleCapacity(int facilityId, int unitID);

    Facility addNewFacility(Facility facility);

    Unit addFacilityDetail(int facilityID, Unit unit);

    void removeFacility(Facility facility);

    void RemoveUnit(Unit unit1);
}
