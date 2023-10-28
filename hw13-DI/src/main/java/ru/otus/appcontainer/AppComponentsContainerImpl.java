package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;


import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;


@SuppressWarnings("squid:S1068")
public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        try {
            processConfig(initialConfigClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void processConfig(Class<?> configClass) throws Exception {
        Object configClassInstance = configClass.getConstructor().newInstance();

        List<Method> methods = Arrays.stream(configClass.getMethods()).filter(method -> method.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparingInt(m -> m.getAnnotation(AppComponent.class).order())).toList();

        String componentName;
        Object component;
        for (Method method : methods) {
            componentName = getComponentName(method);
            checkDuplicate(componentName);
            component = method.invoke(configClassInstance, getArgs(method.getParameters()));

            appComponentsByName.put(componentName, component);
            appComponents.add(component);
        }
    }


    @Override
    @SuppressWarnings("unchecked")
    public <C> C getAppComponent(Class<C> componentClass) {
        List<Object> components = appComponents.stream().filter(component -> componentClass.isAssignableFrom(component.getClass())).toList();
        if (components.size() > 1) {
            throw new AppContainerException("getting component is present in container more then one instance");
        } else if (components.isEmpty()) {
            throw new AppContainerException("getting component is missing in container");
        }
        return (C) components.get(0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C> C getAppComponent(String componentName) {
        return (C) Optional.ofNullable(appComponentsByName.get(componentName)).orElseThrow(() -> new AppContainerException("getting component is absent in container"));
    }


    private String getComponentName(Method method) {
        return method.getAnnotation(AppComponent.class).name();
    }

    private void checkDuplicate(String componentName) {
        if (appComponentsByName.containsKey(componentName)) {
            throw new AppContainerException("duplicate component name: " + componentName);
        }
    }

    private Object[] getArgs(Parameter[] parameters) {
        Object[] args = new Object[parameters.length];
        for (int i = 0; i < args.length; i++) {
            args[i] = getAppComponent(parameters[i].getType());
        }
        return args;
    }


}
