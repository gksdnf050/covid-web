package com.covid.web.util;


import com.covid.web.model.type.Code;

import java.util.EnumSet;

public class EnumUtils {

    public static <E extends Enum<E> & Code<T>, T> E lookupFromCode(Class<E> type, T code) {
        for (E e : EnumSet.allOf(type)) {
            if (e.getCode().equals(code))
                return e;
        }
        return null;
    }

    private EnumUtils() { /*empty*/}
}
