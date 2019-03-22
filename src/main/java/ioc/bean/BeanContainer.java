package ioc.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author 黄传举 2019.03.21
 *
 */
public class BeanContainer {
	
	static Map<String,Object> container = new HashMap<String, Object>();;
	
	public static void join(Object obj) {
		if(!container.containsKey(obj.getClass().getName())) {
			container.put(obj.getClass().getName(), obj);
			System.out.println(obj.getClass().getName() + "♂加入游戏;");
		}
	}
	
	public static Map<String,Object> getContext() {
		return BeanContainer.container;
	}
}
