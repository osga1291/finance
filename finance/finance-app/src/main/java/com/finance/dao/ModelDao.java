package com.finance.dao;

import java.util.Map;
import java.util.UUID;
import com.finance.models.Model;

import redis.clients.jedis.search.IndexDefinition;
import redis.clients.jedis.search.IndexOptions;
import redis.clients.jedis.search.Query;
import redis.clients.jedis.search.Schema;
import redis.clients.jedis.search.SearchResult;


public class ModelDao implements Dao{

    protected DbWorker dbworker;
    public String INDEX_NAME = null;
    public String PREFIX = null;

    public ModelDao(){
        dbworker = DbWorker.getInstance();
    }
    
    public void createIndex(Schema schema){


        IndexDefinition def = new IndexDefinition()
                .setPrefixes(new String[]{PREFIX});

        dbworker.client.ftCreate(INDEX_NAME, IndexOptions.defaultOptions().setDefinition(def), schema);
    }

    public SearchResult getAll(Integer offset, Integer limit){
        Query query = new Query("*").setSortBy("epoch", false).limit(offset, limit);
        SearchResult s = dbworker.client.ftSearch(INDEX_NAME, query);
        return s;

    }

    public SearchResult getByField(String key, String value, Integer offset, Integer limit){
        Query query = new Query(String.format("@%s:%s", key, value)).setSortBy("epoch", false).limit(offset, limit);
        SearchResult s = dbworker.client.ftSearch(INDEX_NAME, query);
        return s;
    }

    public void Insert(Model model) throws Exception{
        Map<String, Object> modelMap = model.saveMap();
        dbworker.client.hsetObject(model.getId(), modelMap);
    }
}
