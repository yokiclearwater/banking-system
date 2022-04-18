package com.banking.myproject;

public class Bank {
    private String accNo;
    private String name;
    private double balance;

    Bank() {
        this.setBalance(0);
    }

    Bank(String accNo, String name, double balance) {
        this.setAccNo(accNo);
        this.setName(name);
        this.setBalance(balance);
    }

    void depositAmount(double cashAmount) {
        this.balance += cashAmount;
    }

    void setName(String name) {this.name = name;}
    void setAccNo(String accNo) {this.accNo = accNo;}
    void setBalance(double balance) {this.balance = balance;}

    String getName() {return this.name;}
    String getAccNo() {return  this.accNo;}
    double getBalance() {return this.balance;}
}
