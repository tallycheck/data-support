package com.taoswork.tallycheck.dataservice;

import com.taoswork.tallycheck.general.solution.reflect.ClassUtility;
import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.*;
import java.util.*;

/**
 * Created by gaoyuan on 8/11/16.
 */
public class IDataServiceTest {
    private Class[] CheckParameterizedTypeList() {
        return new Class[]{
                Collection.class, Map.class, Set.class};
    }

    private void pickType(Collection<Class> types, Type check) {
        if (check instanceof ParameterizedType) {
            ParameterizedType pcheck = ((ParameterizedType) check);
            Class gcls = (Class) pcheck.getRawType();
            boolean checkParamType = false;
            for (Class checkParam : CheckParameterizedTypeList()) {
                if (checkParam.isAssignableFrom(gcls)) {
                    checkParamType = true;
                    break;
                }
            }
            if (checkParamType) {
                Type[] argTypes = pcheck.getActualTypeArguments();
                for (Type subcheck : argTypes) {
                    pickType(types, subcheck);
                }
            } else {
                types.add(gcls);
            }
        } else if (check instanceof Class) {
            types.add((Class) check);
        } else {
            Assert.fail();
        }
    }

    @Test
    public void testInterfaceDubboCompatible() {

        Class<IDataService> dsCls = IDataService.class;
        for (Method method : dsCls.getMethods()) {
            List<Class> types = new ArrayList<Class>();
            Type genericReturnType = method.getGenericReturnType();
            pickType(types, genericReturnType);
            for (Class pt : method.getParameterTypes()) {
                types.add(pt);
            }
            for (Class expTyp : method.getExceptionTypes()) {
                types.add(expTyp);
            }
            for (Class type : types) {
                boolean fail = false;
                try {
                    boolean compat = Serializable.class.isAssignableFrom(type);
                    if (!compat)
                        fail = true;
                    Constructor constructor = type.getConstructor(new Class[]{});
                    constructor.newInstance();
                } catch (NoSuchMethodException e) {
                    fail = true;
                } catch (InvocationTargetException e) {
                    fail = true;
                } catch (InstantiationException e) {
                    fail = true;
                } catch (IllegalAccessException e) {
                    fail = true;
                } finally {
                    if (fail) {
                        Assert.fail("Method: " + method + " Type Error: " + type + " (parameter/return type/exception type)");
                    }
                }
            }
        }
    }
}
