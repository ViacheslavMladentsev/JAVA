package edu.lieineyes.sockets.schemasMessaging.toServer;

import edu.lieineyes.sockets.schemasMessaging.UserSchema;


public class MessageUserInfoToServer extends edu.lieineyes.sockets.schemasMessaging.toServer.MessageToServerBase {

    private final UserSchema user;


    public MessageUserInfoToServer(String itemMenu, UserSchema data) {
        super(itemMenu);
        this.user = data;
    }

}
