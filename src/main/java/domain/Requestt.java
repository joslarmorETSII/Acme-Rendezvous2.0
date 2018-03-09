package domain;


import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)

public class Requestt extends DomainEntity{

    // Constructors -----------------------------------------------------------

    public Requestt() {
       super();
    }


    // Attributes -----------------------------------------------------------

    private String comment;

    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }



    // Relationships -----------------------------------------------------------

    private Rendezvous rendezvous;
    private Servise servise;

    @Valid
    @NotNull
    @ManyToOne(optional = false)
    public Rendezvous getRendezvous() {
        return rendezvous;
    }

    public void setRendezvous(Rendezvous rendezvous) {
        this.rendezvous = rendezvous;
    }

    @Valid
    @NotNull
    @ManyToOne(optional = false)
    public Servise getServise() {
        return servise;
    }

    public void setServise(Servise service) {
        this.servise = service;
    }



}
