package tacos.messaging;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {

  static final String topicExchangeName = "tacocloud.orders";

  static final String routing_key="kitchens.central.#";
  public static String queueName="tacocloud.order.queue";


  @Bean  //声明队列：5.Message durability消息持久化  要持久化队列queue的持久化需要在声明时指定durable=True;
  Queue queue(){
    return new Queue(queueName,false);
  }

  @Bean
  /**
   * //声明exchange
   * 主题匹配订阅,这里的主题指的是RoutingKey,RoutingKey可以采用通配符,如:*或#，RoutingKey命名采用.来分隔多个词,只有消息这将队列绑定到该路由器且指定RoutingKey符合匹配规则时才能收到消息;
   * *(星号)代表任意一个单词
   * #(hash)0个或多个单词
   */
  TopicExchange exchange(){
    return new TopicExchange(topicExchangeName);
  }

  @Bean //将队列和exchange 根据routing_key进行绑定。
  Binding binding(Queue queue, TopicExchange exchange){
    return BindingBuilder.bind(queue).to(exchange).with(routing_key);
  }

  @Bean
  public Jackson2JsonMessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }

}
