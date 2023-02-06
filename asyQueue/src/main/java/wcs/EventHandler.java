package wcs;

import com.google.common.eventbus.Subscribe;

/**
 * @author liuzongshuai
 * @date 2022/12/22 10:24
 */
public interface EventHandler<T> {

    @Subscribe
    boolean process(T data);
}
