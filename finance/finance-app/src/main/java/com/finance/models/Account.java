package com.finance.models;

import java.math.BigDecimal;

public class Account {

    public String AccountNum;
    public String Description;
    public AccountClass AccountClass;
    public BigDecimal Balance;
    public String Type;
    public String Institution;

    public Account(String accountNum, String description, String accountClass, String amount, String institution, String type){
        this.AccountNum = accountNum;
        this.Description = description;
        this.AccountClass = accountClass == "ASSET" ? AccountClass.ASSET : AccountClass.LIABLILITY;
        this.Balance = amountFormat(amount);
        this.Institution = institution;
        this.Type = type;
    }

    private static BigDecimal amountFormat(String amount){
        String remove$ = amount.replace("$", "");
        String formattedString = remove$.replace(",", "");
        return new BigDecimal(formattedString);
    }
}
