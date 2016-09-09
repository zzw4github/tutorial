package me.zzw.app.jdk8.api.reflect;

import com.google.common.base.Strings;
import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * Created by infosea on 2016-07-28.
 */
public class ClassApi {
    Logger logger = Logger.getLogger(ClassApi.class);
    @Test
    public void classApi() {
        Object classApi1 =ClassApi.this;
        Class<? extends ClassApi> classApi2 = ClassApi.this.getClass();
        Class<ClassApi> classApi3 = ClassApi.class;
        try {
            Class<?> classApi4 = Class.forName("me.zzw.app.jdk8.api.reflect.ClassApi");
            logger.info(classApi2.getName());
            logger.info(classApi3.getName());
            logger.info(classApi4.getName());
            logger.info("getSimpleName = " + classApi4.getSimpleName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
