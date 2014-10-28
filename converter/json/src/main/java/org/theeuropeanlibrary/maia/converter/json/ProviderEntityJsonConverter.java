package org.theeuropeanlibrary.maia.converter.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.theeuropeanlibrary.maia.common.converter.Converter;
import org.theeuropeanlibrary.maia.common.converter.ConverterException;
import org.theeuropeanlibrary.maia.common.definitions.Provider;

/**
 * This class converts records from and to xml.
 *
 * @author Markus Muhr (markus.muhr@theeuropeanlibrary.org)
 * @since 17.10.2014
 */
public class ProviderEntityJsonConverter implements Converter<String, Provider> {

    private final ObjectMapper mapper;

    public ProviderEntityJsonConverter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Class<Provider> getDecodeType() {
        return Provider.class;
    }

    @Override
    public Class<String> getEncodeType() {
        return String.class;
    }

    @Override
    public String encode(Provider data) throws ConverterException {
        try {
            return mapper.writeValueAsString(data);
        } catch (JsonProcessingException ex) {
            throw new ConverterException("Cannot write record to string!", ex);
        }
    }

    @Override
    public Provider decode(String data) throws ConverterException {
        try {
            return mapper.readValue(data, Provider.class);
        } catch (IOException ex) {
            throw new ConverterException("Cannot read record from string!", ex);
        }
    }
}
