package tacos.messaging;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.*;
import tacos.Order;
import tacos.Taco;

@RestController
@RequestMapping(path="/aabb", produces="application/json")
@CrossOrigin(origins="*")
public class RabbitMqController implements CommandLineRunner {

    @Autowired
    private RabbitOrderMessagingService sendService;

    @GetMapping("/{id}")
    public Taco addMqMessage (@PathVariable("id") Long id ){
        Order order = new Order();
        order.setId(id);
        order.setDeliveryName("20230312");
        sendService.sendOrder2(order);
        System.out.println("----------");
        return null;


    }

    @GetMapping()
    public String addMqMessage2 ( ){
        Order order = new Order();
        order.setId(34l);
        order.setDeliveryName("20230312");
        sendService.sendOrder2(order);

        return "success";
    }

    @Override
    public void run(String... args) throws Exception {

        Order order = new Order();
        order.setId(12l);
        order.setDeliveryName("20230312");
        sendService.sendOrder2(order);
        System.out.println(" 发送消息----------");

    }
}
