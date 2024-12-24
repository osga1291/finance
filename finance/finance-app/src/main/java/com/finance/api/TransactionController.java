package com.finance.api;

import com.finance.dao.TransactionDao;

import redis.clients.jedis.search.SearchResult;
import io.javalin.http.Context;

public class TransactionController {
    
    public TransactionController(){
    }

    public void fetchAll(Context ctx) {
        TransactionDao dao = new TransactionDao();
        SearchResult result = dao.getAll();
        ctx.json(result.getDocuments());
    }

    public void getByDate(Context ctx){
        TransactionDao dao = new TransactionDao();
        String startDate = ctx.queryParam("startDate");
        String endDate = ctx.queryParam("endDate");
        SearchResult result = dao.getByDate(startDate, endDate);
        ctx.json(result.getDocuments());
    }

}
    

