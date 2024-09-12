import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class XiwenContext {
    // 两级缓存解决循环依赖问题
    private final Map<String, Object> singletonObjects = new HashMap<>();
    private final Map<String, Object> earlySingletonMap = new HashMap<>();

    static Map<String, Class<?>> map = new HashMap<>();
    
    public void init() {
        for (String beanName : map.keySet()) {
            createBean(beanName);
        }
    }
    /**
     * 创建bean
     * @param beanName
     * @return
     */
    private Object createBean(String beanName) {
        Object singletonObject = null;
        try {
            Class<?> obj = map.get(beanName);
            //反射创建实例
            singletonObject = obj.getConstructor().newInstance();
            earlySingletonMap.put(beanName, singletonObject);
            populateBean(singletonObject);
            this.singletonObjects.put(beanName, singletonObject);
            return singletonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 自动注入依赖
     * @param bean
     */
    private void populateBean(Object bean) {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            // 判断是否有Autowired注解
            if (field.getAnnotation(XiwenAutowired.class) != null) {
                Object value = getBean(field.getName());
                try {
                    field.setAccessible(true);
                    field.set(bean, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 获取bean
     * 如果bean已经创建过，则直接返回，否则创建bean并返回
     * @param beanName
     * @return
     */
    public Object getBean(String beanName) {
        Object singletonObject = this.singletonObjects.get(beanName);
        if (singletonObject != null) {
            return singletonObject;
        }
        singletonObject = this.earlySingletonMap.get(beanName);
        if (singletonObject != null) {
            return singletonObject;
        }
        return createBean(beanName);
    }
}