package com.finance.dao;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.UUID;

import com.finance.models.Transaction;
import redis.clients.jedis.search.IndexDefinition;
import redis.clients.jedis.search.IndexOptions;
import redis.clients.jedis.search.Query;
import redis.clients.jedis.search.Schema;
import redis.clients.jedis.search.SearchResult;

public class TransactionDao {

    private DbWorker dbworker;


    public TransactionDao(){
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
                .setPrefixes(new String[]{"transactions:"});

        dbworker.client.ftCreate("transaction-index", IndexOptions.defaultOptions().setDefinition(def), sc);
    }

    public void Insert(Transaction transaction) throws Exception{
        UUID uuid = UUID.randomUUID();
        Map<String, Object> transactionMap = transaction.saveMap();
        dbworker.client.hsetObject(String.format("transactions:%s", uuid.toString()), transactionMap);
    }

    public SearchResult getAll(){
        Query query = new Query("*");
        SearchResult s = dbworker.client.ftSearch("transaction-index", query);
        return s;

    }

    public SearchResult getByField(String key, String value){
        Query query = new Query(String.format("@%s:%s", key, value));
        SearchResult s = dbworker.client.ftSearch("transaction-index", query);
        return s;
    }

    public SearchResult getByDate(String startDate, String endDate){
        long FormattedStartDate = LocalDate.parse(startDate).atStartOfDay().toEpochSecond(ZoneOffset.UTC);
        long FormattedEndDate = LocalDate.parse(endDate).atStartOfDay().toEpochSecond(ZoneOffset.UTC);
        Query query = new Query(String.format("@epoch:[%s %s]", FormattedStartDate, FormattedEndDate));
        SearchResult s = dbworker.client.ftSearch("transaction-index", query);

        return s;

    }
}
