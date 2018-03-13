package services;

import domain.Rendezvous;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import repositories.RendezvousRepository;
import utilities.AbstractTest;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Transactional
@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class RendezvousServiceTest extends AbstractTest {

    // The SUT
    // ====================================================

    @Autowired
    private RendezvousService rendezvousService;

    // Tests
    // ====================================================

   // comprobamos que cualquiera puede listar las academias que existen en nuestra aplicación

    public void listOfRendezvousTest(final String username,final Class<?> expected){
        Class<?> caught = null;
        try {
            this.authenticate(username);

            this.rendezvousService.findAll();

            this.unauthenticate();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
    }

   /* Create a new rendezvous.

              En este caso de uso un usuario puede crear un rendezvous.*/

    @SuppressWarnings("deprecation")
    public void rendezvousCreateTest(final String username, final String name,String description1,String moment1,String picture1,Double lat1,Double lngi1,Boolean res1,Boolean res2,Boolean res3, final Class<?> expected) {
        Class<?> caught = null;

        try {

            this.authenticate(username);

            final Rendezvous result = this.rendezvousService.create();

            result.setName(name);
            result.setDescription(description1);

            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            result.setMoment(formatter.parse(moment1));
            result.setPicture(picture1);
            result.getLocation().setLatitude(lat1);
            result.getLocation().setLongitude(lngi1);
            result.setFinalMode(res1);
            result.setDeleted(res2);
            result.setForAdults(res3);


           Rendezvous r= this.rendezvousService.save(result);
            r = result;
            this.unauthenticate();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
    }

    /*
     * Edit a new rendezvous.
     *
     * En este caso de uso un usuario puede editar un rendezvous existente.
     */


    public void editRendezvousTest(final String username, final String name,String description1,String moment1,String picture1,
                                   Double lat1,Double lngi1,Boolean res1,Boolean res2,Boolean res3,String rendezvousBean, final Class<?> expected) {
        Class<?> caught = null;

        try {

            this.authenticate(username);

            Rendezvous result= rendezvousService.findOne(super.getEntityId(rendezvousBean));



            result.setName(name);
            result.setDescription(description1);

            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            result.setMoment(formatter.parse(moment1));
            result.setPicture(picture1);
            result.getLocation().setLatitude(lat1);
            result.getLocation().setLongitude(lngi1);
            result.setFinalMode(res1);
            result.setDeleted(res2);
            result.setForAdults(res3);


            rendezvousService.save(result);
            rendezvousService.flush();


            this.unauthenticate();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
    }

    /*
     * Delete a rendezvous.
     *
     * En este caso de uso un usuario puede borrar un rendezvous existente (Borrado virtual)
     */

    public void deleteRendezvousTest(final String username, final int courseId, final Class<?> expected) {
        Class<?> caught = null;

        try {
         //   final Course course = this.courseService.findOne(courseId);

            this.authenticate(username);

          //  this.courseService.delete(course);

            this.unauthenticate();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
    }

    //Drivers
    // ===================================================

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
            this.listOfRendezvousTest((String) testingData[i][0], (Class<?>) testingData[i][1]);
    }
    @Test
    public void driverRendezvousCreateTest() {
        //DateFormat formatter= new SimpleDateFormat("dd/MM/yyyy HH:mm");

        final Object testingData[][] = {
                // Crear un rendezvous estando logueado como user -> true
    {
        "user1", "name1","description1","12/03/2020 12:00","www.picture.com",20.99,13.09,false,false,false, null
    },
    // Crear un rendezvous sin estar logueado --> false
    {
        null,"name1","description1","12/03/2020 12:00","www.picture.com",20.99,13.09,false,false,false, IllegalArgumentException.class
    },
    // Crear un curso logueado como manager  -> false
    {
        "manager1","name1","description1","12/03/2020 12:00","www.picture.com",20.99,13.09,false,false,false, IllegalArgumentException.class
    },
    // Crear un curso rendezvous con momento en el pasado -> false
    {
        "manager1","name1","description1","12/03/2012 12:00","www.picture.com",20.99,13.09,false,false,false, IllegalArgumentException.class
    },

};
        for (int i = 0; i < testingData.length; i++)
        this.rendezvousCreateTest((String) testingData[i][0], (String) testingData[i][1],(String) testingData[i][2],(String)testingData[i][3] , (String)testingData[i][4],(Double) testingData[i][5],(Double) testingData[i][6],(Boolean) testingData[i][7],(Boolean) testingData[i][8],(Boolean) testingData[i][9], (Class<?>) testingData[i][10]);

        }



    @Test
    public void driverEditRendezvousTest() {

        final Object testingData[][] = {
                // Crear un rendezvous estando logueado como user -> true
              {
                        "user1", "name1","description1","12/03/2020 12:00","http://www.picture.com",20.99,13.09,false,false,false,"rendezvous1", null
               },
                // Crear un rendezvous sin estar logueado --> false
               {
                        null,"name1","description1","12/03/2020 12:00","http://www.picture.com",20.99,13.09,false,false,false,"rendezvous1", IllegalArgumentException.class
                },
                // editar el modo final y pasarlo a true -> true
               {
                        "user1","name1","description1","12/03/2020 12:00","http://www.picture.com",20.99,13.09,true,false,false,"rendezvous1", null
                },
                // Crear un curso logueado como manager  -> false
                {
                        "manager1","name1","description1","12/03/2020 12:00","http://www.picture.com",20.99,13.09,true,false,false, "rendezvous1",IllegalArgumentException.class
               },
                // Edit la description con un script -> false // todo: Preguntar al profesor, no se camptura el error de validacion
                {
                "user1","name1","<script>","12/03/2020 12:00","http://www.picture.com",20.99,13.09,true,false,false,"rendezvous1", ConstraintViolationException.class
            }


        };
        for (int i = 0; i < testingData.length; i++)
            this.editRendezvousTest((String) testingData[i][0], (String) testingData[i][1],(String) testingData[i][2],(String)testingData[i][3] , (String)testingData[i][4],(Double) testingData[i][5],(Double) testingData[i][6],(Boolean) testingData[i][7],(Boolean) testingData[i][8],(Boolean) testingData[i][9],(String) testingData[i][10], (Class<?>) testingData[i][11]);

    }


}
