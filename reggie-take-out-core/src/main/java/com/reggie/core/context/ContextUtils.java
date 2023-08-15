package com.reggie.core.context;

import com.reggie.common.enums.HttpResultCode;
import com.reggie.common.exception.BizException;
import com.reggie.core.modular.auth.model.dto.LoginUser;
import org.springframework.core.NamedThreadLocal;

import java.util.Optional;

public class ContextUtils {

    private static final String CONTEXT = "context";

    private static final NamedThreadLocal<Context> contextContainer = new NamedThreadLocal<>(CONTEXT);

    public static void setContext(Context context) {
        contextContainer.set(context);
    }

    public static Context getContext() {
        return contextContainer.get();
    }

    public static LoginUser getCurrentUser() {
        return Optional.ofNullable(contextContainer.get())
                       .map(Context::getCurrentUser)
                       .orElse(null);
    }

    public static String getCurrentUserId() {
        return Optional.ofNullable(getCurrentUser())
                       .map(LoginUser::getId)
                       .orElseThrow(() -> new BizException(HttpResultCode.SYSTEM_ERROR, "当前用户ID获取失败！"));
    }

    public static void clear() {
        contextContainer.remove();
    }

}
