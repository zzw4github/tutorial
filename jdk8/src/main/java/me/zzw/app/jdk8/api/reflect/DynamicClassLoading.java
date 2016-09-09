package me.zzw.app.jdk8.api.reflect;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by infosea on 2016-07-28.
 */
class MyClassLoader extends ClassLoader {
    public MyClassLoader(ClassLoader parent) {
        super(parent);
    }
    
    public Class loadClass(String name) throws ClassNotFoundException {
        if(!"me.zzw.app.jdk8.api.reflect.MyObject".equals(name)) {
            return super.loadClass(name);
        }
        
        try {
         String url = "file:e:/tutorial/jdk8/target/classes/me/zzw/app/jdk8/api/reflect/MyObject.class";
            URL url1 = new URL(url);
            URLConnection connection = url1.openConnection();
            InputStream input = connection.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int data = input.read();
            while(data != -1) {
                buffer.write(data);
                data = input.read();
            }
            input.close();
            byte[] classData = buffer.toByteArray();
            return defineClass("me.zzw.app.jdk8.api.reflect.MyObject",
                    classData, 0, classData.length);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
public class DynamicClassLoading {
    public static Class<?>  loadClass(String className) throws ClassNotFoundException {
        ClassLoader classLoader = DynamicClassLoading.class.getClassLoader();
        return classLoader.loadClass(className);
    }

    @Test
    public void loadClass() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoader parentClassLoader = MyClassLoader.class.getClassLoader();
        MyClassLoader myClassLoader = new MyClassLoader(parentClassLoader);
        Class myObjectClass = myClassLoader.loadClass("me.zzw.app.jdk8.api.reflect.MyObject");

        MyObjectInterface object1 = (MyObjectInterface) myObjectClass.newInstance();
        MyObjectSuperClass object2 = (MyObjectSuperClass) myObjectClass.newInstance();

        //create new class loader so classes can be reloaded.
        myClassLoader = new MyClassLoader(parentClassLoader);
        myObjectClass = myClassLoader.loadClass("me.zzw.app.jdk8.api.reflect.MyObject");
        object1 = (MyObjectInterface)  myObjectClass.newInstance();
        object2 = (MyObjectSuperClass) myObjectClass.newInstance();

        object1.testInterface();
        object2.testClass();


    }


}
