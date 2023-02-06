package wcs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuzongshuai
 * @date 2022/12/22 10:46

 */
@RestController
public class EventController {

    @Autowired
    private EventProducer eventProducer;

    @RequestMapping("/order")
    public void test(@RequestBody Order order){
        //如果要发布其他的事件，只需要改变事件类型。
        eventProducer.post("order",order);
        System.out.println("发送order消息结束");
    }
}
