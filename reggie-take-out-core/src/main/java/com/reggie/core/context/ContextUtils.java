package com.reggie.core.context;

import org.springframework.core.NamedThreadLocal;

public class ContextUtils {

    private static final String CONTEXT = "context";

    private static final NamedThreadLocal<Context> contextContainer = new NamedThreadLocal<>(CONTEXT);

    public static void setContext(Context context) {
        contextContainer.set(context);
    }

    public static Context getContext() {
        return contextContainer.get();
    }

    //TODO 获取上下文中用户信息

    public static void clear() {
        contextContainer.remove();
    }

}
