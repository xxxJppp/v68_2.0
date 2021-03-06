package com.dmg.gameconfigserver.model.dto.sys;

import com.dmg.gameconfigserver.model.dto.PageReqDTO;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author liubo
 * @Description //TODO
 * @Date 18:02 2019/11/6
 */
@Data
public class SysRolePageDTO extends PageReqDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    /**
     * 名称
     */
    private String name;
}
