package com.zyhy.lhj_server.game.nnyy.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.zyhy.common_lhj.WinLineInfo;
import com.zyhy.common_lhj.Window;
import com.zyhy.common_lhj.process.MainProcess;
import com.zyhy.common_server.result.HttpMessageResult;
import com.zyhy.common_server.result.MessageClient;
import com.zyhy.common_server.util.NumberTool;
import com.zyhy.lhj_server.prcess.result.nnyy.NnyyGameBetResult;
@Service
public class NnyyTest {
	private  String roleid = "802";
	private  String uuid = "1";
	@Autowired
	private MainProcess mainProcess;
	// 下注线数
	private   int line = 9;
	// 下注线注
	private   double lineBet = 5;
	// 下注次数
	private   int betCount = 100000;
	// 统计轮数	
	private  int round = 1;
	// 正常游戏次数
	private   int gameCount = 0;
	// 正常游戏中奖次数
	private   int totalCount = 0;
	// 正常游戏奖励
	private   double totalReward = 0;
	// wild的总奖励
	private   double wildreward = 0;
	// 免费游戏触发次数
	private   int freeCount = 0;
	// 免费游戏总次数
	private   int totoalFreeCount = 0;
	// 免费游戏奖励
	private   double freeGameReward = 0;
	// 正常游戏奖励信息(K：图标名称 V：K：中奖个数 V：中奖次数)
	private   Map<String, Map<Integer, Integer>> countmap = new HashMap<>();
	private   Map<String, Map<Integer, Double>> map = new HashMap<>();
	// wild游戏奖励信息
	private   Map<String, Map<Integer, Integer>> countfreeMap = new HashMap<>();
	private   Map<String, Map<Integer, Double>> freeMap = new HashMap<>();
	
	public  void initTest() throws IOException, InterruptedException {
		testOpenGame();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd _HH_mm");
		Date date = new Date(System.currentTimeMillis());
		String format = sdf.format(date);
		String path = "C:\\Users\\Administrator\\Desktop\\shuzhi\\nnyy\\1";
		String name = "nnyy-" + format +".txt";
		File file = new File(path, name);
		System.out.println(file.createNewFile());
		for (int i = 1; i <= round; i++) {
			String testLogin1 = testLogin();
			NnyyResult result = testAuto();
			String testLogin2 = testLogin();
			String filename = i + ".==========>\r\n";
			String content1 = "统计结果: " + result.toString() + "\r\n";
			String content2 = "不包含WILD统计结果: " + map.toString()+ "\r\n";
			String content3 = "不包含WILD次数统计: " + countmap.toString()+ "\r\n";
			String content4 = "包含WILD统计结果: " + freeMap.toString()+ "\r\n";
			String content5 = "包含WILD次数统计: " + countfreeMap.toString()+ "\r\n";
			String content6 = "游戏开始金币: " + testLogin1.toString()+ "\r\n";
			String content7 = "游戏结束金币: " + testLogin2.toString()+ "\r\n";
			String output = filename + content1 + content2 + content3 + content4 + content5 + content6 + content7;
			FileWriter fw = new FileWriter(file,true);    
			fw.write(output,0,output.length());   
			String clean = clean();
			fw.write(clean,0,clean.length());   
			fw.flush();
		}
	}
	public  String clean(){
		
		// 正常游戏次数
		gameCount = 0;
		// 正常游戏中奖次数
		totalCount = 0;
		// 正常游戏奖励
		totalReward = 0;
		// wild的总奖励
		wildreward = 0;
		// 免费游戏触发次数
		freeCount = 0;
		// 免费游戏总次数
		totoalFreeCount = 0;
		// 免费游戏奖励
		freeGameReward = 0;
		// 正常游戏奖励信息(K：图标名称 V：K：中奖个数 V：中奖次数)
		map.clear();
		// wild游戏奖励信息
		freeMap.clear();
		countmap.clear();
		countfreeMap.clear();
		String result =  "[清除数据成功!当前数据 : ]"
				+ "gameCount = " + gameCount
				+ ", totalCount = " + totalCount
				+ ", totalReward = " + totalReward
				+ ", wildreward = " + wildreward
				+ ", freeCount = " + freeCount
				+ ", totoalFreeCount = " + totoalFreeCount
				+ ", freeGameReward = " + freeGameReward
				+ ", map = " + map.size()
				+ ", freeMap = " + freeMap.size()
				+ ", countmap = " + countmap.size()
				+ ", countfreeMap = " + countfreeMap.size()
				+"\r\n"
				;
		return result;
	}
	
