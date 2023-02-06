package wcs;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liuzongshuai
 * @date 2022/12/22 10:26
 */
@Component
@Slf4j
public class EventHub {



    private final Map<String, AsyncEventBus> eventBusMap = new ConcurrentHashMap<>();

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void onStart() {
        this.loadEventHandler();
    }

    public void loadEventHandler() {
        Map<String, EventHandler> eventHandlerMap = applicationContext.getBeansOfType(EventHandler.class);
        for (EventHandler eventHandler : eventHandlerMap.values()) {
            this.register(eventHandler);
        }
    }

    private void register(EventHandler eventHandler) {
        EventConsume eventConsume = eventHandler.getClass().getAnnotation(EventConsume.class);
        if (eventConsume == null || StringUtils.isEmpty(eventConsume.identifier())) {
            log.error("EventHandler[ {} ]没有配置 identifier ，注册失败", eventHandler.getClass().getSimpleName());
            return;
        }
        String identifier = eventConsume.identifier();
        AsyncEventBus eventBus = eventBusMap.get(identifier);
        if (eventBus == null) {
            AsyncEventBus asyncEventBus = new AsyncEventBus(identifier, EventThreadPoolFactory.buildDefaultExecutor(identifier));
            eventBus = ObjectUtils.defaultIfNull(eventBusMap.putIfAbsent(identifier, asyncEventBus), asyncEventBus);
        }
        eventBus.register(eventHandler);
    }

    public EventBus getEventBus(String identifier) {
        return eventBusMap.get(identifier);
    }
}
