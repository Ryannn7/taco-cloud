package com.ryan.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IDEA
 * author:Liuzongshuai
 * Date:2023/3/19
 * Time:14:08
 */


public class ZookeeperTest {

    private CuratorFramework client;

    public static Logger log=LogManager.getLogger(ZookeeperTest.class);

    /**
     * 建立链接
     */
    @Before
    public void testConnect(){

        //方法1：使用构造函数
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);
      // client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", 1000, 1000, retryPolicy);

        //方法2：使用build链式编程
       client= CuratorFrameworkFactory.builder()
        .connectString("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183")
                .sessionTimeoutMs(60*1000)
                        .connectionTimeoutMs(15*1000)
                                .retryPolicy(retryPolicy)
                                        .namespace("itheima")
                                                .build();
        client.start();
    }


    //=====================第一部分：java创建节点===========================================

    /**
     * 创建节点：create 持久 临时 顺序 数据
     *      * 1. 基本创建 ：create().forPath("")
     *      * 2. 创建节点 带有数据:create().forPath("",data)
     *      * 3. 设置节点的类型：create().withMode().forPath("",data)
     *      * 4. 创建多级节点  /app1/p1 ：create().creatingParentsIfNeeded().forPath("",data)
     */
    @Test
    public void testCreate() throws Exception {
        //默认创建的数据是当前客户端的IP
        /*String s = client.create().forPath("/app1");
        log.info(s);*/
        //创建带有数据的节点
       /* String path = client.create().forPath("/app2", "hehe".getBytes());
        System.out.println(path);*/

        //设置节点的类型：create().withMode().forPath("",data)
      /*  String app3 = client.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath("/app3");
        log.info(app3);*/

       /* String app4 = client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/app4");
        log.info(app4);*/

      /*  String app5 = client.create().withMode(CreateMode.EPHEMERAL).forPath("/app5");
        System.out.printf(app5);
        while (true){

        }*/
        String path = client.create().creatingParentsIfNeeded().forPath("/app6/p_1");
        System.out.printf(path);
    }


    //=====================第二部分：java查询节点===========================================
    /**
     *
     * 查询节点：
     *      * 1. 查询数据：get: getData().forPath()
     *      * 2. 查询子节点： ls: getChildren().forPath()
     *      * 3. 查询节点状态信息：ls -s:getData().storingStatIn(状态对象).forPath()
     */
    @Test
    public void  testGet() throws Exception {
//        byte[] bytes = client.getData().forPath("/app2");
//        System.out.printf(new String(bytes));

        //查询子节点
        List<String> list = client.getChildren().forPath("/app6");
        System.out.println(list);

        //查询节点状态信息
        Stat stat=new Stat();
        System.out.println(stat);
        byte[] bytes = client.getData().storingStatIn(stat).forPath("/app2");
        System.out.println(stat);
    }



//=====================第三部分：java修改节点===========================================

    /**
     *  修改数据
     *      * 1. 基本修改数据：setData().forPath()
     *      * 2. 根据版本修改: setData().withVersion().forPath()
     *      * * version 是通过查询出来的。目的就是为了让其他客户端或者线程不干扰我。
     * @throws Exception
     */
    @Test
public void  testUpdate() throws Exception {

      //  client.setData().forPath("/app1","update".getBytes());


     /*   Stat stat=new Stat();
        byte[] bytes = client.getData().storingStatIn(stat).forPath("/app1");
        Stat stat1 = client.setData().withVersion(stat.getVersion()).forPath("/app1", "update2".getBytes());
        System.out.println(stat1.getVersion());
*/
        Stat stat2 = client.setData().withVersion(100).forPath("/app1", "update3".getBytes());
        System.out.println(stat2);

    }


    //=====================第四部分：java删除节点===========================================

    /**
     *  删除节点： delete deleteall
     *      * 1. 删除单个节点:delete().forPath("/app1");
     *      * 2. 删除带有子节点的节点:delete().deletingChildrenIfNeeded().forPath("/app1");
     *      * 3. 必须成功的删除:为了防止网络抖动。本质就是重试。  client.delete().guaranteed().forPath("/app2");
     */
    @Test
    public void  testDelete() throws Exception {
     //   client.delete().forPath("/app6");

/*
        client.delete().deletingChildrenIfNeeded().forPath("/app2");
        System.out.println("delete success");

*/

        client.delete().guaranteed().forPath("app2");

        client.delete().inBackground(new BackgroundCallback() {
            @Override
            public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                System.out.println("删除之后执行回调方法");
            }
        }).forPath("/app2");
    }


    @After
    public void destory(){
        if(client!=null){
            client.close();
        }
    }

}
