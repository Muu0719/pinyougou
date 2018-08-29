package com.pinyougou.pay.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import com.pinyougou.pay.service.WeiXinPayService;

public class WeiXinPayServiceImpl implements WeiXinPayService {

	@Value("${appid}")
	private String appid;
	
	@Value("${partner}")
	private String partner;
	
	@Value("${partnerkey}")
	private String partnerkey;
	
	@Value("${notifyurl}")
	private String notifyurl;
	
	
	public Map createNative(String out_trade_no, String total_fee) {
		Map map = new HashMap<>();
		//1设置参数
		map.put("appId", this.appid);
		map.put("mch_id", this.partner);
		//2.使用HTTPClient工具类来访问微信的URL并传递参数
		//3.返回第三方的数据
		//4.封装的结果集,并返回
		
		return null;
	}


	@Override
	public Map queryPayStatus(String out_trade_no) {
		//1,封装参数
		//2,设置请求
		//3,
		return null;
	}

}
