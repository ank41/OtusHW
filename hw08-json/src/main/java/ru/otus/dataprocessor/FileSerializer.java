package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class FileSerializer implements Serializer {
    private final String fileName;
    private final ObjectMapper mapper = new ObjectMapper();
    public FileSerializer(String fileName) {
        Objects.requireNonNull(fileName);
        this.fileName = fileName;}

    @Override
    public void serialize(Map<String, Double> data) {
        try {
            mapper.writeValue(new File(fileName), data);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
