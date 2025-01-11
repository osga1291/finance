package com.finance.dao;

import java.time.LocalDate;
import java.time.ZoneOffset;

import com.finance.models.Transaction;

import redis.clients.jedis.search.Query;
import redis.clients.jedis.search.Schema;
import redis.clients.jedis.search.SearchResult;
import redis.clients.jedis.search.Document;

public class TransactionDao extends ModelDao{

    public TransactionDao(){
        super();
        INDEX_NAME = "transaction-index";
        PREFIX = "transactions:";
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

    public SearchResult getByDate(String startDate, String endDate, Integer offset, Integer limit){
        long FormattedStartDate = Transaction.dateFormat(startDate).atStartOfDay().toEpochSecond(ZoneOffset.UTC);
        long FormattedEndDate = Transaction.dateFormat(endDate).atStartOfDay().toEpochSecond(ZoneOffset.UTC);
        Query query = new Query(String.format("@epoch:[%s %s]", FormattedStartDate, FormattedEndDate)).setSortBy("epoch", false).limit(offset, limit);
        SearchResult s = dbworker.client.ftSearch(INDEX_NAME, query);
        return s;
    }
}
