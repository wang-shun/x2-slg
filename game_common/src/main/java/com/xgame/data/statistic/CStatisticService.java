package com.xgame.data.statistic;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.xgame.data.service.IService;

/**
 * 包的大小统计管理器
 * @author jacky.jiang
 *
 */
public class CStatisticService implements IService {
	
	
	public static CStatisticService statisticService = new CStatisticService();
	
	public static CStatisticService getInstance() {
		return statisticService;
	}

    private int period = 1;
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    @Override
    public void init() {
        executor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                _doSTatistic();
            }
        }, 0, period, TimeUnit.MINUTES);
    }

    @Override
    public void shutdown() {
        executor.shutdown();
    }

    private void _doSTatistic() {
        MessageStat.getInstance().doStatistic();
        ChannelStatistic.getInstance().doStatistic();
    }
}
