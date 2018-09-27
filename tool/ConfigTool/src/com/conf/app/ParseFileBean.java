package com.conf.app;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.conf.app.util.Kit;

/**
 *
 *2016-12-02  14:40:06
 *@author ye.yuan
 *
 */
public class ParseFileBean {
	
	
	private String fileName;
	
	private String packageName;
	
	private String fileFactoryName;
	
	private String importName;
	
	private List<List<ConfBean>> confs;
	
	private String xdate;
	
	public ParseFileBean(File file) {
		try {
			packageName = file.getName().replace(".txt", "");
			String fomat = packageName.substring(0,1).toUpperCase()+packageName.substring(1);
			fileName = fomat+"Pir";
			fileFactoryName = fileName+"Factory";
			importName = (ConfigTool.packageParentName+packageName+"."+fileFactoryName).replaceAll("\\\\", ".");
			List<String> lines = Files.readAllLines(file.toPath(), Charset.forName("UTF-8"));
			String[] sees = lines.get(0).split("\t");
			String[] fields = lines.get(1).split("\t");
			String[] fieldTypes = lines.get(2).split("\t");
			confs = new ArrayList<>(lines.size());
			for(int i=3;i<lines.size();i++){
				String [] values = lines.get(i).split("\t");
				List<ConfBean> arr = new ArrayList<>();
				for(int j=0;j<fields.length;j++){
					if(fields[j].equals("#"))continue;
					String see = j<sees.length?sees[j]:"";
					String field = j<fields.length?fields[j]:"";
					String fieldType = j<fieldTypes.length?fieldTypes[j]:"";
					String value = j<values.length?values[j]:"";
					ConfBean confBean = new ConfBean(j, see, field, fieldType,value);
					arr.add(confBean);
				}
				confs.add(arr);
			}
			this.xdate = Kit.dateStr(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getPackageName() {
		return packageName;
	}



	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}



	public List<List<ConfBean>> getConfs() {
		return confs;
	}



	public void setConfs(List<List<ConfBean>> confs) {
		this.confs = confs;
	}



	public String getFileFactoryName() {
		return fileFactoryName;
	}



	public void setFileFactoryName(String fileFactoryName) {
		this.fileFactoryName = fileFactoryName;
	}


	public String getImportName() {
		return importName;
	}



	public void setImportName(String importName) {
		this.importName = importName;
	}



	public String getXdate() {
		return xdate;
	}



	public void setXdate(String xdate) {
		this.xdate = xdate;
	}
	
	
}
