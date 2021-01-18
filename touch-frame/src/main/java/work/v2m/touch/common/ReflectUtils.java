package work.v2m.touch.common;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by <a href="mailto:88052350@qq.com">chengqiang.han</a> on 2021/1/18.
 */
public class ReflectUtils {
    public static Type[] getTypeParams(Class<?> klass) {
        if (klass == null)
            return null;
        Type superclass = klass.getGenericSuperclass();
        if (superclass == null || "java.lang.Object".equals(superclass.toString()))
            return null;
        if (superclass instanceof ParameterizedType)
            return ((ParameterizedType) superclass).getActualTypeArguments();
//        return getTypeParams(klass.getSuperclass());
        throw new IllegalArgumentException(
             "Not found ParameterizedType " + klass.getClass() );
    }
}
