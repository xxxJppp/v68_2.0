package com.dmg.redblackwarserver.service.logic.impl;

import java.math.BigDecimal;

import com.dmg.server.common.util.GameOnlineChangeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dmg.redblackwarserver.common.model.BasePlayer;
import com.dmg.redblackwarserver.common.model.BaseRobot;
import com.dmg.redblackwarserver.common.result.MessageResult;
import com.dmg.redblackwarserver.common.result.ResultEnum;
import com.dmg.redblackwarserver.manager.RoomManager;
import com.dmg.redblackwarserver.model.Room;
import com.dmg.redblackwarserver.model.Seat;
import com.dmg.redblackwarserver.model.dto.RoomInfoDTO;
import com.dmg.redblackwarserver.service.cache.PlayerService;
import com.dmg.redblackwarserver.service.cache.RobotCacheService;
import com.dmg.redblackwarserver.service.logic.JoinRoomService;
import com.dmg.redblackwarserver.service.logic.RoomActionService;
import com.dmg.redblackwarserver.tcp.server.MessageIdConfig;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author mice
 * @Date 2019/8/1 10:41
 * @Version V1.0
 **/
@Service
@Slf4j
public class JoinRoomServiceImpl implements JoinRoomService {

    @Autowired
    private PlayerService playerCacheService;
    @Autowired
    private RobotCacheService robotCacheService;
//    @Autowired
//    private StartGameService startGameService;
    @Autowired
    private RoomActionService roomActionService;

    @Override
    public synchronized void playerJoinRoom(int userId, int level) {
        BasePlayer basePlayer = this.playerCacheService.getPlayer(userId);
        Room room;
        Seat selfSeat = new Seat();
        if (RoomManager.intance().getGoldLimitMap().get(level + "") > basePlayer.getGold().doubleValue()) {
            basePlayer.push(MessageIdConfig.JOIN_ROOM, ResultEnum.PLAYER_HAS_NO_MONEY.getCode());
            return;
        }
        if (basePlayer.getRoomId() > 0) {
            room = RoomManager.intance().getRoom(basePlayer.getRoomId());
            if (room == null) {
                room = this.filterRoom(level);
                this.initSelfSeat(room, selfSeat, basePlayer);
            } else {
                selfSeat = RoomManager.intance().getSeat(room, userId);
                if (selfSeat == null) {
                    selfSeat = new Seat();
                    this.initSelfSeat(room, selfSeat, basePlayer);
                }
            }
        } else {
            room = this.filterRoom(level);
            this.initSelfSeat(room, selfSeat, basePlayer);
        }
        this.playerCacheService.updatePlayer(basePlayer);
        RoomInfoDTO roomInfoDTO = new RoomInfoDTO();
        BeanUtils.copyProperties(room, roomInfoDTO);
        roomInfoDTO.setSelfSeat(selfSeat);
        roomInfoDTO.setRoomLevel(room.getLevel());
        MessageResult messageResult = new MessageResult(MessageIdConfig.JOIN_ROOM, roomInfoDTO);
        basePlayer.push(messageResult);
        GameOnlineChangeUtils.incOnlineNum(Integer.parseInt(RoomManager.intance().getGameId()),room.getLevel());
    }

    @Override
    public synchronized void robotJoinRoom() {
        RoomManager.intance().getRoomMap().values().forEach(room -> {
            // TODO 机器人加入策略
            if (room.getPlayerNumber() < 50 && RandomUtil.randomInt(100) > 60) {
                int userId = RandomUtil.randomInt(31, 300) + 101000;
//                int userId = RandomUtil.randomInt(200) + 1000;
                log.info("robotId:{}", userId);
                BaseRobot robot = this.robotCacheService.getRobot(userId);
                if (robot.getRoomId() == 0) {
                    robot.setPlayRoundLimit(RandomUtil.randomInt(3, 6));
                    if (room.getLevel() == 1) {
                        robot.setGold(new BigDecimal(RandomUtil.randomInt(50000, 200000)).multiply(RoomManager.intance().getExchangeRate()));
                    } else {
                        robot.setGold(new BigDecimal(RandomUtil.randomInt(500000, 2000000)).multiply(RoomManager.intance().getExchangeRate()));
                    }
                    this.initSelfSeat(room, new Seat(), robot);
                    log.info("==>机器人:{},加入房间:{}", robot.getNickname(), room.getRoomId());
                    this.robotCacheService.update(robot);
                }
            }
        });
    }

    /**
     * @description: 过滤房间
     * @param
     * @return com.dmg.Room
     * @author mice
     * @date 2019/7/31
     */
    private Room filterRoom(int roomLevel) {
        for (Room room : RoomManager.intance().getRoomMap().values()) {
            // TODO 过滤策略
            if (room.getLevel() == roomLevel && room.getPlayerNumber() < room.getTotalPlayer()) {
                return room;
            }
        }
        Room room = RoomManager.intance().createRoom(roomLevel);
        this.roomActionService.action(room.getRoomId());
        return room;
    }

    /**
     * @description: 初始化座位信息
     * @param room
     * @param selfSeat
     * @param basePlayer
     * @return void
     * @author mice
     * @date 2019/7/31
     */
    private void initSelfSeat(Room room, Seat selfSeat, BasePlayer basePlayer) {
        selfSeat.setPlayer(basePlayer);
        int lastSeatIndex = 0;
        synchronized (room) {
            if (!(basePlayer instanceof BaseRobot)) {
                for (Seat seat : room.getSeatMap().values()) {
                    if (seat.getPlayer().getUserId() == basePlayer.getUserId()) {
                        basePlayer.setRoomId(room.getRoomId());
                        if (!(basePlayer instanceof BaseRobot)) {
                            this.playerCacheService.syncRoom(room.getLevel(),room.getRoomId(), basePlayer.getUserId());
                        }
                        basePlayer.setSeatIndex(seat.getSeatIndex());
                        return;
                    }
                }
            }
            if (room.getSeatMap().size() > 0) {
//                lastSeatIndex = room.getSeatMap().get(room.getSeatMap().lastKey()).getSeatIndex();
                lastSeatIndex = Integer.parseInt(room.getSeatMap().lastKey());
            }
            selfSeat.setSeatIndex(lastSeatIndex + 1);
            room.getSeatMap().put(selfSeat.getSeatIndex() + "", selfSeat);
            room.setPlayerNumber(room.getSeatMap().size());
            basePlayer.setRoomId(room.getRoomId());
            if (!(basePlayer instanceof BaseRobot)) {
                this.playerCacheService.syncRoom(room.getLevel(),room.getRoomId(), basePlayer.getUserId());
            }
            basePlayer.setSeatIndex(selfSeat.getSeatIndex());
        }
    }
}