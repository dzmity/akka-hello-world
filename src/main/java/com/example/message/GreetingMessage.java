package com.example.message;

import akka.actor.typed.ActorRef;

public record GreetingMessage(String whom, ActorRef<GreetingConfirmationMessage> replyTo) {
}
