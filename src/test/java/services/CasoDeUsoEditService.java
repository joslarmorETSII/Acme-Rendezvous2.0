package services;

import domain.Category;
import domain.Manager;
import domain.Servise;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import utilities.AbstractTest;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.Collection;

@Transactional
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
        "classpath:spring/config/packages.xml",
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class CasoDeUsoEditService extends AbstractTest{

    // The SUT ---------------------------------
    @Autowired
    private ServiseService serviseService;

    @Autowired
    private CategoryService categoryService;

    // Tests ------------------------------------------------------------------

    /*  CASO DE USO:
     *
     *   Un Actor se authentifica como Manager, lista todos los Servicios del sistema,
     *   elige uno de los que creado para editar lo edita y lo guarda.
     *
     *
     *
     * COMO SE VA HA HACER?
     *
     * En este caso de uso vamos a hacer tests positivos y negativos:
     *
     * Como caso positivo:
     *
     * · Editar uno de los servicios que ha creado logueado como manager,
     *   proporcionando datos validos.
     *
     * Para forzar el error pueden darse varios casos negativos, como son:
     *
     * · Intentar editar un servicio como un usuario autenticado distinto de manager.
     * . Intentar editar un servicio de otro manager.
     * · Introducir la descripcion y el titulo vacíos.
     * · Introducir un script.
     * · Introducir una url invalida para la picture.
     * ·
     */


    public void serviseEditTest(final String username, String name, String description, String picture, String serviseBean,String categoryBean, Class<?> expected) {

        Class<?> caught = null;
        Servise serviseToEdit;
        Category category;

        try {

            this.authenticate(username);

            serviseService.findAll();
            serviseToEdit = serviseService.findOneToEdit(super.getEntityId(serviseBean));
            category = categoryService.findOne(super.getEntityId(categoryBean));
            serviseToEdit.setName(name);
            serviseToEdit.setDescription(description);
            serviseToEdit.setPicture(picture);
            serviseToEdit.setCategory(category);
            this.serviseService.save(serviseToEdit);
            serviseService.flush();

            this.unauthenticate();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
    }



    @Test
    public void driverServiseEdit() {

        final Object testingData[][] = {

                // 1. Editar un servicio estando logueado como Manager -> true

                {
                        "manager1", "Service edited", "description1", "http://www.picture.com","servise1","category1", null
                },
                // 2. Editar un servicio estando logueado como Manager sin especificar la foto -> true
                {
                        "manager1", "Service edited", "description1", "","servise1","category1", null
                },
                // 3. Editar un servicio estando logueado como Manager  -> true
                {
                        "manager2", "Service manager2 ", "description manager2", "http://www.picture-manager2.com","servise2","category1", null
                },
                // 4. Editar un servicio que no pertenece al manager que lo creo -> false
                {
                        "manager2", "Service edited 2", "Edited by Manager2", "http://www.picture.com", "servise1","category1", IllegalArgumentException.class
                },

                // 5. Editar un servicio siendo un usuario distinto de manager -> false
                {
                        "user1", "Service", "Edited by User1", "http://www.picture.com", "servise1","category1", IllegalArgumentException.class
                },

                // 6. Editar un servicio sin estar autenticado -> false
                {
                        null, "service 3", "descripcion1", "http://www.picture.com", "servise1","category1", IllegalArgumentException.class
                },

                // 7. Editar un servicio estando logueado como Manager sin dar una descripción -> false
                {
                        "manager1", "Service edited", "", "http://www.picture.com","servise1","category1", ConstraintViolationException.class
                },

                // 8. Editar un servicio sin especificar la Categoria-> false
                {
                        "manager1", "Service edited", "description2", "http://www.picture.com","servise1","", ConstraintViolationException.class
                },

                // 9. Editar un servicio con un srcipt-> false
                {
                        "manager1", "<script>", "descripcion1", "http://www.picture.com","servise1","category1", ConstraintViolationException.class
                },
                // 10. Editar un servicio con un srcipt-> false
                {
                        "manager1", "", "", "","servise1","category1", ConstraintViolationException.class
                },

        };
        for (int i = 0; i < testingData.length; i++)
            this.serviseEditTest((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4],
                    (String) testingData[i][5], (Class<?>) testingData[i][6]);
    }





}
