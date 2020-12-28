package com.lotus.stage01.demo01;

import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * ThreadLocal：Java8 以前解决线程安全问题的方式
 *
 * Java8以前使用的处理日期和线程的类：java.text.SimpleDateFormat/java.util.Date 作为多线程共享变量时存在线程安全问题
 *
 * 测试：将"2016-12-18"解析成Date后，添加到集合并遍历
 *
 * @author lotus
 * @create 2020-12-26 20:45
 */
public class ThreadLocalTest {

    // SimpleDateFormat 存在线程安全问题
    @Test
    public void test1() throws ExecutionException, InterruptedException {

        // 多线程操作共享变量sdf，SimpleDateFormat的方法都是未上锁的（没有用synchronized，也没有用reentLock手动上锁），存在线程安全问题
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Future<Date>> list = new ArrayList<>();

        Callable<Date> task = new Callable<Date>() {
            @Override
            public Date call() throws Exception {
                return sdf.parse("2016-12-18");
            }
        };

        ExecutorService pool = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            Future<Date> dateFuture = pool.submit(task);
            list.add(dateFuture);
        }

        for (Future<Date> df : list) {
            System.out.println(df.get());
        }

        pool.shutdown();
    }

    // 使用ThreadLocal解决Date的线程安全问题（Java8以前解决线程安全问题的方式）
    @Test
    public void test2() throws ExecutionException, InterruptedException {
        List<Future<Date>> list = new ArrayList<>();

        Callable<Date> task = new Callable<Date>(){
            @Override
            public Date call() throws Exception {
                return DateFormatThreadLocal.covert("2016-12-18");
            }
        };

        ExecutorService pool = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            Future<Date> dateFuture = pool.submit(task);
            list.add(dateFuture);
        }

        for (Future<Date> fd : list) {
            System.out.println(fd.get());
        }

        pool.shutdown();
    }
}

class DateFormatThreadLocal {

    public static final ThreadLocal<DateFormat> THREAD_LOCAL = new ThreadLocal<DateFormat>(){
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    public static Date covert(String dateStr) throws ParseException {
        return THREAD_LOCAL.get().parse(dateStr);
    }
}