	/**
	 * 自动测试
	 * @return 
	 * @throws InterruptedException 
	 */
	public  NnyyResult testAuto() throws InterruptedException{
		// 统计结果
		NnyyResult yzhxResult = new NnyyResult();
		// 开始测试
		for (int i = 0; i < betCount; i++) {
			//Thread.sleep(20L);
			gameCount ++;
			System.out.println("目前自动运行次数: " + gameCount);
			NnyyGameBetResult result = testStartGame(line,lineBet);
			//System.out.println("正常游戏result: " + result);
			dataRecord(result);
			int poolGame = result.getPoolGame();
			//System.out.println("poolGame: " + poolGame);
			if (poolGame > 0) {
				freeCount ++;
				while (true) {
					JSONObject freeGame = freeGame();
					//System.out.println("奖池游戏result: " + freeGame);
					int status = freeGame.getIntValue("status");
					if (status == 2) {
						Double rewardcoin = freeGame.getDouble("rewardcoin");
						freeGameReward += rewardcoin;
						break;
					}
				}
			}
		}
		yzhxResult.setBetCount(betCount);
		yzhxResult.setTotalBet(lineBet*line*betCount);
		yzhxResult.setTotalCount(totalCount);
		yzhxResult.setTotalreward(totalReward);
		yzhxResult.setWildreward(wildreward);
		yzhxResult.setFreeCount(freeCount);
		yzhxResult.setTotoalFreeCount(totoalFreeCount);
		yzhxResult.setFreeGameReward(freeGameReward);
		yzhxResult.setGameCount(gameCount);
		yzhxResult.setTotalIncomelv((totalReward + freeGameReward + wildreward)/(lineBet*line*betCount));
		yzhxResult.setTotalRewardlv(NumberTool.divide(totalCount, betCount).doubleValue());
		System.out.println("统计结果: " + yzhxResult);
		System.out.println("不包含WILD统计结果: " + map);
		System.out.println("包含WILD统计结果: " + freeMap);
		return yzhxResult;
	}
	
	/**
	 * 测试老虎机登陆
	 * @return 
	 */
	public  String testLogin(){
		int messageid = 1200;
		
		MessageClient mc = new MessageClient();
		mc.setMessageid(messageid);
		mc.setUuid(uuid);
		mc.setSid("");
		mc.setType(0);
		Map<String, String> map = new HashMap<>();
		map.put("messageid", String.valueOf(messageid));
		map.put("roleid", roleid);
		map.put("uuid", uuid);
		mc.setParams(map);
		HttpMessageResult result = mainProcess.mainProcessMsg(mc);
		return JSONObject.toJSONString(result);
	}
	
	/**
	 * 测试老虎机界面返回
	 */
	public  void testOpenGame(){
		int messageid = 1201;
		
		MessageClient mc = new MessageClient();
		mc.setMessageid(messageid);
		mc.setUuid(uuid);
		mc.setSid("");
		mc.setType(0);
		Map<String, String> map = new HashMap<>();
		map.put("messageid", String.valueOf(messageid));
		map.put("roleid", roleid);
		map.put("uuid", uuid);
		mc.setParams(map);
		mainProcess.mainProcessMsg(mc);
	}
	
	
	/**
	 * 测试老虎机正常游戏
	 * @return 
	 */
	public  NnyyGameBetResult testStartGame(int line,double lineBet){
		int messageid = 1202;
		
		MessageClient mc = new MessageClient();
		mc.setMessageid(messageid);
		mc.setUuid(uuid);
		mc.setSid("");
		mc.setType(0);
		Map<String, String> map = new HashMap<>();
		map.put("messageid", String.valueOf(messageid));
		map.put("roleid", roleid);
		map.put("num", String.valueOf(line));
		map.put("jetton", String.valueOf(lineBet));
		map.put("uuid", uuid);
		mc.setParams(map);
		NnyyGameBetResult result = (NnyyGameBetResult) mainProcess.mainProcessMsg(mc);
		return result;
	}
	
