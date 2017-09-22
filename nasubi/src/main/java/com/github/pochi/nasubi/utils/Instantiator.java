package com.github.pochi.nasubi.utils;

public class Instantiator {
    private static final Instantiator INSTANCE = new Instantiator();

    public static <T> T instantiate(Class<T> c) {
        return Exceptions.map(c, clazz -> INSTANCE.instantiateClass(clazz))
                .orElse(null);
    }

    private <T> T instantiateClass(Class<T> c) throws Exception {
        return c.getDeclaredConstructor()
                .newInstance();
    }

}
