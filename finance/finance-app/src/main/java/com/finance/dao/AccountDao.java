package com.finance.dao;

import redis.clients.jedis.search.Schema;

public class AccountDao extends ModelDao{

    public AccountDao(){
        super();
        INDEX_NAME = "account-index";
        PREFIX = "accounts:";
        
    }
    
    public void createIndex(){
        Schema sc = new Schema()
            .addTextField("description", 1.0)
            .addTextField("account", 1.0)
            .addTextField("category", 1.0)
            .addTagField("amount")
            .addTagField("date")
            .addTextField("institution", 1.0)
            .addSortableNumericField("epoch")
            .addTextField("accountNum", 1.0);

            super.createIndex(sc);
    }
    
}
