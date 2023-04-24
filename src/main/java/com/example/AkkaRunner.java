package com.example;

import akka.actor.typed.ActorSystem;
import com.example.behavior.GreetingGuardian;
import com.example.message.SayHelloMessage;

import java.io.IOException;

import static java.lang.System.in;
import static java.lang.System.out;

public class AkkaRunner {
  private static final String ACTOR_SYSTEM_NAME = "hello-akka-actor-system";

  public static void main(String[] args) {
    // creating actor system
    final ActorSystem<SayHelloMessage> greeterMain = ActorSystem.create(GreetingGuardian.create(), ACTOR_SYSTEM_NAME);
    // sending message to the actor system
    greeterMain.tell(new SayHelloMessage("Charles"));

    try {
      out.println(">>> Press ENTER to exit <<<");
      in.read();
    } catch (IOException ignored) {
      // warning: empty catch
    } finally {
      greeterMain.terminate();
    }
  }
}
