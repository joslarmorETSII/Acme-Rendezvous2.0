package converters;

import domain.Servise;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import repositories.ServiseRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToServiseConverter implements Converter<String, Servise> {

    @Autowired
    private ServiseRepository serviseRepository;

    @Override
    public Servise convert(final String text) {
        Servise result;
        int id;

        try {
            if (StringUtils.isEmpty(text))
                result = null;
            else {
                id = Integer.valueOf(text);
                result = serviseRepository.findOne(id);
            }
        } catch (final Throwable oops) {
            throw new IllegalArgumentException(oops);
        }
        return result;
    }
}
