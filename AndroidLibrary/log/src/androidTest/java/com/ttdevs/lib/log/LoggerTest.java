package com.ttdevs.lib.log;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

/**
 * Logger 测试
 * <p>
 * Create by ttdevs
 * 2017-11-05 LikingfitLibrary
 * https://github.com/ttdevs
 */

@RunWith(AndroidJUnit4.class)
public class LoggerTest {
    private Context mAppContext;

    @Before
    public void setup() {
        mAppContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testLogger() {
        String json = "{\"string\":\" %d 这几日里天儿变脸快,仔细着还是着了风寒,今日倍感乏力,身子也越发疲累。私心想着若是连着歇息个两三日，闻花之芬芳，沐阳光之温存,既开阔思维，又可以培养情操,定可心情大佳，对以后的工作必定是极好的。\"}";
        for (int i = 0; i < 2000; i++) {
            TtdevsLogger.i(json);
            TtdevsLogger.d(json);
        }
    }

    @After
    public void tearDown() {
        mAppContext = null;
    }
}
