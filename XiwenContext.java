import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class XiwenContext {
    private final Map<String, Object> singletonObjects = new HashMap<>();
    private final Map<String, Object> earlySingletonMap = new HashMap<>();

    static Map<String, Class<?>> map = new HashMap<>();
    
    public void init() {
        for (String beanName : map.keySet()) {
            createBean(beanName);
        }
    }

    private Object createBean(String beanName) {
        Object singletonObject = null;
        try {
            Class<?> obj = map.get(beanName);
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

    private void populateBean(Object bean) {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
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