package Main.Entities.usage;

import Main.Entities.Person;

public class UnitUser extends Person {


    private String creditCard;

	private String companyName;

    private int unitUserId;

	public UnitUser() {

	}


    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getUnitUserId(){
        return unitUserId;
    }

}
