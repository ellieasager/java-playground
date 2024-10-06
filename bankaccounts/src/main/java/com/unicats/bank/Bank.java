package com.unicats.bank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.Optional;

public class Bank {
  
  public HashMap<String, Account> accounts = new HashMap<>();

  public boolean createAccount(String accountId) {
    if (accounts.containsKey(accountId)) return false;
    accounts.put(accountId, new Account(accountId));
    return true;
  }

  public Optional<Integer> deposit(String accountId, int amount) {
    Account a = accounts.get(accountId);
    if (a == null) Optional.empty();
    a.deposit(amount);
    accounts.put(accountId, a);
    return Optional.of(a.balance);
  }

  public Optional<Integer> transfer(String sourceAccountId, String targetAccountId, int amount) {
    Account sourceAccount = accounts.get(sourceAccountId);
    Account targetAccount = accounts.get(targetAccountId);
    if (sourceAccount == null || targetAccount == null || targetAccountId.equals(sourceAccountId)) return Optional.empty();
    if (sourceAccount.balance < amount) return Optional.empty();

    sourceAccount.withdraw(amount);
    targetAccount.deposit(amount);
    accounts.put(sourceAccountId, sourceAccount);
    accounts.put(targetAccountId, targetAccount);
    return Optional.of(sourceAccount.balance);
  }

  public List<String> topSpenders(int n) {

    ArrayList<String> result = new ArrayList<>();
    ArrayList<Transaction> topWithdrawals = new ArrayList<>();
    
    for (Account a: accounts.values()) {
      Optional<Transaction> topTxn = a.getTopWithdrawal();
      // System.out.println(a.accountId + ":" + topTxn);
      if (topTxn.isPresent()) topWithdrawals.add(topTxn.get());
    }

    // System.out.println(topWithdrawals);
    Collections.sort(topWithdrawals, Comparator.comparing(Transaction::getAmount).reversed().thenComparing(Transaction::getAccountId));

    int numberOfEntries = n > topWithdrawals.size() ? topWithdrawals.size() : n;
    for (Transaction t: topWithdrawals.subList(0, numberOfEntries)) {
      result.add(t.accountId + "(" + t.getAmount().intValue() + ")");
    }
    return result;
  }

  private void helper() {

    Iterator<Map.Entry<String, Account>> it = accounts.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry<String, Account> entry = it.next();
      String key = (String)entry.getKey();
      Account value = (Account)entry.getValue();
    }
    for (Map.Entry<String, Account> a : accounts.entrySet()) {}
    for (Account a: accounts.values()) {}

    Collections.sort(topWithdrawals, 
      Comparator.comparing(Transaction::getAmount)
      .reversed()
      .thenComparing(Transaction::getAccountId)
    );

    List<Transaction> withdrawals = this.txns
    .stream()
    .filter(t -> t.txnType.equals(TransactionType.WITHDRAWAL))
    .sorted((a, b) -> b.amount.compareTo(a.amount))
    .collect(Collectors.toList());
  }

}
