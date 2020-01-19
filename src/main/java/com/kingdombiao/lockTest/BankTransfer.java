package com.kingdombiao.lockTest;

import lombok.experimental.Helper;

import java.util.concurrent.TimeUnit;

/**
 * 描述:
 * 模拟银行转账
 *
 * @author biao
 * @create 2020-01-19 14:20
 */
public class BankTransfer implements Runnable {

    private int flag;
    private final static Account accountA = new Account(200);
    private final static Account accountB = new Account(300);
    private final static Object lock = new Object();


    public static void main(String[] args) throws InterruptedException {
        BankTransfer bankTransferA = new BankTransfer();
        BankTransfer bankTransferB = new BankTransfer();
        bankTransferA.flag = 1;
        bankTransferB.flag = 2;

        Thread t1 = new Thread(bankTransferA);
        Thread t2 = new Thread(bankTransferB);

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("a的余额" + accountA.getBalance());
        System.out.println("b的余额" + accountB.getBalance());


    }


    @Override
    public void run() {

        if (flag == 1) {
            //A转B
            transferMoney(accountA, accountB, 200);
        }

        if (flag == 2) {
            //B转A
            transferMoney(accountB, accountA, 600);
        }

    }

    /**
     * 转账方法
     *
     * @param from   转出人
     * @param from   收款人
     * @param amount 转账金额
     */
    private void transferMoney(Account from, Account to, int amount) {

        class Helper {
            public void transfer() {
                if (from.getBalance() - amount < 0) {
                    System.out.println("余额不足，转账失败。");
                    return;
                }
                from.setBalance(from.getBalance() - amount);
                to.setBalance(to.getBalance() + amount);
                System.out.println("成功转账" + amount + "元");
            }
        }

        //使用类的hash值来帮助排序
        int fromHash = System.identityHashCode(from);
        int toHash = System.identityHashCode(to);
        if (fromHash < toHash) {
            synchronized (from) {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (to) {
                    new Helper().transfer();
                }
            }
        } else if (toHash < fromHash) {
            synchronized (to) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (from) {
                    new Helper().transfer();
                }
            }
        } else {
            //发生hash碰撞时，可以增加锁来进行控制，类似“加时赛”
            synchronized (lock) {
                synchronized (from) {
                    synchronized (to) {
                        new Helper().transfer();
                    }
                }
            }
        }
    }
}

/**
 * 银行账户
 */
class Account {
    private int balance;

    public Account(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
