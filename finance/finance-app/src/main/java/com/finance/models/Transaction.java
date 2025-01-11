package com.finance.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import redis.clients.jedis.search.Document;

import com.google.gson.Gson;

public class Transaction implements Model{

    public LocalDate Date;
    public String Description;
    public BigDecimal Amount;
    public Account Account;
    public String Category;
    public String Institution;
    public String id;
    private final String PREFIX = "transactions";


    public Transaction(String date, String description, String amount, Account account){
        this.Date = dateFormat(date);
        this.Description = description;
        this.Amount = amountFormat(amount);
        this.Account = account;
        UUID uuid = UUID.randomUUID();
        this.id = String.format("%s:%s", PREFIX, uuid.toString());
    }

    public Transaction(String id, String date, String description, String amount, Account account){
        this.Date = dateFormat(date);
        this.Description = description;
        this.Amount = amountFormat(amount);
        this.Account = account;
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public void setCategory(String category){
        this.Category = category;
    }

    public String getCategory(){
        return Category;
    }

    public void setAmount(BigDecimal amount){
        this.Amount = amount;
    }

    public static Transaction fromDoc(Document doc, Account account){
        Transaction t = new Transaction(doc.getId(),String.valueOf(doc.get("date")), String.valueOf(doc.get("description")), String.valueOf(doc.get("amount")), account);
        return t;
    } 

    public static LocalDate dateFormat(String date){
        try{

            if (date.contains("-")){
                String[] DateProvided = date.split("-");
                return LocalDate.of(Integer.parseInt(DateProvided[2]), Month.of(Integer.parseInt(DateProvided[0])), Integer.parseInt(DateProvided[1]));

            }
            else{
                String[] DateProvided = date.split("/");
                return LocalDate.of(Integer.parseInt(DateProvided[2]), Month.of(Integer.parseInt(DateProvided[0])), Integer.parseInt(DateProvided[1]));
            }
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
        fields.put("accountNum", Account.AccountNum);
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
