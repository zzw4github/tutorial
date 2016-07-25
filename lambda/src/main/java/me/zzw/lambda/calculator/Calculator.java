package me.zzw.lambda.calculator;

/**
 * Created by infosea on 2016-07-21.
 * @desc an example of lambda expressions that take more than one formal parameter
 */
public class Calculator {
    interface IntegerMath {
        int operation(int a, int b);
    }

    public int operateBinary(int a, int b, IntegerMath op) {
        return op.operation(a, b);
    }

    public static void main(String... args) {
        Calculator app = new Calculator();
        IntegerMath addition = (a, b) -> a + b;
        IntegerMath subtraction = (a, b) -> a - b;
        System.out.println("40 + 2 = " + app.operateBinary(40, 2, addition));
        System.out.println("40 - 2 = " + app.operateBinary(40, 2, subtraction));
    }
}

