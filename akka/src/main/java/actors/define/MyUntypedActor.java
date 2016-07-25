package actors.define;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by infosea on 2016-07-21.
 */
public class MyUntypedActor extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void preStart() throws Exception {
       final ActorRef actor2 = getContext().actorOf(Props.create(MyUntypedActor2.class), "MyUntypedActor2" );
        actor2.tell("this is a message from 1 ", getSelf());
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if(message instanceof String) {
            log.info("Received String message: {}", message);
            getSender().tell("this is a message from 1", getSelf());
        }
    }
}
