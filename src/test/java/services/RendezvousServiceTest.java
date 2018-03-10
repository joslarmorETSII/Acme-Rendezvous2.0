package services;

import domain.Rendezvous;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import utilities.AbstractTest;

import javax.transaction.Transactional;
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


            this.rendezvousService.save(result);

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
                // Crear un curso estando logueado como academy -> true
                {
                        "user1", "name1","description1","12/03/2020 12:00","www.picture.com",20.99,13.09,false,false,false, null
                },
                // Crear un curso sin estar logueado --> false
                {
                        null,"name1","description1","12/03/2020 12:00","www.picture.com",20.99,13.09,false,false,false, IllegalArgumentException.class
                },
                // Crear un curso logueado como dancer -> false
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
}
