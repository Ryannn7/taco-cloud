package wcs;

import com.google.common.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author liuzongshuai
 * @date 2022/12/22 10:29
 */
@Component
@Slf4j
public class EventProducer {

    @Autowired
    private EventHub eventHub;

    public <T> void post(String identifier, T data) {
        EventBus eventBus = eventHub.getEventBus(identifier);
        if (eventBus == null) {
            log.error("identifier [ {} ] 没有事件监听者", identifier);
            return;
        }
        eventBus.post(data);
    }
}
