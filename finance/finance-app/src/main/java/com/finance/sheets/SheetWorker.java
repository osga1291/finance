package com.finance.sheets;

import com.finance.models.Account;
import com.finance.models.Model;
import com.finance.models.Transaction;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SheetWorker {
  private static final String APPLICATION_NAME = "finance app";
  final String spreadsheetId = "1Zfgi53pgBJxu9K8NXWl7uoE1N6KQ0AQw_RTD5kkgils";
  public Sheets sheetsService;

    private GoogleAuthorizeUtil auth;
    public SheetWorker(){
      auth = new GoogleAuthorizeUtil();
      try {
        sheetsService = getSheetsService();
      } catch (IOException e) {
        System.err.println(e); 
      } catch (GeneralSecurityException e){
        System.err.println(e);
      }
    }
    private Sheets getSheetsService() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return new Sheets.Builder(HTTP_TRANSPORT, auth.JSON_FACTORY, auth.getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private List<List<Object>> GetValues(String range) throws Exception{
      try{
        
        ValueRange response = sheetsService.spreadsheets().values()
          .get(spreadsheetId, range)
          .execute();
        
        return response.getValues();
    
      }

      catch(IOException e){
        System.err.println(e);
      }

      throw new Exception("Data Not Found");
      
    }
    
    public List<Model> getTrasactions(){
      Map<String, Account> accounts = getAccounts();
      ArrayList<Model> transactions = new ArrayList<>();
      try{
        List<List<Object>> resp = GetValues("Transactions!A2:H");

        for (List<Object> entry: resp){
          transactions.add(new Transaction(String.valueOf(entry.get(1)), String.valueOf(entry.get(2)),String.valueOf(entry.get(4)), accounts.get(String.valueOf(entry.get(6)))));

        }
   
      } catch(Exception e){
        System.out.println(e);
      }

      return transactions;
   
    }

    public Map<String, Account> getAccounts(){
      ArrayList<Model> accounts = new ArrayList<>();
      Map<String, Account> accountMap = new HashMap<>();
      try{
        List<List<Object>> resp = GetValues("Accounts!A2:P");

        for (List<Object> entry: resp){
          Account account = new Account(String.valueOf(entry.get(10)), String.valueOf(entry.get(9)), String.valueOf(entry.get(1)), String.valueOf(entry.get(8)), String.valueOf(entry.get(11)), String.valueOf(entry.get(12)));
          accounts.add(account);
          accountMap.put(String.valueOf(entry.get(10)), account);
        }
      } catch(Exception e){
        System.out.println(e);
      }

      return accountMap;
   
    }
      
  }
  