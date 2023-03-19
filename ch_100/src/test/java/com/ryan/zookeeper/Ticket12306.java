package com.ryan.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

/**
 * Created with IDEA
 * author:Liuzongshuai
 * Date:2023/3/19
 * Time:18:29
 * 12306卖票服务
 */
public class Ticket12306 implements Runnable{

    private InterProcessMutex lock;



    public Ticket12306(){

        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);
        // client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", 1000, 1000, retryPolicy);

        //方法2：使用build链式编程
        CuratorFramework   client= CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .sessionTimeoutMs(60*1000)
                .connectionTimeoutMs(15*1000)
                .retryPolicy(retryPolicy)
                .build();
        client.start();
        lock=new InterProcessMutex(client, "/lock");


    }

    private int tickQty=10;


    @Override
    public void run() {
        while (true){
            try {
                lock.acquire(2, TimeUnit.SECONDS);
                if(tickQty>0){
                    tickQty--;
                    System.out.println("剩余票数：" +tickQty+ Thread.currentThread().getName()+"购票成功");
                    Thread.sleep(100);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }finally {
                try {
                    lock.release();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
