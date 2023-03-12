package tacos.messaging;

import org.aspectj.weaver.ast.Or;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tacos.Order;

/**
 * Created with IDEA
 * author:Liuzongshuai
 * Date:2023/3/12
 * Time:14:04
 */
@Component
public class RabbitOrderReceiver {

    private RabbitTemplate rabbit;
    private MessageConverter converter;
    @Autowired
    public RabbitOrderReceiver(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
        this.converter = rabbit.getMessageConverter();
    }

    public Order receiveOrder1(){
        Message message = rabbit.receive("tacocloud.orders");
        return message!=null ?(Order)converter.fromMessage(message) : null;
    }

    public Order receiveOrder2(){
        Message message = rabbit.receive("tacocloud.order.queue",3000);
        return message!=null ?(Order)converter.fromMessage(message) : null;
    }

    public Order receiveOrder3(){
       return  (Order) rabbit.receiveAndConvert("tacocloud.order.queue");

    }

}
