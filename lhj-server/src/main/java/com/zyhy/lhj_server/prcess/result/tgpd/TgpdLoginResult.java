/**
 * 
 */
package com.zyhy.lhj_server.prcess.result.tgpd;

import com.zyhy.common_server.result.HttpMessageResult;

/**
 * @author ASUS
 *
 */
public class TgpdLoginResult extends HttpMessageResult{

	private String uuid;
	
	private double gold;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public double getGold() {
		return gold;
	}

	public void setGold(double gold) {
		this.gold = gold;
	}
	
	
}
