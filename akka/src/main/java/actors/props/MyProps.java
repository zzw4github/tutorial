package actors.props;

import actors.define.MyUntypedActor;
import akka.actor.Props;
import akka.japi.Creator;

/**
 * Created by infosea on 2016-07-21.
 */
public  class MyProps {

    static class MyActorC implements Creator<MyActor> {
        @Override
        public MyActor create() {
            return new MyActor("...");
        }
    }

//    static class ParametricCreator<T extends MyActor> implements Creator<T> {
//        @Override public T create() {
//            // ... fabricate actor here
//        }
//    }

    Props props1 = Props.create(MyUntypedActor.class);
    Props props2 = Props.create(MyActor.class, "...");
    Props props3 = Props.create(new MyActorC());
}
