package services;

import domain.Actor;
import domain.Comment;
import domain.Rendezvous;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import repositories.CommentRepository;
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
public class CommentServiceTest extends AbstractTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private RendezvousService   rendezvousService;


    public void commentCreateTest(String username,String text,String picture,String rendezVousBean, final Class<?> expected) {
        Class<?> caught = null;

        try {
            Rendezvous rendezvous = this.rendezvousService.findOne(this.getEntityId(rendezVousBean));
            this.authenticate(username);

            Comment result = this.commentService.create();

            result.setText(text);
            result.setPicture(picture);
            result.setRendezvous(rendezvous);
            commentService.save(result);
            commentService.flush();


            this.unauthenticate();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
    }

    public void listOfCommentTest(final String username,final Class<?> expected){
        Class<?> caught = null;

        try {
            this.authenticate(username);

            this.commentService.findAll();

            this.unauthenticate();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
    }

    public void editCommentTest(String username,String text,String picture,String commnetBean, final Class<?> expected){
        Class<?> caught = null;

        try {

            this.authenticate(username);

            Comment result= commentService.findOneToEdit(super.getEntityId(commnetBean));

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

    public void deleteCommentTest(final String username, String commentBean, final Class<?> expected) {
        Class<?> caught = null;

        try {
            Comment result= commentService.findOne(super.getEntityId(commentBean));

            this.authenticate(username);

            this.commentService.delete(result);

            this.unauthenticate();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
    }

    @Test
    public void driverListRendezvousTest() {

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
            this.listOfCommentTest((String) testingData[i][0], (Class<?>) testingData[i][1]);
    }

    @Test
    public void driverCommentCreateTest() {
            //DateFormat formatter= new SimpleDateFormat("dd/MM/yyyy HH:mm");

            final Object testingData[][] = {
                    // Crear un comentario perfecto -> true
                    {
                        "user1","description1","http://www.picture.com", "rendezvous1", null
                    },
                    // Crear un comentario sin estar logueado --> false
                    {
                        null, "description1","http://www.picture.com","rendezvous1",IllegalArgumentException.class
                    },
                    // Crear un comentario siendo manager -> false
                    {
                       "manager1","description1","http://www.picture.com", "rendezvous1",IllegalArgumentException.class
                    },
                    // Crear un comentario con el text vacio -> false
                    {
                       "user1","", "www.picture.com","rendezvous1",ConstraintViolationException.class
                    },
                    // Crear un comentario con una url vacia -> false
                    {
                        "user1","description1", "","rendezvous1",ConstraintViolationException.class
                    }

            };
            for (int i = 0; i < testingData.length; i++)
                this.commentCreateTest((String) testingData[i][0], (String) testingData[i][1],(String) testingData[i][2],(String) testingData[i][3], (Class<?>) testingData[i][4]);

    }

    @Test
    public void driverEditCommentTest() {

        final Object testingData[][] = {
                // Crear un comentario perfecto -> true
                {
                        "user2","cambiando descripción","http://www.picture.com", "comment1", null
                },
                // Intentamos modificar la descripcion del comentario sin estar logueado --> false
                {
                        null, "descripcion modificada","http://www.picture.com","comment1",IllegalArgumentException.class
                },
                // Intento modificar un comentario siendo manager -> false
                {
                        "manager1","descripcion modificada","http://www.picture.com", "comment1",IllegalArgumentException.class
                },
                // Intento modificar un comentario siendo user1 del user2 -> false
                {
                        "user1","hacking","http://www.picture.com", "comment1",IllegalArgumentException.class
                },
                // Editamos un comentario e intentamos guardarlo con el texto vacio -> false
                {
                        "user2","", "http://www.picture.com","comment1",ConstraintViolationException.class
                },
                // Editamos un comentario dejando la foto vacia -> false
                {
                        "user2","description1", "","comment1",null
                },
                // Editamos un comentario con un usuario que no existe -> false
                {
                        "user999999","Prueba de usuario que no existe", "http://www.picture.com","comment1",IllegalArgumentException.class
                }

        };
        for (int i = 0; i < testingData.length; i++)
            this.editCommentTest((String) testingData[i][0], (String) testingData[i][1],(String) testingData[i][2],(String) testingData[i][3], (Class<?>) testingData[i][4]);

    }

    @Test
    public void driverDeleteCommentTest() {

        final Object testingData[][] = {
                // Borrar un comentario estando logueado como administrator -> true
                {
                        "administrator", "comment1", null
                },

                // Borrar un comentario estando logueado como user -> false
                {
                        "user1", "comment1", IllegalArgumentException.class
                },
                // Borrar un comentario sin estar logueado -> false
                {
                        null, "comment1", IllegalArgumentException.class
                },
                // Borrar un comentario estando logueado como manager -> false
                {
                        "manager1", "comment1", IllegalArgumentException.class
                }

        };
        for (int i = 0; i < testingData.length; i++)
            this.deleteCommentTest((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
    }
}
