package Main;

import Main.BL.*;
import Main.DAL.*;
import Main.Entities.Facility.Facility;
import Main.Entities.Facility.Unit;
import Main.Entities.maintenance.Inspection;
import Main.Entities.maintenance.MaintenanceRequest;
import Main.Entities.maintenance.MaintenanceStaff;
import Main.Entities.usage.UnitUsage;
import Main.Entities.usage.UnitUser;
import org.joda.time.DateTime;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Jackson on 2/12/14.
 */
public class PrintLine {
    public static void main(String[] args)
    {
        IDatabaseConnector connector = new DatabaseConnector();

        try{

            System.out.println("----------------begining program---------------");
            IMaintenanceStaffDAO maintenanceStaffDAO = new MaintenanceStaffDAO(connector);
            IUserDAO userDAO = new UserDAO(connector);
            IUnitDAO unitDAO = new UnitDAO(userDAO, connector);
            IFacilityDAO facilityDAO = new FacilityDAO(connector,unitDAO);
            IMaintenanceRequestDAO maintenanceRequestDAO = new MaintenanceRequestDAO(connector,facilityDAO,maintenanceStaffDAO);
            IFacilityMaintenanceService facilityMaintenanceService = new FacilityMaintenanceService(facilityDAO,unitDAO,maintenanceRequestDAO,maintenanceStaffDAO);

            IUsageDAO usageDAO = new UsageDAO(connector, unitDAO, userDAO);

            FacilityService facilityService = new FacilityService(connector,facilityDAO, unitDAO);
            IInspectionDAO inspectionDAO = new InspectionDAO(connector, facilityService, maintenanceStaffDAO);
            FacilityUseService facilityUseService = new FacilityUseService(facilityDAO,unitDAO,inspectionDAO,usageDAO);

            IInspectionService inspectionService = new InspectionService(inspectionDAO, facilityDAO, maintenanceStaffDAO);



            Random r = new Random();

            List<Unit> units = new ArrayList<Unit>();
            System.out.println("initialize test units");

            Unit unit1 = new Unit();
            unit1.setCapacity(r.nextInt(3));
            unit1.setUnitNumber(111);
            units.add(unit1);

            Unit unit2 = new Unit();
            unit2.setCapacity(r.nextInt(3));
            unit2.setUnitNumber(r.nextInt(3));
            units.add(unit2);


            Unit unit3 = new Unit();
            unit3.setCapacity(r.nextInt(3));
            unit3.setUnitNumber(r.nextInt(3));
            units.add(unit3);


            Unit unit4 = new Unit();
            unit4.setCapacity(r.nextInt(3));
            unit4.setUnitNumber(r.nextInt(3));
            units.add(unit4);

            System.out.print("create facilities objects and add to units to facilities");
            Facility facility1 = new Facility();
            facility1.setBuildingNumber(r.nextInt(4));
            facility1.setCapacity(50);
            facility1.setName("facility1");
            facility1.setUnits(units);


            Facility facility2 = new Facility();
            facility2.setBuildingNumber(r.nextInt(4));
            facility2.setCapacity(34);
            facility2.setName("facility2");
            facility2.setUnits(units);

            Facility facility3 = new Facility();
            facility3.setBuildingNumber(r.nextInt(4));
            facility3.setCapacity(23);
            facility3.setName("facility3");
            facility3.setUnits(units);

            Facility facility = new Facility();
            facility.setBuildingNumber(r.nextInt(4));
            facility.setCapacity(50);
            facility.setName("facility0");
            facility.setUnits(units);

            System.out.print("add facilities with units to the database");

            facility1 = facilityService.addNewFacility(facility1);
            facility2 = facilityService.addNewFacility(facility2);
            facility3 = facilityService.addNewFacility(facility3);
            facility = facilityService.addNewFacility(facility);

            System.out.print("facilities current facilities in DB:");


            printAllFacilities(facilityService.listFacilities());


            Unit unitForUse =  facility2.getUnits().get(0);
            Unit unitForUse1 =  facility2.getUnits().get(1);
            Unit unitForUse2 =  facility2.getUnits().get(2);

            UnitUser user1 = new UnitUser();
            user1.setCreditCard(String.valueOf(r.nextInt(9)));
            user1.setEmailAddress("j@examle");
            user1.setCompanyName("random company "+ r.nextInt());
            user1.setFirstName("some user" + r.nextInt());//random makes sure these people look different in db
            user1.setLastName("lastName" + r.nextInt());
            user1.setPhoneNumber(r.nextInt(10));

            UnitUser user2 = new UnitUser();
            user2.setCreditCard(String.valueOf(r.nextInt(9)));
            user2.setEmailAddress("j@examle");
            user2.setCompanyName("random company "+ r.nextInt());
            user2.setFirstName("some user" + r.nextInt());//random makes sure these people look different in db
            user2.setLastName("lastName" + r.nextInt());
            user2.setPhoneNumber(r.nextInt(10));

            UnitUser user3 = new UnitUser();
            user3.setCreditCard(String.valueOf(r.nextInt(9)));
            user3.setEmailAddress("j@examle");
            user3.setCompanyName("random company "+ r.nextInt());
            user3.setFirstName("some user" + r.nextInt());//random makes sure these people look different in db
            user3.setLastName("lastName" + r.nextInt());
            user3.setPhoneNumber(r.nextInt(10));


            user1 = userDAO.Create(user1);
            user2 = userDAO.Create(user2);
            user3 = userDAO.Create(user3);


            UnitUsage usage1 = new UnitUsage();
            usage1.setUnit(unitForUse);
            usage1.setUnitUser(user1);
            usage1.setStartTime(new Date(new DateTime(2014,1,1,1,1).toDate().getTime()));
            usage1.setEndTime(new Date(new DateTime(2014,1,3,1,1).toDate().getTime()));

            UnitUsage usage2 = new UnitUsage();
            usage2.setUnit(unitForUse1);
            usage2.setUnitUser(user2);
            usage2.setStartTime(new Date(new DateTime(2024,2,2,2,2).toDate().getTime()));
            usage2.setEndTime(new Date(new DateTime(2024,2,3,2,2).toDate().getTime()));

            UnitUsage usage4 = new UnitUsage();
            usage4.setUnit(unitForUse1);
            usage4.setUnitUser(user3);
            usage4.setStartTime(new Date(new DateTime(2044,4,4,4,4).toDate().getTime()));
            usage4.setEndTime(new Date(new DateTime(2044,4,3,4,4).toDate().getTime()));

            UnitUsage usage3 = new UnitUsage();
            usage3.setUnit(unitForUse2);
            usage3.setUnitUser(user3);
            usage3.setStartTime(new Date(new DateTime(2034,3,3,3,3).toDate().getTime()));
            usage3.setEndTime(new Date(new DateTime(2034,3,3,3,3).toDate().getTime()));

            Date d1 = new Date(new DateTime(2034,2,3,3,3).toDate().getTime());
            Date d2 = new Date(new DateTime(2034,4,3,3,3).toDate().getTime());
            Date d3 = new Date(new DateTime(2034,5,3,3,3).toDate().getTime());

            if(!facilityUseService.IsInUseDuringInterval(unitForUse.getId(),d1,d2))
            {
                facilityUseService.assignFacilityToUse(d1,d2, user1,unitForUse);
            }

            if(!facilityUseService.IsInUseDuringInterval(unitForUse1.getId(),d2,d3))
            {
                facilityUseService.assignFacilityToUse(d1,d2, user1,unitForUse1);
            }
            if(!facilityUseService.IsInUseDuringInterval(unitForUse2.getId(),d1,d3))
            {
                facilityUseService.assignFacilityToUse(d1,d2, user1,unitForUse2);
            }

            if(!facilityUseService.IsInUseDuringInterval(unitForUse.getId(),d1,d2))
            {
                facilityUseService.assignFacilityToUse(d1,d2, user1,unitForUse);
            }





            List<UnitUsage> usagesforunit1 = facilityUseService.listActualUsage(unitForUse.getId());
            List<UnitUsage> usagesforunit2 = facilityUseService.listActualUsage(unitForUse1.getId());
            List<UnitUsage> usagesforunit3 = facilityUseService.listActualUsage(unitForUse2.getId());




            System.out.println("------Print sample Usages Just Created-----------");


            if(usagesforunit1!=null){

                for(UnitUsage uu :usagesforunit1)
                {
                    System.out.println("unit:");
                    System.out.println(uu.getUnit());
                    System.out.println("unit user id");
                    System.out.println(uu.getUnitUser());
                    System.out.println(uu.getStartTime().toString());
                    System.out.println(uu.getEndTime());
                    System.out.println();
                }}

            System.out.println("------Print all usages for -----------");
            List<UnitUsage> usagestopring = facilityUseService.listUsages();
            for (UnitUsage uu:usagestopring)
            {
                System.out.println("unit:");
                System.out.println(uu.getUnit());
                System.out.println("unit user id");
                System.out.println(uu.getUnitUser());
                System.out.println(uu.getStartTime().toString());
                System.out.println(uu.getEndTime());
                System.out.println();

            }

            Facility facilityForMaintenance = facilityDAO.get(facility1.getID());

            MaintenanceStaff staff = new MaintenanceStaff();
            staff.setHoursPerWeek(34);
            staff.setPayPerHour(23);
            staff.setEmailAddress("j@examle");
            staff.setFirstName("some staff" + r.nextInt());//random makes sure these people look different in db
            staff.setLastName("staffmember" + r.nextInt());
            staff.setPhoneNumber(r.nextInt(100000));
            staff.setFullTime(false);

            staff = maintenanceStaffDAO.create(staff);
            System.out.println("Created Staff Member :"+ staff.getFirstName());
            System.out.println("ID " + staff.getID());


            staff.setPayPerHour(45);
            staff = maintenanceStaffDAO.update(staff);
            System.out.println("And then we gave him a raise to 45 dollars ");


            Date d4 = new Date(new DateTime(2014,6,3,3,3).toDate().getTime());
            Date d5 = new Date(new DateTime(2015,6,3,3,3).toDate().getTime());
            Date foreverFromNow = new Date(new DateTime(2050,6,3,3,3).toDate().getTime());


            System.out.println("Facility "+ facilityForMaintenance.getName());

            System.out.println("Adding maintence requests for facility:" + facilityForMaintenance.getName()+
                    "id:"+facilityForMaintenance.getID());

            for(Unit unit:facilityForMaintenance.getUnits())
            {

                facilityMaintenanceService.makeFacilityMaintRequest(unit.getId(),"I need stuff Fixed");
                facilityMaintenanceService.makeFacilityMaintRequest(unit.getId(),"I need more stuff Fixed ");

            }
            List<MaintenanceRequest> requests = facilityMaintenanceService.listMaintenanceRequests(facilityForMaintenance.getID());


            for(MaintenanceRequest request:requests)
            {
                request = facilityMaintenanceService.scheduleMaintenance(request.getID(),
                        staff.getID(),r.nextInt(6),d4);
            }

            System.out.println("Facility "+ facilityForMaintenance.getName());
            System.out.println("Maintenance Cost: " + facilityMaintenanceService.calcMaintenanceCostForFacility(facilityForMaintenance.getID()));
            System.out.println("Problem Rate: " + facilityMaintenanceService.calcProblemRateForFacility(facilityForMaintenance.getID()));


            System.out.println("add inspections to facility:" + facility1.getName());

            inspectionService.addInspection(facility1.getID(), staff.getID(), d1);
            inspectionService.addInspection(facility1.getID(), staff.getID(), d2);
            inspectionService.addInspection(facility1.getID(), staff.getID(), d3);
            inspectionService.addInspection(facility1.getID(), staff.getID(), d4);

            System.out.println("Inspections Scheduled for"+ facility1.getID());

            List<Inspection> inspections = inspectionService.getInspectionForFacility(facility1.getID());

            for(Inspection inspection:inspections)
            {
                System.out.println("Inspection ID:" + inspection.getID());
                System.out.println("Staff Member Assign ID"+inspection.getInspectingStaff().getID());
                System.out.println("Staff Member Name"+inspection.getInspectingStaff().getFirstName());
                System.out.println("Date :" +inspection.getInspectionDate());


            }








            System.out.print("Delete everything that was created");

            //should be encapsalated in a service later
            maintenanceRequestDAO.delete(staff.getID());
            userDAO.Delete(user1.getID());
            userDAO.Delete(user2.getID());
            userDAO.Delete(user3.getID());


            ////

            Facility testDelete = facilityService.getFacilityInformation(facility1.getID());

            List<Unit> unitsfordelte = testDelete.getUnits();
            for(Unit u: unitsfordelte)
            {
                facilityService.RemoveUnit(u);
            }//tests deletion of individual units.


            facilityService.removeFacility(facility);//delete facility and cascades all units and usages
            facilityService.removeFacility(facility1);
            facilityService.removeFacility(facility2);
            facilityService.removeFacility(facility3);
            facilityService.removeFacility(facility);




        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        
        
    }


    public static void printFacility(Facility facility,HttpServletResponse resp) throws IOException {
        System.out.print("Building Num: " + facility.getBuildingNumber()+"\n");
        System.out.print("Facility ID: " + facility.getID()+"\n");
        System.out.print("Facility Capacity: " + facility.getTotalCapacity()+"\n");
        for(Unit unit:facility.getUnits()){
            System.out.print("Unit Capacity: " + unit.getCapacity()+"\n");
            System.out.print("Unit ID: " + unit.getId()+"\n");
            System.out.print("Room Number: " + unit.getUnitNumber()+"\n");
            for(UnitUsage usage:unit.getUsages()){
                System.out.print("UsageID: " + usage.getId()+"\n");
                System.out.print("Start Time: " + usage.getStartTime()+"\n");
                System.out.print("End Time: " + usage.getEndTime()+"\n");
                System.out.print("UsageID: " + usage.getId()+"\n");
                System.out.print("Usage User ID: " + usage.getUnitUser().getID()+"\n");
            }
            for(UnitUser user:unit.getUsers()){
                System.out.print("Unit user comp: " + user.getCompanyName()+"\n");
                System.out.print("Unit user cred card: " + user.getCreditCard()+"\n");
                System.out.print("Unit user email: " + user.getEmailAddress()+"\n");
                System.out.print("Unit user first: " + user.getFirstName()+"\n");
                System.out.print("Unit user last: " + user.getLastName()+"\n");
                System.out.print("Unit user id: " + user.getID()+"\n");
                System.out.print("Unit user phone: " + user.getPhoneNumber()+"\n");
            }
        }
    }

    public static void printAllFacilities(List<Facility> facilities) throws IOException {
        for(Facility facility:facilities){
            System.out.print("Building Num: " + facility.getBuildingNumber()+"\n");
            System.out.print("Facility ID: " + facility.getID()+"\n");
            System.out.print("Facility Capacity: " + facility.getTotalCapacity()+"\n");
            for(Unit unit:facility.getUnits()){
                System.out.print("Unit Capacity: " + unit.getCapacity()+"\n");
                System.out.print("Unit ID: " + unit.getId()+"\n");
                System.out.print("Room Number: " + unit.getUnitNumber()+"\n");


            }

        }
    }
}