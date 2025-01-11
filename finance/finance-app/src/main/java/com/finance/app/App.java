package com.finance.app;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

import org.checkerframework.checker.units.qual.A;

import com.finance.dao.AccountDao;
import com.finance.dao.TransactionDao;
import com.finance.models.Transaction;
import com.finance.models.Account;
import com.finance.sheets.SheetWorker;
import com.finance.models.Model;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        SheetWorker n = new SheetWorker();
        //System.out.println(a);
        //List<Transaction> g =  n.getAll();
        TransactionDao f = new TransactionDao();
        List<Model> a = n.getTrasactions();

        //AccountDao f = new AccountDao();
        //Map<String, Account> a = n.getAccounts();
        f.createIndex();
        try{
            for (Model entry : a) {
                f.Insert(entry);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        //TransactionDao f = new TransactionDao();
        //f.createIndex();
        //try {
            //f.Insert(g.get(2));
            //f.Insert(g.get(3));
        //} catch (Exception e) {
         //   System.err.println(e);
        //}
        
    }
}
