package com.example.behavior;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;
import com.example.message.GreetingMessage;
import com.example.message.GreetingConfirmationMessage;

public class Greeter extends AbstractBehavior<GreetingMessage> {

  public static Behavior<GreetingMessage> create() {
    return Behaviors.setup(Greeter::new);
  }

  private Greeter(ActorContext<GreetingMessage> context) {
    super(context);
  }

  @Override
  public Receive<GreetingMessage> createReceive() {
    return newReceiveBuilder()
            .onMessage(GreetingMessage.class, this::onGreet)
            .build();
  }

  private Behavior<GreetingMessage> onGreet(GreetingMessage command) {
    getContext().getLog().info("Hello {}!", command.whom());
    ActorRef<GreetingMessage> from = getContext().getSelf();
    command.replyTo().tell(new GreetingConfirmationMessage(command.whom(), from));
    return this;
  }
}