package br.com.joaojunio.cloudkeeper.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;

public class ObjectMapper {

    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    public static <O, D> D parseObject(O origin, Class<D> destination) {
        return mapper.map(origin, destination);
    }

    public static <O, D>List<D> parseListObjects(List<O> origins, Class<D> destination) {
        List<D> destinationsObjects = new ArrayList<>();
        for (O origin : origins) {
            destinationsObjects.add(mapper.map(origin, destination));
        }
        return destinationsObjects;
    }
}
