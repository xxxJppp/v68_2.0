package com.dmg.zhajinhuaserver.result;

/**
 * @Author: ChenHao
 * @Date: 2018/5/22 17:21
 */
public enum ResultEnum {
    PARAM_ERROR(1000,"参数错误"),
    SYSTEM_EXCEPTION(1001,"系统异常"),
    GET_USERINFO_FAIL(1002,"获取用户信息失败"),
    ACCOUNT_NOT_EXIST(1006,"账号不存在"),
    CANNOT_START_GAME(2000,"不能开始游戏"),
    ROOM_NO_EXIST(2001,"房间不存在"),
    SEAT_BE_USE(2002,"座位被抢占"),
    ROOM_NO_SEAT(2003,"房间已满"),
    ROOM_HAS_STARTED(2004,"房间已经开始游戏"),
    PLAYER_HAS_NO_SEAT(2005,"玩家不在座位上"),
    PLAYER_HAS_NO_MONEY(2006,"金币不足"),
    PLAYER_HAS_NOT_INROOM(2007,"玩家不在房间里"),
    PLAYER_ASK_WRONG(2008,"请求错误"),
    PLAYER_ASK_TOO_FAST(2009,"请求过于频繁"),
    PLAYER_HAS_PLAYING(2010,"玩家已经开始游戏"),
    ROOM_IS_GAME(2011,"房间在游戏中"),
    ROOM_HAS_FULL(2012,"房间已满"),
    PLAYER_HAS_ACTION_ERROR(2013,"当前动作无效"),
    PLAYER_HAS_NO_CARDS(2014,"玩家无手牌"),
    ROOM_HAS_EMPTY(2015,"暂无可用房间"),
    SUCCESS(1,"成功");
    private Integer code;
    private String msg;
    ResultEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
    public Integer getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
}
