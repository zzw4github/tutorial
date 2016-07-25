package actors.props;

import akka.actor.UntypedActor;

/**
 * Created by infosea on 2016-07-21.
 */
public class MyActor extends UntypedActor {
    final String name;

    public MyActor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void onReceive(Object message) throws Throwable {

    }
}
