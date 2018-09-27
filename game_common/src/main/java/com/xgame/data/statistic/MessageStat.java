package com.xgame.data.statistic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import lombok.extern.slf4j.Slf4j;

/**
 * 网管消息监控
 * @author jacky.jiang
 *
 */
@Slf4j
public class MessageStat {
    private static MessageStat messageStatManager = new MessageStat();
    private AtomicInteger size = new AtomicInteger();
    private AtomicInteger sizeMB = new AtomicInteger();
    private AtomicInteger speed = new AtomicInteger();
    private AtomicLong total = new AtomicLong();

    private MessageStat() {
    }

    ;

    public void doStatistic() {
        log.info("message statistic: size:" + size.getAndSet(0) / 60 + "_ total:" + total.get() / 60);
    }

    public void increase(int dataSize) {
        size.incrementAndGet();
        sizeMB.addAndGet(dataSize);
        speed.addAndGet(dataSize);
        total.incrementAndGet();
    }

    public static MessageStat getInstance() {
        return messageStatManager;
    }

}
