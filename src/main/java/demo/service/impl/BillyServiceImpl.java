package demo.service.impl;

import demo.service.BillyService;
import ioc.annotation.Bean;

@Bean
public class BillyServiceImpl implements BillyService {

	public void agree() {
		System.out.println("Billy不仅接受挑战，且邀请香蕉君加入");
	}

}
