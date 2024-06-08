package edu.school21.sockets.schemasMessaging.toServer;

import edu.school21.sockets.schemasMessaging.UserSchema;


public class MessageUserInfoToServer extends MessageToServerBase {

    private final UserSchema user;


    public MessageUserInfoToServer(String itemMenu, UserSchema data) {
        super(itemMenu);
        this.user = data;
    }

}
