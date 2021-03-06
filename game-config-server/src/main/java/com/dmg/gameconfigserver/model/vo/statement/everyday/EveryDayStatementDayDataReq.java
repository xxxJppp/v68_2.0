package com.dmg.gameconfigserver.model.vo.statement.everyday;

import java.util.Date;

import com.dmg.gameconfigserver.model.dto.PageReqDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 每日报表_每日数据_请求
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class EveryDayStatementDayDataReq extends PageReqDTO {
    /** 查询起始时间(包含) */
    private Date startDate;
    /** 查询结束时间(包含) */
    private Date endDate;
}