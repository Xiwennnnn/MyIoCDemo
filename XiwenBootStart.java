import java.io.File;

public class XiwenBootStart {
    public static XiwenContext run(Class<?> clazz) {
        XiWenScan annotation = clazz.getAnnotation(XiWenScan.class);
        String[] paths = annotation.value();
        for (String path : paths) {
            File file = new File(path);
            if (file.exists()) {
                String[] files = file.list();
                for (String f : files) {
                    if (f.endsWith(".java")) {
                        String className = f.replace(".java", "").replace("/", ".");
                        try {
                            Class<?> c = Class.forName(className);
                            XiwenContext.map.put(className, c);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        XiwenContext ctx = new XiwenContext();
        ctx.init();
        return ctx;
    }
}
