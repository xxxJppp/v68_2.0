package com.dmg.game.record.model.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author liubo
 * @Description //TODO
 * @Date 10:16 2019/11/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameRecordDTO {
    /**
     * 游戏牌局时间
     */
    private Date gameDate;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 用户昵称
     */
    private String userName;
    /**
     * 游戏id
     */
    private Integer gameId;
    /**
     * 游戏名称
     */
    private String gameName;
    /**
     * 场次id
     */
    private Integer fileId;
    /**
     * 场次名称
     */
    private String fileName;
    /**
     * 游戏底分
     */
    private BigDecimal baseScore;
    /**
     * 游戏角色
     */
    private String role;
    /**
     * 叫分
     */
    private Integer callBranch;
    /**
     * 倍数
     */
    private String multiples;
    /**
     * 游戏结果
     */
    private String gameResult;
    /**
     * 游戏前金币
     */
    private BigDecimal beforeGameGold;
    /**
     * 游戏后金币
     */
    private BigDecimal afterGameGold;
    /**
     * 下注金币
     */
    private BigDecimal betsGold;
    /**
     * 输赢金币
     */
    private BigDecimal winLosGold;
    /**
     * 服务费
     */
    private BigDecimal serviceCharge;
}
