package ioc.bean;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Map;

import ioc.annotation.AutoBean;
import ioc.annotation.Bean;

/**
 * 
 * @author 黄传举 2019.03.21
 *
 */
public class BeanConstructor {
	
	private static String currentPath;//当前项目路径
	
	public BeanConstructor(String packageName) {
		init(packageName);
	}
	
	public void init(String packageName) {
		try {
			setCurrentPath();
			Enumeration<URL> systemResources = ClassLoader.getSystemResources(packageName);
			while (systemResources.hasMoreElements()) {
				URL url = systemResources.nextElement();
				if ("file".equals(url.getProtocol())) {
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					createBeanByClassName(filePath);
				}
			}
			dependency();
		} catch (Exception e) {
			System.err.println("door♀初始失败");
			e.printStackTrace();
		}
	}

	/**
	 * 创建Bean
	 * @param filePath
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private void createBeanByClassName(String filePath) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		File f = new File(filePath);
		File[] files = f.listFiles();
		if (files == null || files.length <= 0)
			return;
		for (File file : files) {
			String fileName = file.getAbsolutePath();
			if (file.isFile()) {
				if (fileName.endsWith(".class")) {
					String className = fileName.replace(currentPath, "").replace(".class", "").replace("\\", ".");
					Class<?> cls = Class.forName(className);
					Bean[] annotationsBeans = cls.getAnnotationsByType(Bean.class);
					if (annotationsBeans != null && annotationsBeans.length > 0) {
						Object instance = cls.newInstance();
						BeanContainer.join(instance);
					}
				}
			} else {
				createBeanByClassName(fileName);
			}
		}
	}
	
	/**
	 * 依赖注入
	 * @throws Exception
	 */
	public void dependency() throws Exception {
		Map<String, Object> container = BeanContainer.getContext();
		Object[] beanKeys = container.keySet().toArray();
		for (int i = 0; i < beanKeys.length; i++) {
			Class<?> cls = Class.forName(beanKeys[i].toString());
			Object target = container.get(beanKeys[i].toString());
			Field[] fields = cls.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				for (int j = 1; j < beanKeys.length; j++) {
					Class<?> itf = field.getType();
					AutoBean annotation = field.getAnnotation(AutoBean.class);
					Object instance = container.get(beanKeys[j].toString());
					if (annotation != null && itf.isAssignableFrom(instance.getClass())) {
						field.set(target, instance);
					}
				}
				field.setAccessible(false);
			}
		}
	}
	
	private void setCurrentPath() throws Exception{
		currentPath = this.getClass().getResource("/").getPath().replace("/", "\\").replace("test-", "");
		currentPath = currentPath.substring(1, currentPath.length());
		currentPath = URLDecoder.decode(currentPath, "UTF-8");
	}
}
