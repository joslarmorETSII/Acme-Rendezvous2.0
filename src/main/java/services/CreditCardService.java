package services;


import java.util.Collection;


import javax.transaction.Transactional;

import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


import repositories.CreditCardRepository;
import domain.CreditCard;

@Service
@Transactional
public class CreditCardService {

    // Managed Repositories
    @Autowired
    private CreditCardRepository	creditCardRepository;

    // Supported Services

    @Autowired
    private ActorService			actorService;

    @Autowired
    private UserService			userService;


    // Constructor
    public CreditCardService() {
        super();
    }

    // Simple CRUD Methods

    public CreditCard create() {

        CreditCard result;

        result = new CreditCard();

        return result;
    }

    public CreditCard save(final CreditCard creditCard) {

        Assert.notNull(creditCard);
        Assert.isTrue(this.actorService.isUser());
      //  Assert.isTrue(creditCard.getActor().equals(this.actorService.findByPrincipal()));

        CreditCard result;

        result = this.creditCardRepository.save(creditCard);

        return result;
    }

    public void delete(final CreditCard creditCard) {
        Assert.notNull(creditCard);

        User user = userService.findByPrincipal();
        Assert.isTrue(user.getCreditCard().equals(creditCard));

       user.setCreditCard(null);
       userService.save(user);
       this.creditCardRepository.delete(creditCard);
    }

    public CreditCard findOne(final int creditCardId) {
        Assert.isTrue(creditCardId != 0);
        CreditCard result;

        result = this.creditCardRepository.findOne(creditCardId);
        Assert.notNull(result);
        return result;
    }

    public Collection<CreditCard> findAll() {
        Collection<CreditCard> result;

        result = this.creditCardRepository.findAll();
        Assert.notNull(result);

        return result;
    }

    public void flush() {
        creditCardRepository.flush();
    }

    // Other business methods

/*    public CreditCard findOneByActorId(final int actorId) {

        CreditCard result;

        result = this.creditCardRepository.findOneByActorId(actorId);

        return result;

    }*/





}
