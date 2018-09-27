package com.xgame.logic.server.core.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xgame.logic.server.game.world.SpriteManager;


/**
 *数学计算
 *2016-7-12  16:12:14
 *@author ye.yuan
 *
 */
public class MathUtil {

	/**
	 * 说是lua只能收 14位 所以简单移下,解决64后删掉
	 */
	public static final long LUA_INT64_MAX_VALUE = Long.MAX_VALUE>>17;
	
	
	/* 几何方法 */
	
	public static boolean rectContain(int w1,int w2,int h1,int h2, float x, float y) {
		return (x >= w1 && x < w2 && y >= h1 && y < h2);
	}
	
	/**
	 * 根据中心点   半径像素    变换一个2维矩形
	 * @param x
	 * @param y
	 * @param pix
	 * @return 左到右 
	 */
	public static int[] getRectRange(int x,int y,int pix){
		int [] arr = new int[4];
		arr[0] = x - pix;
		arr[1] = x + pix;
		arr[2] = y - pix;
		arr[3] = y + pix;
		return arr;
	}
	
	/**
	 * 获取范围内的格子信息
	 * @param x
	 * @param y
	 * @param range
	 * @return
	 */
	public static List<Integer> getRange(int x,int y, int range) {
		List<Integer> rangList = new ArrayList<Integer>();
		for (int i = x - range; i <= x + range; i++) {
			for (int j = y - range; j <= y + range; j++) {
				rangList.add(j * SpriteManager.xGridNum + x);
			}
		}
		return rangList;
	}
	
	
	/**
	 * 指定位置上的矩形 平移变换 
	 * @param mx
	 * @param my
	 */
	public static int[] rectLeftMove(int mx,int my,int width,int height){
		int [] arr = new int[4];
		arr[0] = mx/2-width/2;
		arr[1] = mx/2+width/2;
		arr[2] = my/2-height/2;
		arr[3] = my/2+height/2;
		return arr;
	}
	
	
	/**
	 * 线段相交判定， 必须确保b1>=a1,b2>=a2， 返回值：1为完全包含，2为相等，3为完全被包含，-1为完全不包含，0为相交。
	 */
	public static int lineCross(float a1, float b1, float a2, float b2) {
		if (a2 > a1) {
			if (a2 >= b1)
				return -1;
			if (b2 <= b1)
				return 1;
			return 0;
		} else if (a2 < a1) {
			if (b2 <= a1)
				return -1;
			if (b2 >= b1)
				return 3;
			return 0;
		} else {
			if (b2 < b1)
				return 1;
			if (b2 == b1)
				return 2;
			return 3;
		}
	}

	/**
	 * 矩形1和矩形2的相交判定， 必须确保x1<x2,y1<y2,x3<x4,y3<y4，
	 * 返回值：1为完全包含，2为相等，3为完全被包含，-1为完全不包含，0为相交。
	 */
	public static int rectCross(float x1, float y1, float x2, float y2,
			float x3, float y3, float x4, float y4) {
		int r1 = lineCross(x1, x2, x3, x4);
		int r2 = lineCross(y1, y2, y3, y4);
		if (r1 < 0 || r2 < 0)
			return -1;
		if (r1 == 1 && r2 == 1)
			return 1;
		if (r1 == 2 && r2 == 2)
			return 2;
		if (r1 == 3 && r2 == 3)
			return 3;
		return 0;
	}
	
	/**
	 * 计算距离
	 * @param a
	 * @return
	 */
	public static int magnitude(int x,int y,int x2,int y2)
	{
		int m = Math.abs(x-x2);
		int m2 = Math.abs(y-y2);
	    return (int) Math.sqrt((m * m) + (m2 * m2));
	}
	
	public static int matrixRunTime(int x,int y,int x2,int y2,double t)
	{
		int distance = magnitude(x, y,x2,y2);
	    return (int)Math.ceil(((double)distance)/t);
	}
	
	/**
	 * 线段与矩形相交判定方法
	 * @param px1
	 * @param py1
	 * @param px2
	 * @param py2
	 * @param rectLeftTopX
	 * @param rectLeftTopY
	 * @param rectRightBottomX
	 * @param rectRightBottomY
	 * @return
	 */
	public static boolean lineCrossRect(
			int px1,
			int py1,
			int px2,
			int py2,
			int rectLeftTopX,
			int rectLeftTopY,
			int rectRightBottomX,
			int rectRightBottomY){
		int lineH = py1-py2;
		int lineW = px2-px1;
		int c = px1*py2-px2*py1;
		if(lineH*rectLeftTopX+lineW*rectLeftTopY+c>=0&&lineH*rectRightBottomX+lineW*rectRightBottomY+c<=0||
		   lineH*rectLeftTopX+lineW*rectLeftTopY+c<=0&&lineH*rectRightBottomX+lineW*rectRightBottomY+c>=0||
		   lineH*rectLeftTopX+lineW*rectRightBottomY+c>=0&&lineH*rectRightBottomX+lineW*rectLeftTopY+c<=0||
		   lineH*rectLeftTopX+lineW*rectRightBottomY+c<=0&&lineH*rectRightBottomX+lineW*rectLeftTopY+c>=0){
			if(rectLeftTopX>rectRightBottomX){
				int temp =  rectLeftTopX;
				rectLeftTopX = rectRightBottomX;
				rectRightBottomX = temp;
			}
			if(rectLeftTopY<rectRightBottomY){
				int temp =  rectLeftTopY;
				rectLeftTopY = rectRightBottomY;
				rectRightBottomY = temp;
			}
			if(px1<rectLeftTopX&&px2<rectLeftTopX||
			   px1>rectRightBottomX&&px2>rectRightBottomX||
			   py1>rectLeftTopY&&py2>rectLeftTopY||
			   py1<rectRightBottomY&&py2<rectRightBottomY){
				return false;  
			}else{
				return true;  
			}
		}else{
			return false;
		}
	}
	
