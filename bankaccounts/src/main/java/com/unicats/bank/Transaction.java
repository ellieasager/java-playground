package com.unicats.bank;

enum TransactionType {
  DEPOSIT,
  WITHDRAWAL
}

public class Transaction {
  public String accountId;
  public TransactionType txnType;
  public Integer amount;

  public Transaction(String accountId, int amount, TransactionType txnType) {
    this.accountId = accountId;
    this.amount = amount;
    this.txnType = txnType;
  }

  public Integer getAmount() {
    return this.amount;
  }
  public String getAccountId() {
    return this.accountId;
  }
}
