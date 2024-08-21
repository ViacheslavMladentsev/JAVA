package edu.lieineyes.sockets.services.message;

import java.util.List;


public interface MessageService<T> {

    List<T> getLastThirtyMessage(String nameRoom);

}
