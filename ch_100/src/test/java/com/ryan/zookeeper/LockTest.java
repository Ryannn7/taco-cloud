package com.ryan.zookeeper;

/**
 * Created with IDEA
 * author:Liuzongshuai
 * Date:2023/3/19
 * Time:18:28
 */
public class LockTest {

    public static void main(String[] args) {
        Ticket12306 ticket12306 = new Ticket12306();

        Thread t1 = new Thread(ticket12306, "携程");
        Thread t2 = new Thread(ticket12306, "飞猪");
        t1.start();
        t2.start();

    }
}
