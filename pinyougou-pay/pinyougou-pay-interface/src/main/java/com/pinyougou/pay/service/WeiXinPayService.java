package com.pinyougou.pay.service;

import java.util.Map;

public interface WeiXinPayService {

	
	/** 
	
	 * @Title:        createNative 
	 * @Description:  支付的参数封装方法       
	 * @param out_trade_no
	 * @param total_fee
	 * @return   
	 * @author        Muu_E-mail:369566919@qq.com
	 * @Date          2018-08-27 15:08:24
	 */
	public Map createNative(String out_trade_no, String total_fee);
	
	/** 
	
	 * @Title:        queryPayStatus 
	 * @Description:  查询支付订单的支付状态
	 * @param out_trade_no
	 * @return   
	 * @author        Muu_E-mail:369566919@qq.com
	 * @Date          2018-08-27 16:08:55
	 */
	public Map queryPayStatus(String out_trade_no);
}
