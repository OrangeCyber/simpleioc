package demo.service.impl;

import demo.service.BillyService;
import demo.service.VanService;
import ioc.annotation.AutoBean;
import ioc.annotation.Bean;

@Bean
public class VanServiceImpl implements VanService {
	
	@AutoBean
	private BillyService billyService;
	
	public void battle() {
		System.out.println("Van �ᷢ�����룺1������  2������ ");
		billyService.agree();
	}

}
