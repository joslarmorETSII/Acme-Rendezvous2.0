
package forms;

import domain.Answer;
import domain.Question;

import javax.validation.Valid;
import java.util.Collection;


public class QuestionsForm {


    public QuestionsForm() {
        super();
    }

    private Collection<Question> questions;
    private Answer answer;

    @Valid
    public Collection<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Collection<Question> questions) {
        this.questions = questions;
    }
    @Valid
    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
