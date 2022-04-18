package com.banking.myproject;

import java.time.LocalDate;

public class Account {
    private String accountNo, accountType, gender, address, name, nationality, occupation, phoneNum;
    private String accountPin;
    private LocalDate dateOfBirth;
    Account() {}

    public Account(String accountNo, String accountType, String gender, String address, String name, String nationality,
            String occupation, String phoneNum, String accountPin, LocalDate dateOfBirth) {
        this.setAccountNo(accountNo);
        this.setAccountPin(accountPin);
        this.setName(name);
        this.setGender(gender);
        this.setAddress(address);
        this.setNationality(nationality);
        this.setOccupation(occupation);
        this.setPhoneNum(phoneNum);
        this.setAccountType(accountType);
        this.setDateOfBirth(dateOfBirth);
    }

    public void setAccountNo(String accountNo) {this.accountNo = accountNo;}
    public void setAccountType(String accountType) {this.accountType = accountType;}
    public void setName(String name) {this.name = name;}
    public void setGender(String gender) {this.gender = gender;}
    public void setAddress(String address) {this.address = address;}
    public void setNationality(String nationality) {this.nationality = nationality;}
    public void setOccupation(String occupation) {this.occupation = occupation;}
    public void setPhoneNum(String phoneNum) {this.phoneNum = phoneNum;}
    public void setAccountPin(String accountPin) {this.accountPin = accountPin;}
    public void setDateOfBirth(LocalDate dateOfBirth) {this.dateOfBirth = dateOfBirth;}

    String getAccountNo() {return this.accountNo;}
    String getAccountType() {return this.accountType;}
    String getName() {return this.name;}
    String getGender() {return this.gender;}
    String getAddress() {return this.address;}
    String getNationality() {return this.nationality;}
    String getOccupation() {return this.occupation;}
    String getPhoneNum() {return this.phoneNum;}
    String getAccountPin() {return this.accountPin;}
    LocalDate getDateOfBirth() {return this.dateOfBirth;}

    @Override
    public String toString() {
        return this.getAccountNo() + "," + this.getAccountPin() + "," + this.getAccountType() + "," + this.getName() + ","
                + this.getGender() + "," + this.getOccupation() + ","  + this.getNationality() + "," + this.getAddress()
                + "," + this.getPhoneNum() + "," + this.getDateOfBirth();
    }

}
