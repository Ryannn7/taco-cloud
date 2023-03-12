package tacos.message;

/**
 * Created with IDEA
 * author:Liuzongshuai
 * Date:2023/3/12
 * Time:13:05
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import tacos.Order;
import tacos.message.ui.KitchenUI;

@Component
public class OrderListener {
    private KitchenUI ui;
    @Autowired
    public OrderListener(KitchenUI ui) {
        this.ui = ui;
    }

    /**
     * receiveOrder()方法使用了JmsListener注解，这样它就会监听tacocloud.order.queue目的地的消息。
     * 该方法不需要使用JmsTemplate，也不会被你的应用显式调用。相反，Spring中的框架代码会等待消息抵达指定的目的地，
     * 当消息到达时，receiveOrder()方法会被自动调用，并且会将消息中的Order载荷作为参数。
     * 推送模式：当监听器发现有消息的时候就会将消息推送过来； 被动的监听消息，而不是主动的获取消息。
     * @param order
     */
    @JmsListener(destination = "tacocloud.order.queue")
    public void receiveOrder(Order order) {
        ui.displayOrder(order);
    }
}
