package com.finance.app;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import com.finance.dao.TransactionDao;
import com.finance.models.Transaction;
import com.finance.sheets.SheetWorker;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        SheetWorker n = new SheetWorker();
        List<Transaction> g =  n.getAll();
        TransactionDao f = new TransactionDao();
        //f.createIndex();
        try {
            f.Insert(g.get(2));
            f.Insert(g.get(3));
        } catch (Exception e) {
            System.err.println(e);
        }
        
    }
}
