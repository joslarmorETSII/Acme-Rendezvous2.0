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

@Transactional
@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class AnswerServiceTest extends AbstractTest {

    // The SUT ---------------------------------
    @Autowired
    private RequesttService requesttService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private RendezvousService rendezvousService;

    @Autowired
    private AnswerService answerService;

    // Tests
    // ====================================================


    /*  FUNCTIONAL REQUIREMENT:
    *   An actor who is authenticated as a user must be able to:
        1. Answer the questions that are associated with a rendezvous that he or she’s RSVP-ing now.    *     WHAT WILL WE DO?

    *  To check the create of an answers in our system, the system must check the different attribute of the entity.
    *
    *  En este test, se comprueba el crear las respuestas.
    *  Para ello  simulamos el logueo de distintos usuario autentificados y crear respuestas para las questiones asociados a un rendezvous.
    *
    * Como casos positivos:

    * .Simular el logueo como user1, rellenar correctamente todos los campos.

    * Para forzar el error pueden darse varios casos negativos, como son:

    * .Simular el logueo como manager.
    * .Poner el campo de texto a nulo
    * .Meter un script.
    * .Poner el usuario a null.
    */

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
     /*  FUNCTIONAL REQUIREMENT:
    *   An actor who is not authenticated must be able to:
           1. Display information about the users who have RSVPd a rendezvous, which, in turn, must show their answers to the questions that the creator has registered.
    *     WHAT WILL WE DO?

    *  To check the list of an answers in our system, the system must check the different users and unautification users
    *
    *  En este test, se comprueba el Listar las respuestas.
    *  Para ello simulamos el logueo de distintos usuarios tanto autentificados como no autentificados
    *
    * Como casos positivos:

    * Simular el logueo como user1.
    * Simular el logueo como use2.
    * Simular el logueo como admin.
    * Poner a null el campo de usuario


  */
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
                // Crear una Answer  -> true
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

