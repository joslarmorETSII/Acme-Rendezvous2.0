package converters;

import domain.Rendezvous;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class RendezvousToStringConverter implements Converter<Rendezvous,String> {

    @Override
    public String convert(Rendezvous rendezvous) {
        String result;

        if (rendezvous== null)
            result = null;
        else
            result = String.valueOf(rendezvous.getId());

        return result;
    }
}
