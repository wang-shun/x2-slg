package com.xgame.logic.server.game.copy.constant;

import java.util.Comparator;

import com.xgame.config.copy.CopyPir;
import com.xgame.logic.server.game.copy.enity.MainCopy;
import com.xgame.logic.server.game.copy.enity.MainCopyPoint;

public class CopySequence {

	
	public static final Comparator<MainCopyPoint> MAINCOPYPOINT_SORT = new Comparator<MainCopyPoint>(){

		@Override
		public int compare(MainCopyPoint arg0, MainCopyPoint arg1) {
			return arg0.getCopyPointId() - arg1.getCopyPointId();
		}
	};
	
	public static final Comparator<MainCopy> MAINCOPY_SORT = new Comparator<MainCopy>(){
		
		@Override
		public int compare(MainCopy arg0, MainCopy arg1) {
			return arg0.getCopyId() - arg1.getCopyId();
		}
	};
	
	public static final Comparator<CopyPir> COPYPIR_SORT = new Comparator<CopyPir>(){
		
		@Override
		public int compare(CopyPir arg0, CopyPir arg1) {
			return arg0.getId() - arg1.getId();
		}
	};
}
