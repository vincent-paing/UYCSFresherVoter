package aungkyawpaing.uyfresherwelcome.utils;

/**
 * Created by vincentpaing on 17/12/15.
 */

import android.util.Log;
import aungkyawpaing.uyfresherwelcome.BuildConfig;

public class L {
  public static void e(final String msg) {

    final Throwable t = new Throwable();
    final StackTraceElement[] elements = t.getStackTrace();

    final String callerClassName = elements[1].getClassName();
    final String callerMethodName = elements[1].getMethodName();

    if (BuildConfig.DEBUG) Log.e(callerClassName, "[" + callerMethodName + "] " + msg);
  }

  public static void i(final String msg) {

    final Throwable t = new Throwable();
    final StackTraceElement[] elements = t.getStackTrace();

    final String callerClassName = elements[1].getClassName();
    final String callerMethodName = elements[1].getMethodName();

    if (BuildConfig.DEBUG) Log.i(callerClassName, "[" + callerMethodName + "] " + msg);
  }

  public static void d(final String msg) {

    final Throwable t = new Throwable();
    final StackTraceElement[] elements = t.getStackTrace();

    final String callerClassName = elements[1].getClassName();
    final String callerMethodName = elements[1].getMethodName();

    if (BuildConfig.DEBUG) Log.d(callerClassName, "[" + callerMethodName + "] " + msg);
  }

  public static void w(final String msg) {

    final Throwable t = new Throwable();
    final StackTraceElement[] elements = t.getStackTrace();

    final String callerClassName = elements[1].getClassName();
    final String callerMethodName = elements[1].getMethodName();

    if (BuildConfig.DEBUG) Log.w(callerClassName, "[" + callerMethodName + "] " + msg);
  }
}
