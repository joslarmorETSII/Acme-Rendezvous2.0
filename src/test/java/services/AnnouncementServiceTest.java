package services;

import domain.*;
import domain.Announcement;
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
public class AnnouncementServiceTest extends AbstractTest{


    //The sut
    @Autowired
    AnnouncementService announcementService;

    @Autowired
    UserService userService;

    @Autowired
    ActorService    actorService;

    @Autowired
    RendezvousService rendezvousService;


    /*  FUNCTIONAL REQUIREMENT:
     *
     *   An actor who is authenticated as a user must be able to:
     * - Create an announcement regarding one of the rendezvouses that he or she’s created previously.
     *
     * WHAT WILL WE DO?
     *
     * En este caso de uso vamos a crear un anuncio teniendo en cuenta que sólo se puede crear
     * un anuncio sobre un rendezvous que haya creado el usuario
     *
     * POSITIVE AND NEGATIVE CASES
     *
     * Como caso positivo:
     *
     * ·Introducir un usuario y un rendezvous que el haya creado
     *
     *   Para forzar el error pueden darse varios casos negativos, como son:
     *
     * · Introducir un rendezvous con un usuario que no lo ha creado
     * · Introducir un usuario como manager
     * · Introducir un titulo vacío
     * · Introducir una descripción vacía
     * · Introducir un script en la descripción
     * · Introducir un script en el título
     */
    public void announcementCreateTest(final String username, String title, String description, String rendezvousBean, Class<?> expected) {
        Class<?> caught=null;

        try {
            this.authenticate(username);

            Announcement result = this.announcementService.create();
            //Collection<Rendezvous> rendezvouses = userService.findByPrincipal().getRendezvouses();
            Rendezvous rendezvous = rendezvousService.findOne(super.getEntityId(rendezvousBean));

            result.setTitle(title);
            result.setDescription(description);
            result.setRendezvous(rendezvous);

            announcementService.save(result);
            announcementService.flush();
            this.unauthenticate();

        } catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);

    }

     /*  Test to Edit an announcement
     *
     * WHAT WILL WE DO?
     *
     * En este caso de uso un usuario va a editr un announcement de un rendezvous:
     *
     * POSITIVE AND NEGATIVE CASES
     *
     * Como caso positivo:
     *
     * · Editar announcement siendo logueado como usuario
     *
     * Para forzar el error pueden darse varios casos negativos, como son:
     *

     * · Editar un announcement siendo logueado como manager


     */

    public void announcementEdit(final String username, String announcementBean, Class<?> expected) {

        Class<?> caught = null;

        try {

            this.authenticate(username);

            // Inicializamos los atributos para la edición
            Announcement announcement;
            announcement = announcementService.findOneToEdit(super.getEntityId(announcementBean));

            this.announcementService.save(announcement);
            announcementService.flush();

            this.unauthenticate();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
    }

    /*  FUNCTIONAL REQUIREMENT:
     *
     *   An actor who is not authenticated must be able to:
     * - List the announcements that are associated with each rendezvous.
     *
     * WHAT WILL WE DO?
     *
     * En este caso de uso vamos a listar los anuncios por rendezvous, un anuncio siempre tiene asociado un rendezvous:
     *
     * POSITIVE AND NEGATIVE CASES
     *
     * Como caso positivo:
     *
     * ·Listar los anuncios no estando autenticado.
     * .Listar los anuncios logueado como user.
     * .Listar los anuncios logueado como administrador.
     * .Listar los anuncios logueado como manager.
     */
    public void listOfAnnouncementTest(final String username,final Class<?> expected){
        Class<?> caught = null;

        try {
            this.authenticate(username);

            this.announcementService.findAll();

            this.unauthenticate();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
    }

    /*  FUNCTIONAL REQUIREMENT:
     *
     *   An actor who is authenticated as a user must be able to:
     * Display a stream of announcements that have been posted to the rendezvouses
     * that he or she’s RSVPd. The announcements must be listed chronologically in descending order.
     *
     * WHAT WILL WE DO?
     *
     * En este caso de uso vamos a listar los anuncios de los rendezvous donde el usuario haya participado
     *
     * POSITIVE AND NEGATIVE CASES
     *
     * Como caso positivo:
     *
     * ·Introducir un usuario
     *
     *   Para forzar el error pueden darse varios casos negativos, como son:
     *
     * · Introducir un usuario como manager
     * · Introducir un usuario como admisnistrador
     * · Introducir un usuario no autenticado
     */
    public void displayOfAnnouncementTest(final String username, final Class<?> expected){
        Class<?> caught = null;

        try {
            this.authenticate(username);
                Actor actor;
                actor = actorService.findOne(super.getEntityId(username));
                this.announcementService.announcementFindByParticipated(actor.getId());

            this.unauthenticate();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
    }

    /*  FUNCTIONAL REQUIREMENT:
     *
       * An actor who is authenticated as an administrator must be able to:
            1. Remove an announcement that he or she thinks is inappropriate.
     *
     * WHAT WILL WE DO?
     *
     * En este caso de uso el admin va a borrar un anuncio:
     *
     * POSITIVE AND NEGATIVE CASES
     *
     * Como caso positivo:
     *
     * · Borrar un anuncio estando logueado como admin .
     *
     * Para forzar el error pueden darse varios casos negativos, como son:
     *
     * · Borrar una anuncio logueado como usuario
     * · Borrar una anuncio logueado como manager.
     */

    public void announcementDelete(final String username, String announcementBean, final Class<?> expected) {
        Class<?> caught = null;

        try {

            this.authenticate(username);

            // Inicializamos los atributos para la edición
            final Announcement announcement;

            announcement = announcementService.findOne(super.getEntityId(announcementBean));

            // Borramos
            this.announcementService.delete(announcement);
            announcementService.flush();

            this.unauthenticate();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
    }

    //Drivers
    //===================================================

    @Test
    public void driverAnnouncementCreateTest() {

        final Object testingData[][] = {
                // Crear un announcement sobre un rendezvous que el usuario ha creado -> true

                {
                        "user1", "announcement232", "descripcion3","rendezvous1", null
                },
                // Crear un announcement sobre un rendezvous que el usuario no ha creado -> false
                {
                        "user1", "announcement32", "descripcion7","rendezvous3", IllegalArgumentException.class
                },

                // Crear un announcement sobre un rendezvous con un actor que no es user -> false
                {
                        "manager1", "announcement32", "descripcion7","rendezvous3", IllegalArgumentException.class
                },
               // Crear una announcement con el titulo vacío -> false
                {
                        "user1", "", "descripcion7","rendezvous1", ConstraintViolationException.class
                },
                // Crear una announcement con la descripción vacía -> false
                {
                        "user1", "title3", "","rendezvous1", ConstraintViolationException.class
                },
                 // Crear una announcement introduciendo un script en la descripcion -> false
                {
                        "user1", "title3", "<script>","rendezvous1", ConstraintViolationException.class
                },
                // Crear una announcement introduciendo un script en el título -> false
                {
                        "user1", "<script>", "description8","rendezvous1", ConstraintViolationException.class
                },
        };
        for (int i = 0; i < testingData.length; i++)
            announcementCreateTest((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3],
                      (Class<?>) testingData[i][4]);

    }

    @Test
    public void driverAnnouncementEditTest() {

        final Object testingData[][] = {
                // Editar un announcement sobre un rendezvous que el usuario ha creado -> true

                {
                        "user1", "announcement1", null
                },
                // Editar un announcement sobre un rendezvous que el usuario no ha creado -> false
                {
                        "manager1", "announcement1", IllegalArgumentException.class
                },
        };
        for (int i = 0; i < testingData.length; i++)
            announcementEdit((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

    }

    @Test
    public void driverListAnnouncementTest() {

        final Object testingData[][] = {
                // Sin estar autenticado muestra los anuncios por rendezvous -> true
                {
                        null, null
                },
                // User puede listar los anuncios -> true
                {
                        "user1", null
                },
                // Administrador puede listar los anuncios para borrarlo -> true
                {
                        "administrator", null
                },
                // Un manager puede listar los anuncios -> true
                {
                        "manager1", null
                },

        };
        for (int i = 0; i < testingData.length; i++)
            this.listOfAnnouncementTest((String) testingData[i][0], (Class<?>) testingData[i][1]);
    }

    @Test
    public void driverDisplayAnnouncementTest() {

        final Object testingData[][] = {
                // Autenticado como user y mostrar los anuncios de los rendezvous donde ha participado -> true
                {
                        "user1", null
                },
                // Autenticado como manager -> false
                {
                        "manager1", IllegalArgumentException.class
                },
                // Autenticado como administrador -> false
                {
                        "admin", IllegalArgumentException.class
                },

        };
        for (int i = 0; i < testingData.length; i++)
            this.displayOfAnnouncementTest((String) testingData[i][0], (Class<?>) testingData[i][1]);
    }

    @Test
    public void driverAnnouncementDeleteTest() {

        final Object testingData[][] = {
                // Eliminar un announcement como administrador que considere inapropiado -> true

                {
                        "administrator", "announcement1", null
                },
                // Eliminar un announcement como manager que considere inapropiado-> false
                {
                        "manager1", "announcement1", IllegalArgumentException.class
                },

                // Eliminar un announcement como manager que considere inapropiado -> false
                {
                        "user1", "announcement1", IllegalArgumentException.class
                },
        };
        for (int i = 0; i < testingData.length; i++)
            announcementDelete((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

    }
}
