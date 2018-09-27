package com.xgame.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

import org.springframework.util.ResourceUtils;

/**
 * @author Alex
 */
@Slf4j
public class FileUtils {
	public static final Charset UTF8 = Charset.forName("UTF-8");
	

	public static String readAllLinesAsString(String fpath) {
		try {
			URL firstUrl = ResourceUtils.getURL(fpath);
			Path path = Paths.get(firstUrl.toURI());
			return readAllLinesAsString(path);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String readTxt(File file) {
		String result = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String s = null;
			while ((s = br.readLine()) != null) {
				if (!"".equals(result)) {
					result = result + "\n" + s;
				} else {
					result = s;
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String readAllLinesAsString(Path path) {
		return readAllLinesAsString(path, UTF8);
	}

	public static String readAllLinesAsString(Path path, Charset cs) {
		String str = "";
		for (String line : readAllLines(path, cs)) {
			str += line;
		}
		return str;
	}

	public static List<String> readAllLines(Path path) {
		return readAllLines(path, UTF8);
	}

	public static List<String> readAllLines(Path path, Charset cs) {
		try {
			return Files.readAllLines(path, cs);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * '#'开头的行会被跳过
	 *
	 * @param fristFile
	 *            the resource location to resolve: either a "classpath:" pseudo
	 *            URL, a "file:" URL, or a plain file path
	 */
	public static <R> List<R> readAllLines(String fristFile,
			Function<String, R> func) {
		try {
			URL firstUrl = ResourceUtils.getURL(fristFile);
			Path path = Paths.get(firstUrl.toURI());
			return Files.readAllLines(path, UTF8).stream()
					.filter(str -> !str.startsWith("#") && !str.isEmpty())
					.map(func).collect(Collectors.toList());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static ArrayList<File> getPathAllFile(String path) {
		ArrayList<File> filelist = new ArrayList<File>();
		doGetPathAllFile(path, filelist);
		return filelist;
	}

	static void doGetPathAllFile(String path, ArrayList<File> filelist) {
		File root = new File(path);
		File[] files = root.listFiles();

		for (File file : files) {
			if (file.isDirectory()) {
				doGetPathAllFile(file.getAbsolutePath(), filelist);
			} else {
				filelist.add(file);
			}
		}
	}
}
