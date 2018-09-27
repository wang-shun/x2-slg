package com.xgame.logic.server;


import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.xgame.logic.server.game.timertask.entity.job.data.TimerTaskData;

public class TestClass {

	public static void main(String[] args) {
//		System.out.println(TimeUtils.getCurrentTime());
//		
//		ReqDefensSolderSetupMessage defensSolderSetupMessage = new ReqDefensSolderSetupMessage();
//		defensSolderSetupMessage.buildUid = 613000;
//		defensSolderSetupMessage.useSoldier = new SoldierBriefPro();
//		defensSolderSetupMessage.useSoldier.num = 100;
//		defensSolderSetupMessage.useSoldier.soldierId = 120210;
//		
//		byte[] bytes = MSerializer.encode(defensSolderSetupMessage);
//		System.out.println('bytes='+bytes);
//		byte[] out = new byte[12];
//		out[0] = 16;
//		out[1] = -120;
//		out[2] = -75;
//		out[3] = 37;
//		out[4] = 26;
//		out[5] = 6;
//		out[6] = 8;
//		out[0] = 16;
//		out[0] = 16;
//		System.out.println(10-Double.valueOf('10%'));
		
		Queue<Integer> queue = new ConcurrentLinkedQueue<>();
		queue.offer(1);
		queue.offer(2);
		queue.offer(3);
		queue.offer(4);
		queue.offer(5);
		queue.offer(6);
		
		queue.peek();
		
		System.out.println(TimerTaskData.class.getName());
		
		
//		String str = "{'@type':'com.xgame.logic.server.game.player.entity.eventmodel.SpeedUpEventObject','param':'{'@type':'com.xgame.logic.server.game.timertask.entity.job.data.BuildTimerTaskData','level':1,'sid':1101,'state':1,'uid':611010,'vector2Bean':{'x':38,'y':30}}','timerTaskCommand':'BUILD','type':9}";
//
//		System.out.println(JsonUtil.fromJSON(str, SpeedUpEventObject.class));
//		System.out.println();
		//				{16, -120, -75, 37, 26, 6, 8, -110, -85, 7, 16, 100};
//		MSerializer.decode(out, ReqDefensSolderSetupMessage.class);
//		System.out.println(out);
	}

}
