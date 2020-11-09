package com.mola.molamod.utils;

import net.minecraft.crash.CrashReport;
import net.minecraft.util.ReportedException;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author : molamola
 * @Project: molamod
 * @Description:
 * @date : 2020-11-04 09:29
 **/
public class LoggerUtil {

    private static Logger logger = null;

    private static final AtomicBoolean hasBeenInit = new AtomicBoolean(false);

    public static void init(Logger logger) {
        if (!hasBeenInit.get()) {
            if (null == logger) {
                throw new ReportedException(new CrashReport("[molamola] logger can not be null!",
                        new NullPointerException()));
            }
            LoggerUtil.logger = logger;
            hasBeenInit.compareAndSet(false, true);
        }
    }

    public static Logger getLogger() {
        if (null == logger) {
            throw new ReportedException(new CrashReport("[molamola] can not resolve logger!",
                    new NullPointerException()));
        }
        return logger;
    }
}
