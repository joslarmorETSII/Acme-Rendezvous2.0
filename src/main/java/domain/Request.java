package domain;


import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)

public class Request extends DomainEntity{

    // Constructors -----------------------------------------------------------

    public Request() {
       super();
    }


    // Attributes -----------------------------------------------------------

    private String comment;
    private CreditCard creditCard;

    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Valid
    @NotNull
    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    // Relationships -----------------------------------------------------------

    private Servise service;


    @ManyToOne(optional = false)
    public Servise getService() {
        return service;
    }

    public void setService(Servise service) {
        this.service = service;
    }

}
