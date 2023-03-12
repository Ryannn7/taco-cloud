package tacos.message.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tacos.Order;
import tacos.data.OrderRepository;
import tacos.message.JmsOrderMessagingService;

import java.util.Optional;

/**
 * Created with IDEA
 * author:Liuzongshuai
 * Date:2023/3/12
 * Time:3:29
 */
@RestController
@RequestMapping(path="/mq",                      // <1>
        produces="application/json")
@CrossOrigin(origins="*")
public class JmsTacoController {

    @Autowired
    private JmsOrderMessagingService jmsOrderMessagingService;

    @Autowired
    private OrderRepository repo;

    @GetMapping("/convertAndSend/order")
    public String convertAndSendOrder() {
        Iterable<Order> all = repo.findAll();
        Optional<Order> option = repo.findById(4l);
        Order order=null;
        if(!option.isPresent()){
            order=repo.findById(4l).get();
        }
        if(order==null){
            order=new Order();
            order.setId(100l);
            order.setDeliveryName("zshuai");
        }
        jmsOrderMessagingService.sendOrder(order);
        return "success";

    }



}
