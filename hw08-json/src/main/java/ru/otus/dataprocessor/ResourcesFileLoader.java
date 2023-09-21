package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.model.Measurement;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class ResourcesFileLoader implements Loader {
    private final String fileName;
    private final ObjectMapper mapper =  new ObjectMapper();
    public ResourcesFileLoader(String fileName) {
        this.fileName = requireNonNull(fileName);
    }

    @Override
    public List<Measurement> load() {
        // читает файл, парсит и возвращает результат
        try (var is = getClass().getClassLoader().getResourceAsStream(fileName);
             MappingIterator<Measurement> iterator = mapper.readerFor(Measurement.class).readValues(is)) {

            return iterator.readAll()
                    .stream()
                    .map(data -> new Measurement(data.name(), data.value()))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
