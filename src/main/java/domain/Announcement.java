package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

@Entity
@Access(AccessType.PROPERTY)
public class Announcement extends DomainEntity {

    // Constructors -----------------------------------------------------------

    public Announcement() {
        super();
    }


    // Attributes -------------------------------------------------------------

    private String title;
    private String description;
    private Date moment;

    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    @NotBlank
    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    @NotNull
    @Past
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    public Date getMoment() {

        return moment;
    }

    public void setMoment(Date moment) {

        this.moment = moment;
    }

    // Relationships ----------------------------------------------------------

    private Rendezvous rendezvous;

    @Valid
    @NotNull
    @ManyToOne(optional = false)
    public Rendezvous getRendezvous(){

        return rendezvous;
    }

    public void setRendezvous(Rendezvous rendezvous) {

        this.rendezvous = rendezvous;
    }

}
