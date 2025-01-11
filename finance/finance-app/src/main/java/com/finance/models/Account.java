package com.finance.models;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import redis.clients.jedis.search.Document;

import java.util.HashMap;

public class Account implements Model{

    public String AccountNum;
    public String Description;
    public AccountClass AccountClass;
    public BigDecimal Balance;
    public String Type;
    public String Institution;
    public String id;
    private final String PREFIX = "accounts";

    public Account(String accountNum, String description, String accountClass, String amount, String institution, String type){
        this.AccountNum = accountNum;
        this.Description = description;
        this.AccountClass = accountClass == "ASSET" ? AccountClass.ASSET : AccountClass.LIABLILITY;
        this.Balance = amountFormat(amount);
        this.Institution = institution;
        this.Type = type;
        UUID uuid = UUID.randomUUID();
        this.id = String.format("%s:%s", PREFIX, uuid.toString());
    }

    public Account(String id, String accountNum, String description, String accountClass, String amount, String institution, String type){
        this.AccountNum = accountNum;
        this.Description = description;
        this.AccountClass = accountClass == "ASSET" ? AccountClass.ASSET : AccountClass.LIABLILITY;
        this.Balance = amountFormat(amount);
        this.Institution = institution;
        this.Type = type;
        this.id = id;
    }

    public String getId(){
        return id;
    }

    private static BigDecimal amountFormat(String amount){
        String remove$ = amount.replace("$", "");
        String formattedString = remove$.replace(",", "");
        return new BigDecimal(formattedString);
    }

    public Map<String, Object> saveMap(){
        Map<String, Object> fields = new HashMap<>();
        fields.put("accountNum", AccountNum);
        fields.put("description", Description);
        fields.put("accountClass", AccountClass.getValue());
        fields.put("balance", Balance.toString());
        fields.put("institution", Institution);
        fields.put("type", Type);
        return fields;
    }

    public static Account fromDoc(Document doc){
        Account a = new Account(doc.getId(),String.valueOf(doc.get("accountNum")), String.valueOf(doc.get("description")), String.valueOf(doc.get("accountClass")), String.valueOf(doc.get("balance")), String.valueOf(doc.get("institution")), String.valueOf(doc.get("type")));
        return a;
    }
}
