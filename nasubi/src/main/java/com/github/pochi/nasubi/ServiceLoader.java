package com.github.pochi.nasubi;

import static com.github.pochi.nasubi.utils.Exceptions.map;
import static com.github.pochi.nasubi.utils.Instantiator.instantiate;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import com.github.pochi.nasubi.utils.Exceptions;

public class ServiceLoader<T> {
    private List<String> list;

    ServiceLoader(Stream<String> stream){
        list = stream.collect(toList());
    }

    public Stream<T> toInstance(Function<Class<T>, T> instantiateFunction){
        return stream()
                .map(instantiateFunction::apply);
    }

    public Stream<T> toInstance(){
        return stream().map(clazz -> map(clazz, c -> instantiate(c)))
                .filter(Optional::isPresent)
                .map(Optional::get);
    }

    public Stream<Class<T>> stream(){
        return toClassStream()
                .filter(Optional::isPresent)
                .map(Optional::get);
    }

    private Stream<Optional<Class<T>>> toClassStream(){
        return list.stream()
                .map(this::toClass);
    }

    @SuppressWarnings("unchecked")
    private Optional<Class<T>> toClass(String className){
        return Exceptions.map(className, 
                item -> (Class<T>)Class.forName(item));
    }
}
