package services;

import domain.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import repositories.CommentRepository;
import utilities.AbstractTest;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

@Transactional
@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class CommentServiceTest extends AbstractTest {

    // The SUT
    // ====================================================

    @Autowired
    private CommentService commentService;

    // Tests
    // ====================================================

    /* Create a new comment.

              En este caso de uso un usuario puede crear un rendezvous.*/

    public void commentCreateTest(String username,String text,String picture,final Class<?> expected) {
        Class<?> caught = null;

        try {

            this.authenticate(username);

            Comment result = this.commentService.create();

            result.setText(text);
            result.setPicture(picture);

            commentService.save(result);
            commentService.flush();


            this.unauthenticate();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
    }

//   // comprobamos que cualquiera puede listar las academias que existen en nuestra aplicación
//
//    public void listOfRendezvousTest(final String username,final Class<?> expected){
//        Class<?> caught = null;
//        try {
//            this.authenticate(username);
//
//            this.rendezvousService.findAll();
//
//            this.unauthenticate();
//
//        } catch (final Throwable oops) {
//
//            caught = oops.getClass();
//
//        }
//
//        this.checkExceptions(expected, caught);
//    }
//
//
//
//    //Drivers
//    // ===================================================
//
//    @Test
//    public void driverListRendezvousTest() {
//
//        final Object testingData[][] = {
//                // Alguien sin registrar/logueado -> true
//                {
//                        null, null
//                },
//                // Un Usuario -> true
//                {
//                        "user1", null
//                },
//                // Otro Usuario -> true
//                {
//                        "user2", null
//                },
//                // Un administrador -> true
//                {
//                        "administrator", null
//                }
//
//        };
//        for (int i = 0; i < testingData.length; i++)
//            this.listOfRendezvousTest((String) testingData[i][0], (Class<?>) testingData[i][1]);
//    }
    @Test
    public void driverCommentCreateTest() {
            //DateFormat formatter= new SimpleDateFormat("dd/MM/yyyy HH:mm");

            final Object testingData[][] = {
                    // Crear un curso estando logueado como academy -> true
                    {
                            "user1","description1","http://www.picture.com", null
                    },
               // Crear un curso sin estar logueado --> false
                {
                        null, "description1","http://www.picture.com",IllegalArgumentException.class
               },
                // Crear un curso logueado como dancer -> false
                {
                       "manager1","description1","http://www.picture.com", IllegalArgumentException.class
               },
               // Crear un curso rendezvous con momento en el pasado -> false
             //  {
                     //  "user1","", "www.picture.com",IllegalArgumentException.class
              // }
                    {
                           "user1","description1", "",IllegalArgumentException.class
                    }

            };
            for (int i = 0; i < testingData.length; i++)
                this.commentCreateTest((String) testingData[i][0], (String) testingData[i][1],(String) testingData[i][2], (Class<?>) testingData[i][3]);

    }
}
