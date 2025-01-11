package com.finance.dao;

import com.finance.models.Model;

import redis.clients.jedis.search.Schema;
import redis.clients.jedis.search.SearchResult;

public interface Dao{

    public SearchResult getAll(Integer offset, Integer limit);

    public SearchResult getByField(String key, String value, Integer offset, Integer limit);

    public void createIndex(Schema schema);

    public void Insert(Model model) throws Exception;
    
}
