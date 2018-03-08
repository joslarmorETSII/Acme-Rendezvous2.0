package converters;

import domain.CreditCard;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import repositories.CreditCardRepository;

public class StringToCreditCardConverter implements Converter<String, CreditCard> {

    @Autowired
    private CreditCardRepository creditCard;


    @Override
    public CreditCard convert(final String text) {
        CreditCard result;
        int id;

        if (StringUtils.isEmpty(text))
            result = null;
        else
            try {
                id = Integer.valueOf(text);
                result = this.creditCard.findOne(id);

            } catch (final Throwable oops) {
                throw new IllegalArgumentException(oops);
            }
        return result;
    }

}
