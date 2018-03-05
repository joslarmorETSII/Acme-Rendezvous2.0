package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class  Answer extends DomainEntity {

    // Constructors -----------------------------------------------------------

    public Answer() {
        super();
    }

    // Attributes -------------------------------------------------------------

    private String answer;

    @SafeHtml
    @NotBlank
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    // Relationships ----------------------------------------------------------
    private User user;
    private Question question;
    @Valid
    @NotNull
    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(optional = false)
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
