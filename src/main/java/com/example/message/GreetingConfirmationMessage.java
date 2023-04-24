package com.example.message;

import akka.actor.typed.ActorRef;

public record GreetingConfirmationMessage(String whom, ActorRef<GreetingMessage> from) {
}
