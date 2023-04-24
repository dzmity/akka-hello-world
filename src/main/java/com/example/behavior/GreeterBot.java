package com.example.behavior;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;
import com.example.message.GreetingMessage;
import com.example.message.GreetingConfirmationMessage;

public class GreeterBot extends AbstractBehavior<GreetingConfirmationMessage> {
    private final int max;
    private int greetingCounter;

    public static Behavior<GreetingConfirmationMessage> create(int max) {
        return Behaviors.setup(context -> new GreeterBot(context, max));
    }

    private GreeterBot(ActorContext<GreetingConfirmationMessage> context, int max) {
        super(context);
        this.max = max;
    }

    @Override
    public Receive<GreetingConfirmationMessage> createReceive() {
        return newReceiveBuilder()
                .onMessage(GreetingConfirmationMessage.class, this::onGreeted)
                .build();
    }

    private Behavior<GreetingConfirmationMessage> onGreeted(GreetingConfirmationMessage message) {
        greetingCounter++;
        getContext().getLog().info("Greeting {} for {}", greetingCounter, message.whom());
        if (greetingCounter == max) {
            return Behaviors.stopped();
        } else {
            message.from().tell(new GreetingMessage(message.whom(), getContext().getSelf()));
            return this;
        }
    }
}
