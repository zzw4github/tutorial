package me.zzw.app.jdk8;

import com.google.common.collect.Lists;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by infosea on 2016-07-27.
 */
public class StreamApiTest {
    List<Integer> integerList ;
    List<Integer> hasNoNullIntegerList;
    @Before
    public void init(){
        integerList = Lists.newArrayList(1,2,3,null,5);
        hasNoNullIntegerList = Lists.newArrayList(1, 2, 3, 4, 5);
    }
    @Test
    public void first(){
        List<Integer> list = Lists.newArrayList(1,2,3,null,5);
        long numsCountIsNotNull =list.stream().filter(num -> num != null).count();
        Assert.assertEquals(numsCountIsNotNull, 4L);
    }

    @Test
    public void of(){
        Stream<Integer> integerStream = Stream.of(1,2,3,4);
        Stream<List<Integer>> listStream = Stream.of(new ArrayList<Integer>());
    }

    @Test
    public void generator(){
       Stream<Integer> integerStream = Stream.generate(new Supplier<Integer>() {
            @Override
            public Integer get() {
                return 1;
            }
        });

        Stream<Double> doubleStream = Stream.generate(() -> Math.random());
        Stream<Double > longStream = Stream.generate(Math::random);
    }

    @Test
    public void iterator() {
        Stream.iterate(1, item -> item + 1).limit(10).forEach(System.out::println);
    }

//    1. distinct: 对于Stream中包含的元素进行去重操作（去重逻辑依赖元素的equals方法），新生成的Stream中没有重复的元素；
    @Test
    public void distinct(){
        List<Integer> integerList = Lists.newArrayList(1,2,3,3,4,5);
        integerList.stream().distinct().forEach(System.out::println);
    }

//    2. filter: 对于Stream中包含的元素使用给定的过滤函数进行过滤操作，新生成的Stream只包含符合条件的元素；
    @Test
    public void filter(){
        integerList.stream().filter(num -> num != null).forEach(System.out::println);
        integerList.stream().filter(num -> num != null).forEach(num -> System.out.println(num));

    }

    /**
    3. map: 对于Stream中包含的元素使用给定的转换函数进行转换操作，新生成的Stream只包含转换生成的元素。
    这个方法有三个对于原始类型的变种方法，分别是：mapToInt，mapToLong和mapToDouble。这三个方法也比较好理解，
    比如mapToInt就是把原始Stream转换成一个新的Stream，这个新生成的Stream中的元素都是int类型。
    之所以会有这样三个变种方法，可以免除自动装箱/拆箱的额外消耗；
     **/
    @Test
    public void mapToInt(){
        integerList.stream().filter(num -> num != null).mapToInt(num -> num + 1).forEach(System.out::println);

    }

//    flatMap：和map类似，不同的是其每个元素转换得到的是Stream对象，会把子Stream中的元素压缩到父集合中；
    @Test
    public void flatMap(){
       integerList.stream().filter(num -> num != null).flatMap(new Function<Integer, Stream<String>>() {
                                                                    @Override
                                                                    public Stream<String> apply(Integer integer) {
                                                                        return Stream.of(String.valueOf(integer));
                                                                    }
                                                                }
        ).forEach(System.out::println);
    }

//    5. peek: 生成一个包含原Stream的所有元素的新Stream，同时会提供一个消费函数（Consumer实例），新Stream每个元素被消费的时候都会执行给定的消费函数；
    @Test
    public void peek(){
        integerList.stream().peek(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
               System.out.println("is null : " + (integer == null ? true:false));
            }
        }).forEach(System.out::println);
    }
//    6. limit: 对一个Stream进行截断操作，获取其前N个元素，如果原Stream中包含的元素个数小于N，那就获取其所有的元素；
    @Test
    public void limit() {
        Stream.iterate(1 , num -> num + 1).limit(10).forEach(System.out::println);
    }
//    7. skip: 返回一个丢弃原Stream的前N个元素后剩下元素组成的新Stream，如果原Stream中包含的元素个数小于N，那么返回空Stream；
    @Test
    public void skip() {
        Stream.iterate(1, num -> num + 1).limit(10).skip(2).forEach(System.out::println);
    }

    @Test
    public void connection(){
        hasNoNullIntegerList.stream().collect(
                () -> new ArrayList<Integer>(),
                (list, integer) -> list.add(integer),
                (list1, list2) -> list1.addAll(list2)
        ).forEach(System.out::println);
    }

    @Test
    public void connect2() {
        List<Integer> numsWithoutNull = integerList.stream().filter(num -> num != null).
        collect(Collectors.toList());
        Assert.assertEquals(4, numsWithoutNull.size());

    }

    @Test
    public void reduce1() {
        List<Integer> lists = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        System.out.println("sum of this integer list is " + lists.stream().reduce((sum, item) -> sum + item).get());
    }

    @Test
    public void reduce2() {
        List<Integer> lists = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        System.out.println("sum of this integer list is " + lists.stream().reduce(0,(sum, item) -> sum + item));

    }
}

