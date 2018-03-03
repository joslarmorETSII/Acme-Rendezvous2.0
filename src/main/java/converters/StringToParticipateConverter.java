package converters;

import domain.Participate;

import domain.Question;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import repositories.ParticipateRepository;

import javax.transaction.Transactional;

@Component
@Transactional
public class StringToParticipateConverter implements Converter<String,Participate> {

    @Autowired
    private ParticipateRepository participateRepository;

    @Override
    public Participate convert(final String text) {
        Participate result;
        int id;

        try {
            if (StringUtils.isEmpty(text))
                result = null;
            else {
                id = Integer.valueOf(text);
                result = participateRepository.findOne(id);
            }
        } catch (final Throwable oops) {
            throw new IllegalArgumentException(oops);
        }
        return result;

    }
}
