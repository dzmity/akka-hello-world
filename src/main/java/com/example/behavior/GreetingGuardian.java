package com.example.behavior;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;
import com.example.message.GreetingConfirmationMessage;
import com.example.message.GreetingMessage;
import com.example.message.SayHelloMessage;

public class GreetingGuardian extends AbstractBehavior<SayHelloMessage> {
    private static final String GREETER_ACTOR_NAME = "greeter-actor";
    private static final int MAX_GREETING_COUNT = 3;

    private final ActorRef<GreetingMessage> greeter;

    private GreetingGuardian(ActorContext<SayHelloMessage> context) {
        super(context);
        // create an actor with provided behaviour and name
        greeter = context.spawn(Greeter.create(), GREETER_ACTOR_NAME);
    }

    public static Behavior<SayHelloMessage> create() {
        return Behaviors.setup(GreetingGuardian::new);
    }

    @Override
    public Receive<SayHelloMessage> createReceive() {
        return newReceiveBuilder()
                .onMessage(SayHelloMessage.class, this::onSayHello)
                .build();
    }

    private Behavior<SayHelloMessage> onSayHello(SayHelloMessage message) {
        // create an actor with provided behaviour and name
        ActorRef<GreetingConfirmationMessage> replyTo =
                getContext().spawn(GreeterBot.create(MAX_GREETING_COUNT), String.format("%s-actor", message.name()));
        greeter.tell(new GreetingMessage(message.name(), replyTo));
        return this;
    }
}
