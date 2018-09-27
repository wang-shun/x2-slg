package com.xgame.gate.server.akka;

import akka.actor.ActorSystem;

/**
 * akka 系统工厂
 * @author jacky.jiang
 *
 */
public class AkkaSystemFactory {

    public ActorSystem actorSystem;
    private static AkkaSystemFactory akkaSystemManager = new AkkaSystemFactory();

    public void start() {
        actorSystem = ActorSystem.create("gate-akka-system");
    }

    public void shutdown() {
        actorSystem.shutdown();
    }

    public static AkkaSystemFactory getInstance() {
        return akkaSystemManager;
    }

    public ActorSystem getActorSystem() {
        return actorSystem;
    }

}
