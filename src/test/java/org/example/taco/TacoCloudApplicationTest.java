package org.example.taco;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wcs.EventProducer;
import wcs.Order;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author liuzongshuai
 * @date 2022/11/16 12:20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TacoCloudApplicationTest {

    @Autowired
    private EventProducer eventProducer;

    @Test
    public void contextLoads(){
        System.out.println("TacoCloudTest");
    }


    @Test
    public void eventBus(){
        Order order = new Order();
        order.setId(10001l);
        order.setName("订单10001");
        //如果要发布其他的事件，只需要改变事件类型。
        eventProducer.post("order",order);
        System.out.println("发送order消息结束");

    }





}