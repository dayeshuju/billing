package com.daye.common.util;

import javax.servlet.http.HttpServletRequest;

public class HttpUtils {
    public static boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }
}
