package converters;

import domain.Requestt;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import repositories.RequesttRepository;

@Component
@Transactional
public class StringToRequestConverter implements Converter<String, Requestt> {

    @Autowired
    private RequesttRepository requestRepository;


    @Override
    public Requestt convert(final String text) {
        Requestt result;
        int id;

        try {
            if (StringUtils.isEmpty(text))
                result = null;
            else {
                id = Integer.valueOf(text);
                result = requestRepository.findOne(id);
            }
        } catch (final Throwable oops) {
            throw new IllegalArgumentException(oops);
        }
        return result;
    }
}
