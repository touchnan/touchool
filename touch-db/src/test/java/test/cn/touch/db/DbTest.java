/*
 * cn.touch.db.DbTest.java
 * Sep 15, 2011 
 */
package test.cn.touch.db;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cn.touch.db.Db;

/**
 * Sep 15, 2011
 * 
 * @author <a href="mailto:touchnan@gmail.com">chengqiang.han</a>
 * 
 */
public abstract class DbTest {
    protected static Db db;

    @Before
    public void before() {
        init();
    }

    protected abstract void init();

    @After
    public void down() {

    }

    @AfterClass
    public static void downClass() {

    }
    
    @Test
    public void testThreadSafe() throws InterruptedException, ExecutionException {
        List<Future<Boolean>> result = new ArrayList<Future<Boolean>>();
        ExecutorService exec = Executors.newFixedThreadPool(30);
        int count = 1000;
        for (int i = count; i > 0; i--) {
            result.add(exec.submit(new Task(i)));
        }
        exec.shutdown();
        while (!exec.isTerminated()) {
            Thread.yield();
        }

        for (int i = 0; i < result.size(); i++) {
            Assert.assertTrue(i + "行错误", (Boolean) result.get(i).get());
        }
    }

    class Task implements Callable<Boolean> {
        private int id;

        Task(int id) {
            this.id = id;
        }

        @Override
        public Boolean call() throws Exception {
            //db.update("INSERT INTO `tab_u` (`id`, `name`, `state`) VALUES (?, '红', 0)", id);
            db.update("INSERT INTO tab_u (id, name, state) VALUES (?, 红, 0)", id);
            return true;
        }

    }

}
