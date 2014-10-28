package org.theeuropeanlibrary.maia.converter.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.theeuropeanlibrary.maia.common.converter.Converter;
import org.theeuropeanlibrary.maia.common.converter.ConverterException;
import org.theeuropeanlibrary.maia.common.definitions.Dataset;

/**
 * This class converts datasets from and to json.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 17.10.2014
 */
public class DatasetEntityJsonConverter implements Converter<String, Dataset> {

    private final ObjectMapper mapper;

    public DatasetEntityJsonConverter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Class<Dataset> getDecodeType() {
        return Dataset.class;
    }

    @Override
    public Class<String> getEncodeType() {
        return String.class;
    }

    @Override
    public String encode(Dataset data) throws ConverterException {
        try {
            return mapper.writeValueAsString(data);
        } catch (JsonProcessingException ex) {
            throw new ConverterException("Cannot write record to string!", ex);
        }
    }

    @Override
    public Dataset decode(String data) throws ConverterException {
        try {
            return mapper.readValue(data, Dataset.class);
        } catch (IOException ex) {
            throw new ConverterException("Cannot read record from string!", ex);
        }
    }
}
