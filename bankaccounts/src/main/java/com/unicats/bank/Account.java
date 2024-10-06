package com.unicats.bank;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Account {

  public String accountId;
  public int balance;
  public ArrayList<Transaction> txns = new ArrayList<>();

  public Account(String accountId) {
    this.accountId = accountId;
    this.balance = 0;
  }

  public void deposit(int amount) {
    this.balance += amount;
    this.txns.add(new Transaction(this.accountId, amount, TransactionType.DEPOSIT));
  }
  public void withdraw(int amount) {
    this.balance -= amount;
    this.txns.add(new Transaction(this.accountId, amount, TransactionType.WITHDRAWAL));
  }

  public Optional<Transaction> getTopWithdrawal() {
    List<Transaction> withdrawals = this.txns.stream().filter(t -> t.txnType.equals(TransactionType.WITHDRAWAL)).sorted((a, b) -> b.amount.compareTo(a.amount)).collect(Collectors.toList());
    if (withdrawals.isEmpty()) return Optional.empty();
    return Optional.of(withdrawals.get(0));
  }
}

