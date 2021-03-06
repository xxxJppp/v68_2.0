package com.dmg.fish.business.handler.room;

import java.io.UnsupportedEncodingException;

import com.dmg.fish.business.config.dic.FishRoomDic;
import com.dmg.fish.business.logic.FishMsgMgr;
import com.dmg.fish.business.model.room.Seat;
import com.dmg.gameconfigserverapi.fish.dto.FishRoomDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dmg.common.starter.rocketmq.annotation.RocketMQMessageListener;
import com.dmg.common.starter.rocketmq.core.listener.RocketMQListener;
import com.dmg.fish.business.logic.FishMgr;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "MAINTAIN_CONSUMER", topic = "MAINTAIN", tags = "MAINTAIN", messageModel = MessageModel.BROADCASTING)
public class MaintainListener implements RocketMQListener {

    @Value("${game-id}")
    private String gameId;

    @Autowired
    private FishMgr fishMgr;

    @Autowired
    private FishMsgMgr fishMsgMgr;

    @Override
    public void onMessage(MessageExt msg) {
        String data = null;
        try {
            data = new String(msg.getBody(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            log.error("消息解析异常{}", e);
            return;
        }
        if (StringUtils.isBlank(data)) {
            log.info("接收到的消息为空，不做任何处理");
            return;
        }
        log.warn("==>收到停服消息");

        if (StringUtils.equals(this.gameId, data)) {
            fishMgr.stopService();
            for (Seat seat : this.fishMgr.getPlayerSeats().values()) {
                if (seat.robot) {
                    continue;
                }
                long playerId = seat.playerId;
                this.fishMsgMgr.sendStopServerExitMsg(playerId);
            }
        }
    }
}
