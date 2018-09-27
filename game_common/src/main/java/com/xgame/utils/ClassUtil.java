package com.xgame.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;

/**
 * 类加载器
 * 
 * @author ye.yuan
 * @date 2014/10/12 10:22:38
 */
public class ClassUtil {
	private static final Logger logger = Logger.getLogger(ClassUtil.class);

	/**
	 * 找类  找包下所有继承 该父类的全部子类  集合返回
	 * @param packageName 包名
	 * @param parentClass 父类
	 * @return
	 */
	public static <T> Map<String,Class<T>> getSubClasses(String packageName,
			Class<T> parentClass) {
		Map<String,Class<T>> classes = new HashMap<>();

		boolean recursive = true;

		String packageDirName = packageName.replace('.', '/');
		try {
			Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader()
					.getResources(packageDirName);

			while (dirs.hasMoreElements()) {
				URL url = (URL) dirs.nextElement();

				String protocol = url.getProtocol();

				if ("file".equals(protocol)) {
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					findAndAddClassesInPackageByFile(packageName, filePath,
							recursive, classes, parentClass);
				} else if ("jar".equals(protocol)) {
					try {
						JarFile jar = ((JarURLConnection) url.openConnection())
								.getJarFile();

						Enumeration<JarEntry> entries = jar.entries();

						while (entries.hasMoreElements()) {
							JarEntry entry = (JarEntry) entries.nextElement();
							String name = entry.getName();

							if (name.charAt(0) == '/') {
								name = name.substring(1);
							}
							String packageNameNew = packageName;

							if (name.startsWith(packageDirName)) {
								int idx = name.lastIndexOf('/');

								if (idx != -1) {
									packageNameNew = name.substring(0, idx).replace('/', '.');
								}

								if ((idx != -1) || (recursive)) {
									if ((name.endsWith(".class"))
											&& (!entry.isDirectory())) {
										String className = name.substring(
												packageNameNew.length() + 1,
												name.length() - 6);
										try {
											Class<?> loadClass = Thread.currentThread().getContextClassLoader().loadClass(packageNameNew
																	+ '.'
																	+ className);
											if ((parentClass
													.isAssignableFrom(loadClass))
													&& (!parentClass
															.equals(loadClass))) {
												@SuppressWarnings("unchecked")
												Class<T> result = (Class<T>) loadClass;
												classes.put(result.getSimpleName(),result);
											}
										} catch (ClassNotFoundException e) {
											logger.error("添加用户自定义视图类错误 找不到此类的.class文件");
											e.printStackTrace();
										}
									}
								}
							}
						}
					} catch (IOException e) {
						logger.error("在扫描用户定义视图时从jar包获取文件出错");
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return classes;
	}
	
	
	public static List<Field> getAllFields(Class<?> class1) {
		List<Field> fieldList = new ArrayList<>();
		Field[] fields = class1.getDeclaredFields();
		for (Field field : fields) {
			fieldList.add(field);
		}
		fields = class1.getSuperclass().getDeclaredFields();
		for (Field field : fields) {
			fieldList.add(field);
		}
		return fieldList;
	}
	
	/**
	 * 找本继承父类下的所有子类
	 * @param packageName 子类包名
	 * @param packagePath 子类路径
	 * @param recursive 文件夹处理
	 * @param classes 已找到的子类集合
	 * @param parentClass 父类
	 */
	public static <T> void findAndAddClassesInPackageByFile(String packageName,
			String packagePath, boolean recursive, Map<String,Class<T>> classes,
			Class<T> parentClass) {
		File dir = new File(packagePath);

		if ((!dir.exists()) || (!dir.isDirectory())) {
			return;
		}

		File[] dirfiles = dir.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return (recursive && (file.isDirectory()))
						|| (file.getName().endsWith(".class"));
			}
		});
		for (File file : dirfiles) {
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile(
						packageName + "." + file.getName(),
						file.getAbsolutePath(), recursive, classes, parentClass);
			} else {
				String className = file.getName().substring(0,
						file.getName().length() - 6);
				try {
					Class<?> loadClass = Thread.currentThread()
							.getContextClassLoader()
							.loadClass(packageName + '.' + className);
					if ((parentClass.isAssignableFrom(loadClass))
							&& (!parentClass.equals(loadClass))) {
						@SuppressWarnings("unchecked")
						Class<T> result = (Class<T>)loadClass;
						classes.put(result.getSimpleName(), result);
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		getSubClasses("com.mchange", Object.class);
	}

}
