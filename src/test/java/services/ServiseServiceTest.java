package services;

import controllers.AbstractController;
import domain.Category;
import domain.Servise;
import domain.Servise;
import domain.Servise;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import security.Authority;
import utilities.AbstractTest;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

@Transactional
@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class ServiseServiceTest extends AbstractTest {

    // The SUT ---------------------------------
    @Autowired
    private ServiseService serviseService;

    @Autowired
    private CategoryService categoryService;



    public void serviseCreateTest(final String username, String name, String description, String picture, boolean assigned,boolean inappropriate, String categoryBean, Class<?> expected) {
        Class<?> caught=null;

        try {
            this.authenticate(username);

            Category category;
            Servise result = this.serviseService.create();
            category = categoryService.findOne(super.getEntityId(categoryBean));

            result.setName(name);
            result.setDescription(description);
            result.setPicture(picture);
            result.setCategory(category);

            serviseService.save(result);
            serviseService.flush();
            this.unauthenticate();

        } catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);

    }


    public void serviseSaveStatus(final String username, String name, String description, String picture, String serviseBean, Class<?> expected) {

        Class<?> caught = null;

        try {

            this.authenticate(username);

            // Inicializamos los atributos para la edición
            Servise servise;
            servise = serviseService.findOne(super.getEntityId(serviseBean));

            servise.setName(name);
            servise.setDescription(description);
            servise.setPicture(picture);

            // Guardamos
            this.serviseService.save(servise);
            serviseService.flush();

            this.unauthenticate();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
    }

    public void serviseDelete(final String username, String serviseBean, final Class<?> expected) {
        Class<?> caught = null;

        try {

            this.authenticate(username);

            // Inicializamos los atributos para la edición
            final Servise servise;

            servise = serviseService.findOne(super.getEntityId(serviseBean));

            // Borramos
            this.serviseService.delete(servise);
            serviseService.flush();

            this.unauthenticate();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
    }

    public void serviseInappropriate(final String username, String serviseBean, final Class<?> expected) {
        Class<?> caught = null;

        try {

            this.authenticate(username);

            // Inicializamos los atributos para la edición
            final Servise servise;

            servise = serviseService.findOne(super.getEntityId(serviseBean));

            // Borramos
            this.serviseService.inappropriateDontRequest(servise.getId());
            serviseService.flush();

            this.unauthenticate();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
    }
    // Drivers
    // ===================================================

    @Test
    public void driverServiseCreateTest() {

        final Object testingData[][] = {
                // Crear una servise estando logueado como manager -> true

                {
                    "manager1", "paco", "descripcion1", "http://www.picture.com", false, false, "category1", null
                },
                 //Crear una servise estando logueado como user -> false
                {
                    "user1", "paco", "descripcion1", "http://www.picture.com", false, false, "null", IllegalArgumentException.class
                },

               // Crear una servise siendo el nombre y la descripción vacías -> false
                {
                    "manager1", "", "","http://www.picture.com", false, false, "category1", ConstraintViolationException.class
                },
                 // Crear una servise pasando un script en la descripcion -> false
                {
                    "manager2", "paco", "<script>", "http://www.picture.com", false, false, "category1", ConstraintViolationException.class
                },
                // Crear un servise con una picture no valida-> false
                {
                    "manager2", "paco", "description1", "w.picture.com", false, false, "category1", ConstraintViolationException.class
                },
                // Crear un servise asignado no valida-> false
                {
                    "manager2", "paco", "description1", "http://www.picture.com", true, false, "category1", ConstraintViolationException.class
                },
                // Crear un servise asignado no valida-> false
                {
                    "manager2", "paco", "description1", "http://www.picture.com", false, true, "category1", ConstraintViolationException.class
                },
        };
        for (int i = 0; i < testingData.length; i++)
            serviseCreateTest((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2],
                    (String) testingData[i][3], (boolean) testingData[i][4], (boolean) testingData[i][5],
                    (String)testingData[i][6], (Class<?>) testingData[i][7]);

    }

    @Test
    public void driverServiseEdit() {

        final Object testingData[][] = {
                // Editar credit card de su user y atributos correctos (todos) -> true
                // Crear una servise estando logueado como manager -> true

                {
                        "manager1", "paco", "descripcion1", "http://www.picture.com","servise1", null
                },
                // Editar un servicio que no pertenece al manager que lo creo -> false
                {
                        "manager2", "julio", "descripcion1", "http://www.picture.com", "servise1", IllegalArgumentException.class
                },
//
                // Editar un servicio siendo un usuario distinto de manager -> false
                {
                        "user1", "julio", "descripcion1", "http://www.picture.com", "servise1", IllegalArgumentException.class
                },



        };
        for (int i = 0; i < testingData.length; i++)
            this.serviseSaveStatus((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4],
                    (Class<?>) testingData[i][5]);
    }

    @Test
    public void driverDeleteServise() {

        final Object testingData[][] = {
                // Borrar una servise logueado como el manager del servicio -> true
                {
                        "manager1", "servise1", null
                },
                // Borrar una servise logueado como manager que no ha creado el servicio -> false
                {
                        "manager2", "servise1", IllegalArgumentException.class
                },

                // Borrar una servise logueado como otro actor que no sea manager -> false
                {
                        "user1", "servise1", IllegalArgumentException.class
                },

        };
        for (int i = 0; i < testingData.length; i++)
            this.serviseDelete((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
    }

    @Test
    public void driverInappropriateServise() {

        final Object testingData[][] = {
                // Poner un servicio como inapropiado logueado como administrador -> true
                {
                        "administrator", "servise1", null
                },
                // Borrar una servise logueado como manager que no ha creado el servicio -> false
                {
                        "manager2", "servise1", IllegalArgumentException.class
                },

                // Borrar una servise logueado como otro actor que no sea manager -> false
                {
                        "user1", "servise1", IllegalArgumentException.class
                },

        };
        for (int i = 0; i < testingData.length; i++)
            this.serviseInappropriate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
    }
}
