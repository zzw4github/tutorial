package me.zzw.app.jdk8.api.reflect;

import org.apache.log4j.Logger;

/**
 * Created by infosea on 2016-07-28.
 */
public class MyObject extends MyObjectSuperClass implements MyObjectInterface {
    Logger logger = Logger.getLogger(MyObject.class);
    @Override
    public void testInterface() {
        logger.info("implement testInterface");
    }

    @Override
    public void testClass() {
       logger.info("override testClass");
    }
}
