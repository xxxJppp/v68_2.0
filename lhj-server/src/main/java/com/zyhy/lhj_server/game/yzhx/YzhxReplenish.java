package com.zyhy.lhj_server.game.yzhx;

import java.util.ArrayList;
import java.util.List;

import com.zyhy.common_lhj.BetInfo;

/**
 * @author DPC
 * @version 创建时间：2019年2月27日 上午10:18:13
 */
public class YzhxReplenish {
	//下注信息
	private BetInfo betInfo;
	//是否补充数据
	private boolean isReplenish;
	//补充的地图
//	private List<List<WindowInfo>> wininfo = new ArrayList<List<WindowInfo>>();
	//是否是免费游戏
	private boolean isFree;
	//掉落的次数
	private int number;
	//全是wild所在的列
	private List<Integer> wild = new ArrayList<Integer>();
	
	public YzhxReplenish(BetInfo betInfo){
		this.betInfo = betInfo;	
	}
	public YzhxReplenish(){
		
	}
	
	public boolean isReplenish() {
		return isReplenish;
	}
	public void setReplenish(boolean isReplenish) {
		this.isReplenish = isReplenish;
	}
//	public List<List<WindowInfo>> getWininfo() {
//		return wininfo;
//	}
//	public void setWininfo(List<List<WindowInfo>> wininfo) {
//		this.wininfo = wininfo;
//	}
	public BetInfo getBetInfo() {
		return betInfo;
	}
	public void setBetInfo(BetInfo betInfo) {
		this.betInfo = betInfo;
	}

	public boolean isFree() {
		return isFree;
	}

	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	public List<Integer> getWild() {
		return wild;
	}
	public void setWild(List<Integer> wild) {
		this.wild = wild;
	}	
}
