package com.xgame.data.statistic;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;

/**
 * 数据包的大小统计
 * @author jacky.jiang
 */
@Slf4j
public class ChannelStatistic {
	
	private static ChannelStatistic channelStatistic = new ChannelStatistic();
	
	public static ChannelStatistic getInstance(){
		return channelStatistic;
	}
   
	private AtomicInteger channelSize = new AtomicInteger();

    public void increase(int num) {
        channelSize.addAndGet(num);
    }

    public void doStatistic() {
        log.info("当前连接客户端数量：channelSizeL:{}", channelSize.get());
    }
}
