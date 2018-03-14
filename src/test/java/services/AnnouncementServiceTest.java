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
    RendezvousService rendezvousService;

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

    //@Test
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
