package wcs;

import org.springframework.stereotype.Service;

/**
 * @author liuzongshuai
 * @date 2022/12/22 10:31
 *
 * 定义消费者* *
 */
@Service
@EventConsume(identifier="order")
public class OrderEventHandlerImpl implements EventHandler<Order> {

    @Override
    public boolean process(Order data) {
        System.out.println(data.getId());
        return true;
    }
}

