package domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Collection;
import java.util.Date;

@Entity
@Access(AccessType.PROPERTY)
public class User extends Actor {

    // Constructors -----------------------------------------------------------

    public User() {
        super();
    }


    // Attributes -------------------------------------------------------------

    private Date birthday;

    @NotNull
    @Past
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    // Relationships ----------------------------------------------------------

    private Collection<Comment> comments;
    private Collection<Participate> participates;
    private Collection<Rendezvous> rendezvouses;
    private Collection<Answer> answers;

    @Valid
    @NotNull
    @OneToMany(mappedBy = "user")
    public Collection<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Collection<Answer> answers) {
        this.answers = answers;
    }

    @Valid
    @NotNull
    @OneToMany(mappedBy = "user")
    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }

    @Valid
    @NotNull
    @OneToMany(mappedBy = "attendant")
    public Collection<Participate> getParticipates() {
        return participates;
    }

    public void setParticipates(Collection<Participate> participates) {
        this.participates = participates;
    }

    @Valid
    @NotNull
    @OneToMany(mappedBy = "creator")
    public Collection<Rendezvous> getRendezvouses() {
        return rendezvouses;
    }

    public void setRendezvouses(Collection<Rendezvous> rendezvouses) {
        this.rendezvouses = rendezvouses;
    }

}
