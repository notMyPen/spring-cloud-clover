package rrx.cnuo.cncommon.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * 两个含有相同字段名对象属性值的复制（不要使用Apache的那个了，那个效率低）
 * @author xuhongyu
 * @date 2019年8月25日
 */
public class CopyProperityUtils {
	
    public static void copyProperiesIgnoreNull(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    public static void copyAllProperies(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }

    public static void copyPropertiesWithIgnore(Object source, Object target, String...ignoreProperties) {
        BeanUtils.copyProperties(source, target, ignoreProperties);
    }

    public static String[] getNullPropertyNames(Object source) {
        List<String> nullPropertyNames = new ArrayList<String>();
        BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        PropertyDescriptor[] dess = wrappedSource.getPropertyDescriptors();
        if (null != dess && dess.length > 0) {
            for (int i = 0; i < dess.length; i++) {
                PropertyDescriptor des = dess[i];
                if (null == des) continue ;
                String propertyName = des.getName();
                if (null == wrappedSource.getPropertyValue(propertyName)) {
                    nullPropertyNames.add( propertyName );
                }

            }
        }
        return nullPropertyNames.toArray(new String[nullPropertyNames.size()]);
    }

    /**
     * 获取某个实体内值不为空的属性(该方法还可用于判断某参数vo中是否所有参数都为空)
     * @author xuhongyu
     * @param source
     * @return
     */
    public static List<String> getNotNullPropertyNames(Object source) {
        List<String> notNullPropertyNames = new ArrayList<String>();
        BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        PropertyDescriptor[] dess = wrappedSource.getPropertyDescriptors();
        if (null != dess && dess.length > 0) {
            for (int i = 0; i < dess.length; i++) {
                PropertyDescriptor des = dess[i];
                if (null == des) continue ;
                String propertyName = des.getName();
                if (null != wrappedSource.getPropertyValue(propertyName)) {
                    notNullPropertyNames.add(propertyName);
                }
            }
        }
        return notNullPropertyNames;
    }
}
