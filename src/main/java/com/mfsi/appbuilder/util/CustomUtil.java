package com.mfsi.appbuilder.util;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class CustomUtil {
	
	private CustomUtil() {}

	public static List<Class<?>> getClasses(String packageName){
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		ArrayList<Class<?>> classes = new ArrayList<>();
		String path = packageName.replace('.', '/');
		try {
			Enumeration<URL> resources = classLoader.getResources(path);
			List<File> dirs = new ArrayList<>();
			while (resources.hasMoreElements()) {
				URL resource = resources.nextElement();
				dirs.add(new File(resource.getFile()));
			}
			
			for (File directory : dirs) {
				classes.addAll(findClasses(directory, packageName));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return classes;

	}

	private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
		List<Class<?>> classes = new ArrayList<>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
			}
		}
		return classes;
	}

}
