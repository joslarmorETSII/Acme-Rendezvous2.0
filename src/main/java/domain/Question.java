package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Access(AccessType.PROPERTY)
public class Question extends DomainEntity {


    // Constructors -----------------------------------------------------------

    public Question() {
        super();
    }


    // Constructors -----------------------------------------------------------

    private String text;

    @NotBlank
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    // Constructors -----------------------------------------------------------

    private Collection<Answer> answers;
    private Rendezvous rendezvous;

    @NotNull
    @Valid
    @OneToMany(mappedBy = "question")
    public Collection<Answer> getAnswers() {
        return answers;
    }

    @Valid
    @ManyToOne(optional = false)
    public Rendezvous getRendezvous() {
        return rendezvous;
    }

    public void setAnswers(Collection<Answer> answers) {
        this.answers = answers;
    }

    public void setRendezvous(Rendezvous rendezvous) {
        this.rendezvous = rendezvous;
    }
}
