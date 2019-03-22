package test;

import org.junit.Before;
import org.junit.Test;

import demo.service.VanService;
import ioc.bean.BeanConstructor;
import ioc.bean.BeanContainer;

@SuppressWarnings("unused")
public class Battle {
	
	private VanService vanService;
	
	@Test
	public void beging() {//¿ªÊ¼
		vanService.battle();
	}
	
	
	
	@Before
	public void ready() {
		BeanConstructor beanConstructor = new BeanConstructor("demo/service");
		vanService = (VanService) BeanContainer.getContext().get("demo.service.impl.VanServiceImpl");
	}
	
}
