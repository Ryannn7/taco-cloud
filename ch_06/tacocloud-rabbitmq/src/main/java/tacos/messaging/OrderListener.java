package tacos.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tacos.Order;

/**
 * Created with IDEA
 * author:Liuzongshuai
 * Date:2023/3/12
 * Time:14:11
 */

@Component
public class OrderListener {
  /*  private KitchenUI ui;
    @Autowired
    public OrderListener(KitchenUI ui) {
        this.ui = ui;
    }*/
    @RabbitListener(queues = "tacocloud.order.queue") //监听具体队列中的数据
    public void receiveOrder(Order order) {
        System.out.printf("接收到Order信息："+order);
    }
}