package com.finance.dao;

import redis.clients.jedis.UnifiedJedis;

public class DbWorker implements AutoCloseable{

    private static DbWorker DbWorker = null;
    public UnifiedJedis client;


    private DbWorker(){
        client = new UnifiedJedis("redis://localhost:6379");
    }

    @Override
    public void close(){
        client.close();
    }

    public static DbWorker getInstance(){
        if (DbWorker == null){
            DbWorker = new DbWorker();
        }
        return DbWorker;


    }

    
}
