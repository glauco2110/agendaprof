package br.com.agendaprof.core.infra.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Utils {

    public static Type[] getTypes(Object object) {
        ParameterizedType parameterizedType;

        try {
            parameterizedType = (ParameterizedType) object.getClass().getGenericSuperclass();

        } catch (ClassCastException e) {
            try {
                parameterizedType = (ParameterizedType) Class.forName(object.getClass().getGenericSuperclass().getTypeName()).getGenericSuperclass();

            } catch (ClassNotFoundException classNotFountexception) {
                throw new RuntimeException(classNotFountexception);
            }

        }

        return parameterizedType.getActualTypeArguments();
    }
}
