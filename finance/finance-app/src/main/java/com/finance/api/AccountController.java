package com.finance.api;


import com.finance.dao.AccountDao;

import io.javalin.http.Context;
import redis.clients.jedis.search.SearchResult;

public class AccountController {
    
    public AccountController(){
    }

    public void fetchAll(Context ctx) {
        AccountDao dao = new AccountDao();
        String offset = ctx.queryParam("offset");
        String limit = ctx.queryParam("limit");
        SearchResult result = dao.getAll(Integer.parseInt(offset), Integer.parseInt(limit));
        ctx.json(result.getDocuments());
    }

    
}
