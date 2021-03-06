package org.dylan.springframework.factory;

import org.dylan.springframework.annotation.Autowired;
import org.dylan.springframework.annotation.Qualifier;
import org.dylan.springframework.annotation.Repository;
import org.dylan.springframework.annotation.Service;
import org.dylan.springframework.io.Loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Map;

/**
 * @author Jiacheng Huang
 * @date 2019/03/03 23:41
 */
public class AnnotationBeanFactory extends AbstractBeanFactory {

    public AnnotationBeanFactory(Loader loader) {
        try {
            loadConfig(loader);
            doScanner(basePackage);
            initIoC();
            inject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SpringIOCInitException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void inject() throws IllegalAccessException, SpringIOCInitException {

        for (Map.Entry<String, Object> entry : ioc.entrySet()) {

            Class clazz = entry.getValue().getClass();
            if (!clazz.isAnnotationPresent(Repository.class) && !clazz.isAnnotationPresent(Service.class)) {
                continue;
            }

            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {

                field.setAccessible(true);

                if (field.isAnnotationPresent(Autowired.class)) {
                    String beanName = lowerFirst(field.getType().getSimpleName().replace(".class", ""));

                    if (!containsBean(beanName)) {
                        throw new SpringIOCInitException("Bean " + beanName + " not exist");
                    }

                    field.set(entry.getValue(), ioc.get(beanName));
                } else if (field.isAnnotationPresent(Qualifier.class)) {
                    Qualifier qualifier = field.getAnnotation(Qualifier.class);
                    String beanName = qualifier.value();
                    if ("".equals(beanName)) {
                        beanName = lowerFirst(field.getType().getSimpleName().replace(".class", ""));
                    }

                    if (!containsBean(beanName)) {
                        throw new SpringIOCInitException("Bean " + beanName + " not exist");
                    }

                    field.set(entry.getValue(), ioc.get(beanName));
                }

            }

        }

    }

    private void initIoC() throws SpringIOCInitException, ClassNotFoundException, IllegalAccessException, InstantiationException {

        for (String className : classNames) {
            Class clazz = Class.forName(className);

            if (clazz.isAnnotationPresent(Repository.class)) {

                Repository repository = (Repository) clazz.getAnnotation(Repository.class);
                String beanName = repository.name();
                if ("".equals(beanName)) {
                    beanName = lowerFirst(clazz.getSimpleName().replace(".class", ""));
                }
                ioc.put(beanName, clazz.newInstance());

            } else if (clazz.isAnnotationPresent(Service.class)) {

                Service service = (Service) clazz.getAnnotation(Service.class);
                Class<?>[] interfaces = service.getClass().getInterfaces();
                for (Class<?> i : interfaces) {
                    String beanName = service.name();
                    if ("".equals(beanName)) {
                        beanName = lowerFirst(i.getSimpleName().replace(".class", ""));
                    }
                    if (ioc.containsKey(beanName)) {
                        throw new SpringIOCInitException("bean " + beanName + " already exists");
                    }
                    ioc.put(beanName, clazz.newInstance());
                }

            }
        }

    }

    private void doScanner(String basePackage) throws FileNotFoundException {
        String path = "/" + basePackage.replaceAll("\\.", "/");
        URL url = getClass().getResource(path);

        if (url == null) throw new FileNotFoundException("package " + path + " not exists");

        File dir = new File(url.getFile());

        for (File file : dir.listFiles()) {

            if (file.isDirectory()) {
                doScanner(basePackage + "." + file.getName());
            }

            if (!file.getName().endsWith(".class")) {
                continue;
            }
            classNames.add(basePackage + "." + file.getName().replace(".class", ""));

        }

    }

    private void loadConfig(Loader loader) {
        properties = loader.load();
        basePackage = properties.getProperty("ScanPackage");
    }

    public Object getBean(String beanName) {
        return ioc.get(beanName);
    }

    public boolean containsBean(String beanName) {
        return ioc.containsKey(beanName);
    }

    private String lowerFirst(String str) {
        char[] chars = str.toCharArray();
        chars[0] += 32;
        return new String(chars);
    }
}
