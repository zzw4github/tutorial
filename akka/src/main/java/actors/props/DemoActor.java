package actors.props;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Creator;

/**
 * Created by infosea on 2016-07-21.
 * Recommended Practices
 */
public class DemoActor extends UntypedActor {

    public static Props props(final int magicNumber) {
        return Props.create(new Creator<DemoActor>() {
            private static final long serialVersionUID = 1L;
            @Override
            public DemoActor create() {
                return new DemoActor(magicNumber);
            }
        });
    }

    final int magicNumber;

    public DemoActor(int magicNumber) {
        this.magicNumber = magicNumber;
    }

    @Override
    public void onReceive(Object msg) {

    }

    public ActorRef getDemoActorRef(String... args){
       return getContext().system().actorOf(DemoActor.props(43), "demo");
    }
}
