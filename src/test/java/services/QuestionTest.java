package services;

import domain.Question;
import domain.Rendezvous;
import domain.Requestt;
import domain.Servise;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import utilities.AbstractTest;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Transactional
@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class QuestionTest extends AbstractTest {

    // The SUT ---------------------------------
    @Autowired
    private RequesttService requesttService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private RendezvousService rendezvousService;



    public void questionCreateTest(String username, String rendezvousBean,String text,  Class<?> expected) {
        Class<?> caught=null;

        try {
            int rendezvousId = super.getEntityId(rendezvousBean);
            Rendezvous rendezvous = rendezvousService.findOne(rendezvousId);

            this.authenticate(username);

            Question result = questionService.create();
            result.setText(text);
            result.setRendezvous(rendezvous);

            questionService.save(result);
            this.unauthenticate();


        } catch (final Throwable oops) {
            caught = oops.getClass();
        }
            this.checkExceptions(expected, caught);

    }


    public void questionEditTest(String username, String questionBean,String text,  Class<?> expected) {
        Class<?> caught=null;

        try {
            this.authenticate(username);

            int questionId = super.getEntityId(questionBean);
            Question question = questionService.findOne(questionId);
            question.setText(text);

            questionService.save(question);
            this.unauthenticate();


        } catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);

    }

    // Drivers
    // ===================================================

    @Test
    public void driverQuestionCreateTest() {
        final Object testingData[][] = {


                // Crear una Question  -> true
                {
                    "user2", "rendezvous3","Question rend_3",null
                },
                // Crear una Question estando logueado como user y el rendezvous tiene participants -> false
                {
                        "user1", "rendezvous1","Question test?", IllegalArgumentException.class
                },
                // Crear una Question sin texto -> false
                {
                        "user2", "rendezvous3",null,ConstraintViolationException.class
                },
                // Crear una Question logueado como manager --> false
                {
                        "manager2", "rendezvous2","Question created by manager test?",IllegalArgumentException.class
                },
                // Crear una Question con <script> en el texto
                {
                        "user2", "rendezvous3","<script>",ConstraintViolationException.class
                },
                // Crear una Quesion sin  loguearse -> false
                {
                        null, "rendezvous1", "Question anonymous Test", IllegalArgumentException.class
                },
                // Crear una Quesion para un rendezvous que tiene participantes  -> false
                {
                        null, "rendezvous1", "Question anonymous Test", IllegalArgumentException.class
                }
        };
        for (int i = 0; i < testingData.length; i++)
            questionCreateTest((String) testingData[i][0], (String) testingData[i][1],
                    (String) testingData[i][2],(Class<?>) testingData[i][3]);

    }
    @Test
    public void driverQuestionEditTest() {

        final Object testingData[][] = {
                // Editar una Question -> true
                {
                        "user2", "question3", "Question Text?", null
                },
                // Editar una question de otro usuario -> false
                {
                        "user1", "question3", "Question Text?", IllegalArgumentException.class
                },
                // Editar una question con un script en el texto ->false
                {
                        "user2", "question3", "<php>", ConstraintViolationException.class
                },
                // Editar una question sin poner el texto -> flase
                {
                        "user2", "question3", "", ConstraintViolationException.class

                }

        };
        for (int i = 0; i < testingData.length; i++)
            this.questionEditTest((String) testingData[i][0],(String)testingData[i][1],(String)testingData[i][2] ,(Class<?>) testingData[i][3]);
    }


}
