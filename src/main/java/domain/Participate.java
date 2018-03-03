package domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
        @Index(columnList = "attendant_id"), @Index(columnList = "rendezvous_id")
})
public class Participate extends DomainEntity {

    // Constructors -----------------------------------------------------------

    public Participate() {
        super();
    }


    // Attributes -------------------------------------------------------------
    private Date moment;

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

    private User attendant;
    private Rendezvous rendezvous;

    @Valid
    @ManyToOne(optional = false)
    public User getAttendant() {
        return attendant;
    }

    @Valid
    @ManyToOne(optional = false)
    public Rendezvous getRendezvous() {
        return rendezvous;
    }

    public void setAttendant(User attendant) {
        this.attendant = attendant;
    }

    public void setRendezvous(Rendezvous rendezvous) {
        this.rendezvous = rendezvous;
    }
}
