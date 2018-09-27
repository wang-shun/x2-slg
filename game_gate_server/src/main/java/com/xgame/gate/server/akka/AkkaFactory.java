package com.xgame.gate.server.akka;

import akka.actor.ActorRef;
import akka.actor.Props;


/**
 * akka工厂
 * @author jacky.jiang
 *
 */
public class AkkaFactory {

    public static ActorRef createActorRef(String actorName, Class<?> cla) {
        return AkkaSystemFactory.getInstance().getActorSystem().actorOf(Props.create(cla), actorName);
    }
}
