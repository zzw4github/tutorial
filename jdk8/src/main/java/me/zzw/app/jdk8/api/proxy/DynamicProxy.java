package me.zzw.app.jdk8.api.proxy;


import org.apache.log4j.Logger;
import org.junit.Test;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface Dao {
    long insert();
}

class DaoImpl implements Dao {
    @Override
    public long insert() {
        return 0;
    }
}

class MyInvocationHandler implements InvocationHandler {
    private Object object;

    public MyInvocationHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(object, args);
    }
}

/**
 * http://www.cnblogs.com/flyoung2008/archive/2013/08/11/3251148.html
 */
public class DynamicProxy {
    Logger logger = Logger.getLogger(DynamicProxy.class);
    @Test
    public void test() {
        Object object = Proxy.newProxyInstance(Dao.class.getClassLoader(),

                new Class[]{Dao.class},
                new MyInvocationHandler(new DaoImpl()));
        Dao proxy = (Dao)object;
        logger.info(Proxy.isProxyClass(object.getClass()));
        logger.info(Proxy.isProxyClass(proxy.getClass()));
        logger.info(Proxy.getInvocationHandler(proxy));
        logger.info( proxy.insert() );

    }


    @Test
    public  void createProxyClassFile()
    {
        String name = "ProxySubject";
        byte[] data = ProxyGenerator.generateProxyClass( name, new Class[] { Dao.class } );
        try
        {
            FileOutputStream out = new FileOutputStream( name + ".class" );
            out.write( data );
            out.close();
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }

}


