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


    // Tests ------------------------------------------------------------------

    /*  FUNCTIONAL REQUIREMENT:
     *
     *   An actor who is registered as a manager must be able to:
     * - Manage his or her services, which includes:
     *      creating them.
     *
     * WHAT WILL WE DO?
     *
     * En este caso de uso vamos a administrar la creación de un servicio:
     *
     * POSITIVE AND NEGATIVE CASES
     *
     * Como caso positivo:
     *
     * ·Crear un servicio logueado como manager.
     *
     * Para forzar el error pueden darse varios casos negativos, como son:
     *
     * · Intenta crear un servicio como un usuario autenticado distinto de manager
     * · Introducir la descripcion y el titulo vacíos
     * · Introducir un script
     * · Introducir una url invalida para la picture.
     * · Introducir el campo de asignado o inapropiado a true.
     */
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

// Tests ------------------------------------------------------------------

    /*  FUNCTIONAL REQUIREMENT:
     *
     *   An actor who is registered as a manager must be able to:
     * - Manage his or her services, which includes:
     *      updating them.
     *
     * En este caso de uso editaremos un servicio y lo guardaremos
     *
     * Como caso positivo:
     * · Editar un servicio como usuario manager el creador
     *
     * Como casos negativos:
     * Para forzar el error pueden darse varios casos, como son:
     *
     * · Introducir un rendezvous asignado a un servicio
     * · Introducir un manager distinto al creador
     * · Introducir un usuario que no sea manager
     * · Introducir un script en el titulo
     * · Introducir un usuario no autenticado
     */
    public void serviseEditTest(final String username, String name, String description, String picture, String serviseBean, Class<?> expected) {

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

    // Tests ------------------------------------------------------------------

    /*  FUNCTIONAL REQUIREMENT:
     *
     *   An actor who is registered as a manager, user or administrator must be able to:
     * - Manage his or her services, which includes:
     *      List them.
     *
     * En este caso listaremos todos los servicios del sistema
     *
     * Como caso positivo:
     * · Listar los servicios como user
     * · Listar los servicios como manager
     * · Listar los servicios como administrador
     *
     * Como casos negativos:
     * Para forzar el error pueden darse varios casos, como son:
     *
     * · Listar los servicios sin estar autenticado
     */
    public void listOfServiseTest(final String username,final Class<?> expected){
        Class<?> caught = null;

        try {
            this.authenticate(username);

            this.serviseService.findAll();

            this.unauthenticate();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
    }

    // Tests ------------------------------------------------------------------

    /*  FUNCTIONAL REQUIREMENT:
     *
     *   An actor who is registered as a manager must be able to:
	 * - Manage his or her services, which includes:
	 *      deleting them as long as they are not required by any rendezvouses.
     *
     * En este caso de uso vamos a admisnistrar la eliminación de un servicio
     *
     * Como caso positivo:
     * · Eliminar un servicio logueado como manager que no tiene asignado un rendezvous
     *
     * Como casos negativos:
     * Para forzar el error pueden darse varios casos, como son:
     *
     * · Introducir un rendezvous asignado a un servicio
     * · Introducir un manager distinto al creador
     * · Introducir un usuario que no sea manager
     * · Introducir un usuario que no esté autenticado
     */

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


    // Tests ------------------------------------------------------------------

    /*  FUNCTIONAL REQUIREMENT:
     *
     *   An actor who is registered as a administrator must be able to:
     * - Cancel a service that he or she finds inappropriate. Such services cannot be re-quested for
     *  any rendezvous. They must be flagged appropriately when listed.
     *
     * En este caso de uso vamos a admisnistrar la eliminación de un servicio
     *
     * Como caso positivo:
     * · Marcar un servicio como inapropiado logueado como administrador
     *
     * Como casos negativos:
     * Para forzar el error pueden darse varios casos, como son:
     *
     * · Introducir un usuario autenticado como manager
     * · Introducir un usuario autenticado como user
     * · Introducir un usuario no autenticado
     */
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

                // El usuario del servicio editará este -> true

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

                // Editar un servicio con un srcipt-> false
                {
                        "manager1", "<script>", "descripcion1", "http://www.picture.com","servise1", ConstraintViolationException.class
                },

                // Editar un servicio sin estar autenticado -> false
                {
                        null, "julio", "descripcion1", "http://www.picture.com", "servise1", IllegalArgumentException.class
                },


        };
        for (int i = 0; i < testingData.length; i++)
            this.serviseEditTest((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4],
                    (Class<?>) testingData[i][5]);
    }

    @Test
    public void driverListServiseTest() {

        final Object testingData[][] = {
                // Alguien sin registrar/logueado -> true
                {
                        "manager1", null
                },
                // Alguien sin registrar/logueado -> true
                {
                        "user1", null
                },
                // Otro Usuario -> true
                {
                        "administrator", null
                },
                // Un administrador -> true
                {
                        null, IllegalArgumentException.class
                },

        };
        for (int i = 0; i < testingData.length; i++)
            this.listOfServiseTest((String) testingData[i][0], (Class<?>) testingData[i][1]);
    }

    @Test
    public void driverDeleteServise() {

        final Object testingData[][] = {

                //Borrar servicio no asignado a un rendezvous
                {
                        "manager2", "servise3", null
                },

                // Borrar un servicio con un rendezvous asignado -> false
                {
                        "manager1", "servise1", IllegalArgumentException.class
                },
                // Borrar una servise logueado como un manager que no ha creado el servicio -> false
                {
                        "manager1", "servise4", IllegalArgumentException.class
                },

                // Borrar una servise logueado como otro actor que no sea manager -> false
                {
                        "user1", "servise4", IllegalArgumentException.class
                },
                // Borrar una servise sin estar logueado -> false
                {
                        null, "servise1", IllegalArgumentException.class
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
                // marcar un servise como inapropiado logueado como manager  -> false
                {
                        "manager2", "servise1", IllegalArgumentException.class
                },

                // marcar un servise como inapropiado logueado como user -> false
                {
                        "user1", "servise1", IllegalArgumentException.class
                },

                // marcar un servise como inapropiado no autenticado -> false
                {
                        null , "servise1", IllegalArgumentException.class
                },

        };
        for (int i = 0; i < testingData.length; i++)
            this.serviseInappropriate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
    }
}
