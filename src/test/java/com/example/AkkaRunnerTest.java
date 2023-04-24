package com.example;

import akka.actor.testkit.typed.javadsl.TestKitJunitResource;
import akka.actor.testkit.typed.javadsl.TestProbe;
import akka.actor.typed.ActorRef;
import com.example.behavior.Greeter;
import com.example.message.GreetingMessage;
import com.example.message.GreetingConfirmationMessage;
import org.junit.ClassRule;
import org.junit.Test;

public class AkkaRunnerTest {

    @ClassRule
    public static final TestKitJunitResource testKit = new TestKitJunitResource();

    @Test
    public void testGreeterActorSendingOfGreeting() {
        TestProbe<GreetingConfirmationMessage> testProbe = testKit.createTestProbe();
        ActorRef<GreetingMessage> underTest = testKit.spawn(Greeter.create(), "greeter");
        underTest.tell(new GreetingMessage("Charles", testProbe.getRef()));
        testProbe.expectMessage(new GreetingConfirmationMessage("Charles", underTest));
    }
}
