package forms;

import domain.CreditCard;
import domain.Rendezvous;
import domain.Servise;
import domain.User;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class RequesttForm {

    public RequesttForm() {
        super();
    }

    private Servise servise;
    private Rendezvous rendezvous;
    private CreditCard creditCard;
    private String comment;

    @Valid
    @NotNull
    public Servise getServise() {
        return servise;
    }

    public void setServise(Servise servise) {
        this.servise = servise;
    }

    @Valid
    @NotNull
    public Rendezvous getRendezvous() {
        return rendezvous;
    }

    public void setRendezvous(Rendezvous rendezvous) {
        this.rendezvous = rendezvous;
    }

    @Valid
    @NotNull
    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
