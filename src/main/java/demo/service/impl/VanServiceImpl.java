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
		System.out.println("Van ♂发起邀请：1：接受  2：接受 ");
		billyService.agree();
	}

}
