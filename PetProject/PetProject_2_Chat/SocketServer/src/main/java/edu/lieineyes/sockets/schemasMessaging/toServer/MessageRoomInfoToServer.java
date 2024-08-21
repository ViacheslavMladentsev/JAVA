package edu.lieineyes.sockets.schemasMessaging.toServer;

import edu.lieineyes.sockets.schemasMessaging.RoomSchema;
import lombok.Getter;


@Getter
public class MessageRoomInfoToServer extends edu.lieineyes.sockets.schemasMessaging.toServer.MessageToServerBase {

    RoomSchema roomInfo;

    public MessageRoomInfoToServer(String itemMenu, RoomSchema roomInfo) {
        super(itemMenu);
        this.roomInfo = roomInfo;
    }
}