	/**
	 * 获得值类型
	 * @author ye.yuan
	 *
	 */
	enum DataEnum{
		
		BYTE{
			public Object  changeType(String param){
				return Byte.parseByte(param);
			}
			
			public strictfp Number math(Number n,Number v,String mark){
				if(mark.equals("+")){
					n = n.byteValue()+v.byteValue();
				} 
				else if(mark.equals("-")){
					n = n.byteValue()-v.byteValue();
				}
				return n;
			}
		},
		
		CHAR{
			
		},
		
		SHORT{
			public Object  changeType(String param){
				return Short.parseShort(param);
			}
			
			public strictfp Number math(Number n,Number v,String mark){
				if(mark.equals("+")){
					n = n.shortValue()+v.shortValue();
				} 
				else if(mark.equals("-")){
					n = n.shortValue()-v.shortValue();
				}
				return n;
			}
		},
		
		INT{
			public Object  changeType(String param){
				return Integer.parseInt(param);
			}
			
			public strictfp Number math(Number n,Number v,String mark){
				if(mark.equals("+")){
					n = n.intValue()+v.intValue();
				} 
				else if(mark.equals("-")){
					n = n.intValue()-v.intValue();
				}
				return n;
			}
		},
		
		LONG{
			public strictfp Object  changeType(String param){
				return Long.parseLong(param);
			}
			
			public Number math(Number n,Number v,String mark){
				if(mark.equals("+")){
					n = n.longValue()+v.longValue();
				} 
				else if(mark.equals("-")){
					n = n.longValue()-v.longValue();
				}
				return n;
			}
		},
		
		DOUBLE(){
			public strictfp Object  changeType(String param){
				return Double.parseDouble(param);
			}
			
			public Number math(Number n,Number v,String mark){
				if(mark.equals("+")){
					n = n.doubleValue()+v.doubleValue();
				} 
				else if(mark.equals("-")){
					n = n.doubleValue()-v.doubleValue();
				}
				return n;
			}
		},
		
		FLOAT(){
			public strictfp Object  changeType(String param){
				return Float.parseFloat(param);
			}
			
			public Number math(Number n,Number v,String mark){
				if(mark.equals("+")){
					n = n.floatValue()+v.floatValue();
				} 
				else if(mark.equals("-")){
					n = n.floatValue()-v.floatValue();
				}
				return n;
			}
		},
		
		BOOLEAN(){
			public Object  changeType(String param){
				return Boolean.parseBoolean(param);
			}
		},
		;
		
		
		public Object changeType(String param){
			throw new AbstractMethodError();
		}
		
		/**
		 * 根据相加的值类型不同,把number转换成对应的类型相加
		 * 此方法只适合对精度要求不高的计算,如指标,日志
		 * @param n
		 * @param v
		 * @param mark
		 * @return
		 */
		public Number math(Number n,Number v,String mark){
			throw new AbstractMethodError();
		}
		
		private static final Map<Class<?>, DataEnum> baseClazzMap = new HashMap<>();
		
		static{
			baseClazzMap.put(byte.class, BYTE);
			baseClazzMap.put(Byte.class, BYTE);
			baseClazzMap.put(char.class, CHAR);
			baseClazzMap.put(short.class, SHORT);
			baseClazzMap.put(Short.class, SHORT);
			baseClazzMap.put(int.class, INT);
			baseClazzMap.put(Integer.class, INT);
			baseClazzMap.put(long.class,LONG);
			baseClazzMap.put(Long.class,LONG);
			baseClazzMap.put(double.class,  DOUBLE);
			baseClazzMap.put(Double.class,  DOUBLE);
			baseClazzMap.put(float.class, FLOAT);
			baseClazzMap.put(Float.class, FLOAT);
			baseClazzMap.put(boolean.class, BOOLEAN);
			baseClazzMap.put(Boolean.class, BOOLEAN);
		}
	}
	
    
    public static Object getDataType(Class<?> class1,String v){
    	DataEnum dataEnum = DataEnum.baseClazzMap.get(class1);
    	if(dataEnum==null){
    		return null;
    	}
    	return dataEnum.changeType(v);
    }
    
    public static Number math(Number n,Number v,String mark){
    	DataEnum dataEnum = DataEnum.baseClazzMap.get(n.getClass());
    	if(dataEnum==null){
    		return n;
    	}
    	return dataEnum.math(n, v, mark);
    }
}
