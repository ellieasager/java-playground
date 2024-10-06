package com.unicats.bank;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        Bank b = new Bank();
        b.createAccount("account 1");
        b.createAccount("account 2");
        b.deposit("account 1", 1000);
        b.deposit("account 2", 3000);
        System.out.println(b.topSpenders(3));
        System.out.println(b.transfer("account 1", "account 2", 4000));
        System.out.println(b.transfer("account 2", "account 2", 4000));
        System.out.println(b.transfer("account 1", "account 2", 100));
        System.out.println(b.transfer("account 2", "account 1", 100));
        System.out.println(b.topSpenders(3));
    }
}
