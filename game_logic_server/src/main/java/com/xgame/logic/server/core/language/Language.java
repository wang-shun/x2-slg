package com.xgame.logic.server.core.language;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

import com.xgame.logic.server.core.language.view.Tips;
import com.xgame.logic.server.core.utils.JsonUtil;
import com.xgame.logic.server.game.notice.message.ResTitleMessage;
import com.xgame.logic.server.game.player.entity.Player;

/**
 *
 *2016-7-21  20:08:56
 *@author ye.yuan 
 *
 */
public enum Language {
	
	/**
	 * 成功消息提示
	 */
	SUCCESSTIP(1),
	
	/**
	 * 错误码消息提示
	 */
	ERRORCODE(2),
	;
	
	private int type;
	
	Language(int type) {
		this.type=type;
	}
	
	public int getType() {
		return type;
	}
	
	public ResTitleMessage getMessage(int code, Object... param) {
		ResTitleMessage message = new ResTitleMessage();
		message.languageId = code;
		
		//自定义消息
		if (param != null && param.length > 0) {
			List<Object> arrayList = new ArrayList<>();
			for (int i = 0; i < param.length; i++) {
				arrayList.add(param[i]);
			}
			message.param = JsonUtil.toJSON(arrayList);
		}
		
		return message;
	}
	
	/**
	 * 给前端提示
	 * @param player
	 * @param id
	 * @param param 提示携带参数
	 */
	public void send(Player player, Tips tips, Object... param) {
		ResTitleMessage message = new ResTitleMessage();
		message.languageId  = tips.get();
		
		//自定义消息
		if (param != null && param.length > 0) {
			List<Object> arrayList = new ArrayList<>();
			for (int i = 0; i < param.length; i++) {
				arrayList.add(param[i]);
			}
			message.param = JsonUtil.toJSON(arrayList);
		}
		player.send(message);
	}
	
	
	/**
	 * 给前端提示
	 * @param player
	 * @param id
	 * @param param 提示携带参数
	 */
	public void send(Player player, int id, Object... param) {
		ResTitleMessage message = new ResTitleMessage();
		message.languageId  = id;
		
		//自定义消息
		if (param != null && param.length > 0) {
			List<Object> arrayList = new ArrayList<>();
			for (int i = 1; i < param.length; i++) {
				arrayList.add(param[i]);
			}
			message.param = JsonUtil.toJSON(arrayList);
		}
		player.send(message);
	}
	
	private static HashSet<Integer> specials = new HashSet<>();
	
	static {
		String s = "~`!@#$%^&*()-_=+[{]};:'\"\\|,<.>/?";
		for(int i=0;i<s.length();i++){
			specials.add(s.codePointAt(i));
		}
	}
	
	public static boolean haveSpecial(String utf){
		if(utf.length()<=0)return false;
		for(int i=0;i<utf.length();i++){
			if(specials.contains(utf.codePointAt(i))){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		String[] strArr = new String[] { "www.micmiu.com",
//				"!@#$%^&amp;*()_+{}[]|\"'?/:;&lt;&gt;,.", "！￥……（）——：；“”‘’《》，。？、", "不要啊",
//				"やめて", "韩佳人", "???" };
//		for (String str : strArr) {
//			System.out.println("===========&gt; 测试字符串：" + str);
//			System.out.println("正则判断：" + isChineseByREG(str) + " -- "
//					+ isChineseByName(str));
//			System.out.println("Unicode判断结果 ：" + isChinese(str));
//			System.out.println("详细判断列表：");
//			char[] ch = str.toCharArray();
//			for (int i = 0; i &lt; ch.length; i++) {
//				char c = ch[i];
//				System.out.println(c + " --&gt; " + (isChinese(c) ? "是" : "否"));
//			}
//		}
//
//	}

	// 根据Unicode编码完美的判断中文汉字和符号
	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}
	
	
	public static int isFormatName(String strName) {
		int n = 0;
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length;i++) {
			char c = ch[i];
			if (isChinese(c)) {
				n+=2;
			}else{
				n++;
			}
		}
		return n;
	}

	// 完整的判断中文汉字和符号
	public static boolean isChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length;i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

	// 只能判断部分CJK字符（CJK统一汉字）
	public static boolean isChineseByREG(String str) {
		if (str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
		return pattern.matcher(str.trim()).find();
	}

	// 只能判断部分CJK字符（CJK统一汉字）
	public static boolean isChineseByName(String str) {
		if (str == null) {
			return false;
		}
		// 大小写不同：\\p 表示包含，\\P 表示不包含 
		// \\p{Cn} 的意思为 Unicode 中未被定义字符的编码，\\P{Cn} 就表示 Unicode中已经被定义字符的编码
		String reg = "\\p{InCJK Unified Ideographs}&amp;&amp;\\P{Cn}";
		Pattern pattern = Pattern.compile(reg);
		return pattern.matcher(str.trim()).find();
	}

}