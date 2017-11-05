package com.ttdevs.lib.log;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * Created by ttdevs
 * 2017-11-05 (AndroidLibrary)
 * https://github.com/ttdevs
 */
public class TtdevsLogger {
    static {
        FormatStrategy androidStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)
                .tag("ttdevs")
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(androidStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return true;
            }
        });
    }

    public static void i(String message, Object... args) {
        Logger.i(message, args);
    }

    public static void d(String message, Object... args) {
        Logger.d(message, args);
    }

    public static void json(String json) {
        Logger.json(json);
    }
}
