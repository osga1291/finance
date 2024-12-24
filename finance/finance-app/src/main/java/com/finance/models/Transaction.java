package com.finance.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class Transaction {

    public LocalDate Date;
    public String Description;
    public BigDecimal Amount;
    public Account Account;
    public String Category;
    public String Institution;


    public Transaction(String date, String description, String amount, Account account){
        this.Date = dateFormat(date);
        this.Description = description;
        this.Amount = amountFormat(amount);
        this.Account = account;
    }

    private LocalDate dateFormat(String date){
        try{
            String[] DateProvided = date.split("/");
            return LocalDate.of(Integer.parseInt(DateProvided[2]), Month.of(Integer.parseInt(DateProvided[0])), Integer.parseInt(DateProvided[1]));
        } catch (Exception e) {
            throw e;
        }
        
    }

    private static BigDecimal amountFormat(String amount){
        String remove$ = amount.replace("$", "");
        String formattedString = remove$.replace(",", "");
        return new BigDecimal(formattedString);

    }

    public Map<String, Object> saveMap(){
        
        Map<String, Object> fields = new HashMap<>();
        LocalDateTime DateTime = Date.atStartOfDay();
        fields.put("date", Date.toString());
        fields.put("description", Description);
        fields.put("amount", Amount.toString());
        fields.put("account", Account.Description);
        fields.put("institution", Account.Institution);
        fields.put("category", Category == null ? "NULL" : Category);
        fields.put("epoch",  DateTime.toEpochSecond(ZoneOffset.UTC));
        return fields;
    }

    public String convertJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    
}
