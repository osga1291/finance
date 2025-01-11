package com.finance.api;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.finance.dao.AccountDao;
import com.finance.dao.TransactionDao;
import com.finance.models.Account;
import com.finance.models.Transaction;
import com.google.gson.GsonBuilder;
import com.google.api.services.sheets.v4.Sheets.Spreadsheets.Values.Update;
import com.google.gson.Gson;

import redis.clients.jedis.search.Document;
import redis.clients.jedis.search.SearchResult;
import io.javalin.http.Context;
import com.finance.api.Requests.UpdateCategoryRequest;

import java.util.HashMap;
import java.util.List;
import java.math.BigDecimal;

public class TransactionController {
    
    public TransactionController(){
    }

    public void fetchAll(Context ctx) {
        TransactionDao dao = new TransactionDao();
        String offset = ctx.queryParam("offset");
        String limit = ctx.queryParam("limit");
        SearchResult result = dao.getAll(Integer.parseInt(offset), Integer.parseInt(limit));
        ctx.json(result.getDocuments());
    }

    public void getByDate(Context ctx){
        TransactionDao dao = new TransactionDao();
        String startDate = ctx.queryParam("startDate");
        String endDate = ctx.queryParam("endDate");
        String offset = ctx.queryParam("offset");
        String limit = ctx.queryParam("limit");
        SearchResult result = dao.getByDate(startDate, endDate, Integer.parseInt(offset), Integer.parseInt(limit));
        ctx.json(result.getDocuments());
    }

    public void getByField(Context ctx){
        TransactionDao dao = new TransactionDao();
        String key = ctx.queryParam("key");
        String value = ctx.queryParam("value");
        String offset = ctx.queryParam("offset");
        String limit = ctx.queryParam("limit");
        SearchResult result = dao.getByField(key, value, Integer.parseInt(offset), Integer.parseInt(limit));
        ctx.json(result.getDocuments());
    }

    public void getTimeline(Context ctx) {
        TransactionDao dao = new TransactionDao();
        String chunk = ctx.queryParam("timeChunk");
        String offset = ctx.queryParam("offset");
        String limit = ctx.queryParam("limit");
        if (chunk == null){
            ctx.status(400).result("Missing timeChunk parameter");
            return;
        }
        AccountDao accountDao = new AccountDao();
        SearchResult accounts = accountDao.getAll(0, 10);
        HashMap<String, BigDecimal> accountMap = new HashMap<String, BigDecimal>();
        for (Document account : accounts.getDocuments()){
            accountMap.put(String.valueOf(account.get("accountNum")), new BigDecimal(String.valueOf(account.get("balance"))));
        }

        LocalDate today = LocalDate.now();
        LocalDate startDate;
        switch (chunk) {
            case "30d":
                startDate = today.minusDays(30);
                break;
            case "90d":
                startDate = today.minusDays(90);
                break;
            case "365d":
                startDate = today.minusDays(365);
                break;
            default:
                ctx.status(400).result("Invalid timeChunk parameter");
                return;
        }
        String todayStr = today.format(DateTimeFormatter.ofPattern("MM/dd/YYYY"));
        String startDateStr = startDate.format(DateTimeFormatter.ofPattern("MM/dd/YYYY"));
        SearchResult result = dao.getByDate(startDateStr, todayStr, Integer.parseInt(offset), Integer.parseInt(limit));
        List<Document> resultDoc = result.getDocuments();

        HashMap<String, HashMap<String, BigDecimal>> accountTimeline = new HashMap<String, HashMap<String, BigDecimal>>();
        for (Document doc : resultDoc){
                if (accountTimeline.containsKey(String.valueOf(doc.get("accountNum")))){
                    HashMap<String, BigDecimal> currAccount = accountTimeline.get(String.valueOf(doc.get("accountNum")));  
                    if (currAccount.containsKey(String.valueOf(doc.get("date")))){
                        BigDecimal total = currAccount.get(String.valueOf(doc.get("date")));
                        total = total.subtract(new BigDecimal(String.valueOf(doc.get("amount"))));
                        currAccount.put(String.valueOf(doc.get("date")), total);
                        accountMap.put(String.valueOf(doc.get("accountNum")), total);
                        }
                    else {
                        currAccount.put(String.valueOf(doc.get("date")), accountMap.get(String.valueOf(doc.get("accountNum"))));
                    }
                        
                } else {
                    accountTimeline.put(String.valueOf(doc.get("accountNum")), new HashMap<String, BigDecimal>());
            }
        }
        Gson gson = new GsonBuilder().create();
        ctx.json(gson.toJson(accountTimeline));
    }

    public void updateCategory(Context ctx){
        TransactionDao dao = new TransactionDao();
        AccountDao accountDao = new AccountDao();
        UpdateCategoryRequest request = ctx.bodyAsClass(UpdateCategoryRequest.class);
        String description = request.getDescription();
        String category = request.getCategory();
        SearchResult s = dao.getByField("description", description, 0, 100);
        if (s.getTotalResults() == 0){
            return;
        }
        
       for (Document doc : s.getDocuments()){
            SearchResult account = accountDao.getByField("accountNum",doc.getString("accountNum"),0,1);
            Transaction t = Transaction.fromDoc(doc, Account.fromDoc(account.getDocuments().get(0)));
            t.setCategory(category);
    }
        
    }
}
    

