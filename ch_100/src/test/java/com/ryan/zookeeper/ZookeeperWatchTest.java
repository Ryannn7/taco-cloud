package com.ryan.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.apache.curator.framework.recipes.cache.CuratorCacheListener.Type.NODE_CREATED;

/**
 * Created with IDEA
 * author:Liuzongshuai
 * Date:2023/3/19
 * Time:14:08
 */


public class ZookeeperWatchTest {

    private CuratorFramework client;

    public static Logger log=LogManager.getLogger(ZookeeperWatchTest.class);

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
        .connectString("127.0.0.1:2181")
                .sessionTimeoutMs(60*1000)
                        .connectionTimeoutMs(15*1000)
                                .retryPolicy(retryPolicy)
                                        .namespace("itheima")
                                                .build();
        client.start();
    }


  //使用NodeCache模式
    @Test
    public void  testNodeCahce() throws InterruptedException {
        CuratorCache curatorCache = CuratorCache.build(client, "/app1");
        //创建监听
        curatorCache.listenable().addListener(new CuratorCacheListener() {

            // 第一个参数：事件类型（枚举）
            // 第二个参数：节点更新前的状态、数据
            // 第三个参数：节点更新后的状态、数据
            // 创建节点时：节点刚被创建，不存在 更新前节点 ，所以第二个参数为 null
            // 删除节点时：节点被删除，不存在 更新后节点 ，所以第三个参数为 null
            // 节点创建时没有赋予值 create /curator/app1 只创建节点，在这种情况下，更新前节点的 data 为 null，获取不到更新前节点的数据
            @Override
            public void event(Type type, ChildData childData, ChildData childData1) {
                switch (type.name()){

                    case "NODE_CREATED":
                        if(childData!=null){
                            System.out.println("创建了节点："+ new String( childData1.getData()));
                        }
                        break;
                    case "NODE_CHANGED":
                        if(childData!=null){
                            System.out.println("更新了节点："+ new String( childData1.getData()));
                        }else {
                            System.out.println("节点第1次赋值");
                        }
                        break;

                    case "NODE_DELETED":
                        System.out.println(childData.getPath()+"节点已经删除");
                        break;
                    default:
                        break;

                }
            }
        });
        //开启监听
        curatorCache.start();
        //延迟结束
        Thread.sleep(1000*60*10);

    }


    //使用PathChildrenCache模式,方法已经过时
    @Test
    public void  testPathChildrenCache() throws Exception {

        PathChildrenCache pathChildrenCache = new PathChildrenCache(client, "/app2", true);

        //创建监听
        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent event) throws Exception {
                System.out.println("子节点发生了变化");
                PathChildrenCacheEvent.Type type = event.getType();
                if(type.equals(PathChildrenCacheEvent.Type.CHILD_ADDED)){
                System.out.println("新增子节点"+new String(event.getData().getPath() +":"+new String( event.getData().getData())));
                }
                if(type.equals(PathChildrenCacheEvent.Type.CHILD_UPDATED)){
                    System.out.println("修改子节点"+new String(event.getData().getPath() +":"+new String(  event.getData().getData())));
                }
                if(type.equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)){
                    System.out.println("删除子节点"+new String( event.getData().getPath() +":"+new String(  event.getData().getData())));
                }
            }
        });
        //开启监听set
        pathChildrenCache.start();
        //延迟结束
        Thread.sleep(1000*60*10);

    }

    /**
     * 使TreeCache: 监听自身节点以及后续节点的变化
     * @throws InterruptedException
     */
    @Test
    public void  testTreeCache() throws Exception {
        TreeCache treeCache = new TreeCache(client, "/app2");

        //创建监听
        treeCache.getListenable().addListener(new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent event) throws Exception {

                System.out.println("节点发生了变化");
                TreeCacheEvent.Type type = event.getType();
                if(type.equals(TreeCacheEvent.Type.NODE_ADDED)){
                    System.out.println("新增节点"+new String(event.getData().getPath() +":"+new String( event.getData().getData())));
                }
                if(type.equals(TreeCacheEvent.Type.NODE_UPDATED)){
                    System.out.println("修改节点"+new String(event.getData().getPath() +":"+new String(  event.getData().getData())));
                }
                if(type.equals(TreeCacheEvent.Type.NODE_REMOVED)){
                    System.out.println("删除节点"+new String( event.getData().getPath() +":"+new String(  event.getData().getData())));
                }
            }
        });

        //开启监听set
        treeCache.start();
        //延迟结束
        Thread.sleep(1000*60*10);

    }

    @After
    public void destory(){
        if(client!=null){
            client.close();
        }
    }

}
