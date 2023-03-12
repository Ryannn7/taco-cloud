package tacos.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;
import tacos.Order;

import javax.jms.JMSException;
import javax.jms.Message;

/**
 * Created with IDEA
 * author:Liuzongshuai
 * Date:2023/3/12
 * Time:12:46
 */
@Component
public class JmsOrderReceiver implements OrderReceiver{

    private JmsTemplate jms;
    private MessageConverter converter;
    @Autowired
    public JmsOrderReceiver(JmsTemplate jms, MessageConverter converter) {
        this.jms = jms;
        this.converter = converter;
    }

    /**
     * 拉动模式，当client好之后，调用此方法，如果有数据就返回，如果MQ中没有数据，就同步阻塞，等待有数据之后在返回。
     * @return
     * @throws JMSException
     */
    public Order receiveOrder() throws JMSException {
        Message message = jms.receive("tacocloud.order.queue");
        //return (Order) converter.fromMessage(message);
        return   (Order)jms.receiveAndConvert("tacocloud.order.queue");
    }

}
