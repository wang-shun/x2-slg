package com.xgame.logic.server.core.language.view;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.xgame.errorcode.annotation.Description;
import com.xgame.logic.server.core.language.view.error.ErrorCodeable;
import com.xgame.logic.server.core.language.view.success.SuccessTip;
import com.xgame.utils.ClassUtil;


/**
 * 生成多语言excel
 * @author jacky.jiang
 *
 */
public class WriteLanguageExcel {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) throws ClassNotFoundException, IOException {

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("错误码");
		
		HSSFRow row0 = sheet.createRow(0);
		row0.createCell(0).setCellValue("提示id");
		row0.createCell(1).setCellValue("模块");
		row0.createCell(2).setCellValue("内容");
		row0.createCell(3).setCellValue("播放方式(1广播 2提示)");
		row0.createCell(4).setCellValue("提示类型(1 绿 2红)");
		row0.createCell(5).setCellValue("频道(3世界频道4军团频道)");

		HSSFRow row1 = sheet.createRow(1);
		row1.createCell(0).setCellValue("id");
		row1.createCell(1).setCellValue("module");
		row1.createCell(2).setCellValue("content");
		row1.createCell(3).setCellValue("play");
		row1.createCell(4).setCellValue("type");
		row1.createCell(5).setCellValue("channel");
		
		HSSFRow row2 = sheet.createRow(2);
		row2.createCell(0).setCellValue("int");
		row2.createCell(1).setCellValue("String");
		row2.createCell(2).setCellValue("String");
		row2.createCell(3).setCellValue("int");
		row2.createCell(4).setCellValue("int");
		row2.createCell(5).setCellValue("int");
		
		int i= 3;
		Map<String, Class<Enum>> map = ClassUtil.getSubClasses("com.xgame.logic.server.core.language.view.error", Enum.class);
		if(map != null) {
			for(Class<Enum> errorCodeable : map.values()) {
				Field[] fields = errorCodeable.getFields();
				if(fields != null && fields.length > 0) {
					for(Field field : fields) {
						Description description = field.getAnnotation(Description.class);
						HSSFRow rowContent = sheet.createRow(i);
						try  {
							Enum<?>  t = Enum.valueOf(errorCodeable, field.getName()) ;
							if(t instanceof ErrorCodeable) {
								ErrorCodeable k = (ErrorCodeable)t;
								rowContent.createCell(0).setCellValue(k.get());
								rowContent.createCell(1).setCellValue(errorCodeable.getSimpleName());
								rowContent.createCell(2).setCellValue(description.desc());
							} else if(t instanceof SuccessTip) {
								SuccessTip k = (SuccessTip)t;
								rowContent.createCell(0).setCellValue(k.get());
								rowContent.createCell(1).setCellValue(errorCodeable.getSimpleName());
								rowContent.createCell(2).setCellValue(description.desc());
							}
						} catch(Exception e) {
							System.out.println("");
						}
						i++;
					}
					
				}
			}
		}
			
		Map<String, Class<Enum>> success_map = ClassUtil.getSubClasses("com.xgame.logic.server.core.language.view.success", Enum.class);
		if (success_map != null) {
			for (Class<Enum> errorCodeable : success_map.values()) {
				Field[] fields = errorCodeable.getFields();
				if (fields != null && fields.length > 0) {
					for (Field field : fields) {
						Description description = field.getAnnotation(Description.class);
						HSSFRow rowContent = sheet.createRow(i);
						try {
							Enum<?> t = Enum.valueOf(errorCodeable, field.getName());
							if (t instanceof ErrorCodeable) {
								ErrorCodeable k = (ErrorCodeable) t;
								rowContent.createCell(0).setCellValue(k.get());
								rowContent.createCell(1).setCellValue(errorCodeable.getSimpleName());
								rowContent.createCell(2).setCellValue(description.desc());
							} else if (t instanceof SuccessTip) {
								SuccessTip k = (SuccessTip) t;
								rowContent.createCell(0).setCellValue(k.get());
								rowContent.createCell(1).setCellValue(errorCodeable.getSimpleName());
								rowContent.createCell(2).setCellValue(description.desc());
							}
						} catch (Exception e) {
							System.out.println("");
						}
						i++;
					}
				}
			}
		}
		
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream("d:\\language.xls");
			wb.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void readExcel() {
		
	}
	
	/**
	 * excel表结构
	 * @author jacky.jiang
	 *
	 */
	public class WriteExcel  {
		
		/**
		 * 错误码id
		 */
		private int id;
		
		/**
		 * 模块
		 */
		private String module;

		/**
		 * 内容
		 */
		private String content;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getModule() {
			return module;
		}

		public void setModule(String module) {
			this.module = module;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
		
	}
}
