package com.dmg.gameconfigserver.model.dto.lhj;

import lombok.Data;

/**
 * @author Administrator
 * 老虎机玩家派奖条件配置
 */
@Data
public class LhjPlayerPayLimitConfigDTO {
	/**
	 * 条件序号
	 */
	private int number;
	/**
	 * 累计输赢下限
	 */
	private double totalLowLimit;
	/**
	 * 当日输赢下限
	 */
	private double dayLowLimit;
	/**
	 * 累计流水下限
	 */
	private double totalWaterLow;
	/**
	 * 当日流水下限
	 */
	private double dayWaterLow;
	/**
	 * 累计赔率上限
	 */
	private double oddsTotalHight;
	/**
	 * 今日赔率上限
	 */
	private double dayOddsHight;

}
