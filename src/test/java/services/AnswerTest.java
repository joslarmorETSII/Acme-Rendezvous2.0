package services;

import domain.*;
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
public class AnswerTest extends AbstractTest {

    // The SUT ---------------------------------
    @Autowired
    private RequesttService requesttService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private RendezvousService rendezvousService;

    @Autowired
    private AnswerService answerService;



    public void answerCreateTest(String username, String answer,String questionBean,  Class<?> expected) {
        Class<?> caught=null;

        try {

            Question question = questionService.findOne(getEntityId(questionBean));


            this.authenticate(username);

            Answer result = answerService.create();
            result.setQuestion(question);
            //result.setRendezvous(rendezvous);

            answerService.save(result);
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
    public void driverAnswerCreateTest() {
        final Object testingData[][] = {

             //   String username, String answer,String questionBean,  Class<?> expected
                // Crear una Answer  -> true
                {
                        "user2","answer1","question1",null
                },

                // Crear una answer sin texto -> false
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
            answerCreateTest((String) testingData[i][0], (String) testingData[i][1],
                    (String) testingData[i][2],(Class<?>) testingData[i][3]);

    }
    //@Test
    public void driverListRendezvousTest() {

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

