package com.zyhy.lhj_server.game.hjws;

import com.zyhy.common_lhj.BetInfo;

/**
 * @author ASUS
 *
 */
public class HjwsScatterInfo {

	//下注信息
	private BetInfo betInfo;
	//免费次数
	private int num;
	// 下注档位
	private int betLine;
	//免费游戏模式0未选择 1 AMBER模式 2 TROY模式 3 MICHAEL模式 4 SARAH模式
	private int model;
	//总奖金
	private double gold;
	
	public HjwsScatterInfo(){}
	
	public HjwsScatterInfo(BetInfo betInfo){
		this.betInfo = betInfo;
	}

	public BetInfo getBetInfo() {
		return betInfo;
	}

	public void setBetInfo(BetInfo betInfo) {
		this.betInfo = betInfo;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public double getGold() {
		return gold;
	}

	public void setGold(double gold) {
		this.gold = gold;
	}
	public int getModel() {
		return model;
	}

	public void setModel(int model) {
		this.model = model;
	}

	public int getBetLine() {
		return betLine;
	}

	public void setBetLine(int betLine) {
		this.betLine = betLine;
	}

	@Override
	public String toString() {
		return "HjwsScatterInfo [betInfo=" + betInfo + ", num=" + num + ", betLine=" + betLine + ", model=" + model
				+ ", gold=" + gold + "]";
	}

	
}