	/**
	 * 奖池游戏
	 * @return 
	 */
	public  JSONObject freeGame(){
		int messageid = 1204;
		
		MessageClient mc = new MessageClient();
		mc.setMessageid(messageid);
		mc.setUuid(uuid);
		mc.setSid("");
		mc.setType(0);
		Map<String, String> map = new HashMap<>();
		map.put("messageid", String.valueOf(messageid));
		map.put("roleid", roleid);
		map.put("uuid", uuid);
		mc.setParams(map);
		HttpMessageResult result = mainProcess.mainProcessMsg(mc);
		return JSONObject.parseObject(JSONObject.toJSONString(result));
	}
	
	/**
	 * 正常游戏数据记录
	 * @param yzhxResult 
	 */
	public  void dataRecord(NnyyGameBetResult result){
		double rewardcoin = result.getRewardcoin();
		if (rewardcoin > 0) {
			totalCount += 1;
			List<WinLineInfo> winrouteinfos = result.getWinrouteinfos();
			for (WinLineInfo wl : winrouteinfos) {
				//System.out.println("wl" + wl);
				String icon = wl.getIcon().getName();
				int num = wl.getNum();
				//System.out.println("icon" + icon);
				//System.out.println("num" + num);
				Double reward = wl.getReward();
				List<Window> windows = wl.getWindows();
				//System.out.println("windows" + windows);
				int wild = 0;
				for (Window wl2 : windows) {
					String icon2 = wl2.getIcon().getName();
					//System.out.println("icon2" + icon2);
					if (icon2.equalsIgnoreCase("WILD")) {
						wild ++;
						//System.out.println("wl" + wl);
					}
					//System.out.println("wild" + wild);
				}
				if (wild > 0) {
					wildreward += reward;
					if (freeMap.containsKey(icon)) {
						Map<Integer, Double> map2 = freeMap.get(icon);
						if (map2.containsKey(num)) {
							map2.put(num, map2.get(num) + reward);
						} else {
							map2.put(num, reward);
						}
					} else{
						Map<Integer, Double> count = new HashMap<>();
						count.put(num, reward);
						freeMap.put(icon, count);
					}
					
					if (countfreeMap.containsKey(icon)) {
						Map<Integer, Integer> map2 = countfreeMap.get(icon);
						if (map2.containsKey(num)) {
							map2.put(num, map2.get(num) + 1);
						} else {
							map2.put(num, 1);
						}
					} else{
						Map<Integer, Integer> count = new HashMap<>();
						count.put(num, 1);
						countfreeMap.put(icon, count);
					}
				} else {
					totalReward += reward;
					if (map.containsKey(icon)) {
						Map<Integer, Double> map2 = map.get(icon);
						if (map2.containsKey(num)) {
							map2.put(num, map2.get(num) + reward);
						} else {
							map2.put(num, reward);
						}
					} else{
						Map<Integer, Double> count = new HashMap<>();
						count.put(num, reward);
						map.put(icon, count);
					}
					
					if (countmap.containsKey(icon)) {
						Map<Integer, Integer> map2 = countmap.get(icon);
						if (map2.containsKey(num)) {
							map2.put(num, map2.get(num) + 1);
						} else {
							map2.put(num, 1);
						}
					} else{
						Map<Integer, Integer> count = new HashMap<>();
						count.put(num, 1);
						countmap.put(icon, count);
					}
				}
			}
		}
	}
}
