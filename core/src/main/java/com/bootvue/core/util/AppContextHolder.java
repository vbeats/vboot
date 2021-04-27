package com.bootvue.core.util;

import com.bootvue.core.modle.UserInfo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class AppContextHolder implements Serializable {
    private static final long serialVersionUID = -2281938478239620096L;
    private static final ThreadLocal<UserInfo> THREAD_LOCAL = new ThreadLocal<>();


    public static void setUserInfo(UserInfo userInfo) {
        THREAD_LOCAL.set(userInfo);
    }

    public static UserInfo getUserInfo() {
        return THREAD_LOCAL.get();
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }

}
