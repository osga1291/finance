package com.finance.dao;

import redis.clients.jedis.search.IndexDefinition;
import redis.clients.jedis.search.IndexOptions;
import redis.clients.jedis.search.Query;
import redis.clients.jedis.search.Schema;
import redis.clients.jedis.search.SearchResult;

public class AccountDao {

    private DbWorker dbworker;

    public AccountDao(){
        dbworker = DbWorker.getInstance();
    }

        public void createIndex(){
        
        Schema sc = new Schema()
            .addTextField("description", 1.0)
            .addTextField("account", 1.0)
            .addTextField("category", 1.0)
            .addTagField("amount")
            .addTagField("date")
            .addTextField("institution", 1.0)
            .addSortableNumericField("epoch");

        IndexDefinition def = new IndexDefinition()
                .setPrefixes(new String[]{"account:"});

        dbworker.client.ftCreate("account-index", IndexOptions.defaultOptions().setDefinition(def), sc);
    }

    public SearchResult getAll(){
        Query query = new Query("*");
        SearchResult s = dbworker.client.ftSearch("account-index", query);
        return s;

    }
    
}
