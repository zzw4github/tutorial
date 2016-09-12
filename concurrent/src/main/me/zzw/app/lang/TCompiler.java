package me.zzw.app.lang;

/**
 * Created by infosea on 2016-09-12.
 */
public class TCompiler {
    public static boolean compileClass(Class clazz){
        return Compiler.compileClass(clazz);
    }

    public static void main(String[] args){
        try {
            System.out.println(TCompiler.compileClass(Class.forName("me.zzw.app.lang.TCompiler")));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
