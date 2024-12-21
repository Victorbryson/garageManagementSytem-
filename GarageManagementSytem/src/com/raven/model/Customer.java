package com.raven.model;
import javax.swing.Icon;

/**
 *
 * @author matot
 */
public class Customer {
    
     private Icon icon;
    private int customerId;
    private String firstName;
    private String lastName;
    private String identityNumber;
    private String phoneNumber;
    private String email;
    private String address;
    private String carModel;
    private String carBrand;
    private String carPlate;
    
    
     public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    
     public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    
      public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String car_brand) {
        this.carBrand = carBrand;
    }
    
       public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }
    
       public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }
    

    
    
public Customer(int customerId, String firstName, String lastName, String identificationNumber,
                   String phoneNumber, String email, String address, String carBrand,
                   String carModel, String carPlate) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.identityNumber = identificationNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.carPlate = carPlate;
    }
 
//public Object[] toRowTable(EventAction event) {
//        return new Object[]{
//            new ModelProfile(icon, firstName),
//            lastName,
//            email,
//            phoneNumber,
//            car_brand,
//            car_model,
//            new ModelAction(this, event)
//        };
//    }
 
    
}
