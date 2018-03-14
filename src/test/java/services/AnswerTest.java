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
            result.setAnswer(answer);
            result.setQuestion(question);



            answerService.save(result);
            answerService.flush();
            this.unauthenticate();


        } catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);

    }

    public void listOfAnswerTest(final String username,final Class<?> expected){
        Class<?> caught = null;
        try {
            this.authenticate(username);

            this.answerService.findAll();

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

             //   String username, String answer,String answerBean,String questionBean,  Class<?> expected
                // Editar una Answer  -> true
                {
                        "user2","answer1","question1",null
                },

                // Crear una answer sin texto -> false
                {
                        "user2","","question1",ConstraintViolationException.class
                },
                // Crear una Answer logueado como manager --> false
                {
                        "manager2","answer1","question1",IllegalArgumentException.class
                },
                // Crear una Answer con <script> en el texto
                {
                        "user2","<script>","question1",ConstraintViolationException.class
                },
                // Crear una Answer sin  loguearse -> false
                {
                        null,"answer1", "question1", IllegalArgumentException.class
                },

        };
        for (int i = 0; i < testingData.length; i++)
            answerCreateTest((String) testingData[i][0], (String) testingData[i][1],
                    (String) testingData[i][2],(Class<?>) testingData[i][3]);

    }


    @Test
    public void driverListAnswerTest() {

        final Object testingData[][] = {
                // Alguien sin registrar/logueado -> true
                {
                        null, null
                },
                // Un Usuario -> true
                {
                        "user1", null
                },
                // Otro Usuario -> true
                {
                        "user2", null
                },
                // Un administrador -> true
                {
                        "administrator", null
                }

        };
        for (int i = 0; i < testingData.length; i++)
            this.listOfAnswerTest((String) testingData[i][0], (Class<?>) testingData[i][1]);
    }


}

