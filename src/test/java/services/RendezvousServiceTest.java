package services;

import domain.Actor;
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

    @Autowired
    private ActorService    actorService;
    // Tests
    // ====================================================
    /*  FUNCTIONAL REQUIREMENT:
        1. List the rendezvouses in the system and navigate to the profiles of the correspond-ing creators and attendants.

   /*  To check the list of a rendezvous in our system, the system must check the different users and unautification users
    *
    *  En este test, se comprueba el Listar de los rendezvous.
    *  Para ello simulamos el logueo de distintos usuarios tanto autentificados como no autentificados
    *
    * Como casos positivos:

    * Simular el logueo como user1.
    * Simular el logueo como use2.
    * Simular el logueo como admin.
    * Poner a null el campo de usuario


  */

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

              En este caso de uso un usuario puede crear un rendezvous.

    /*  FUNCTIONAL REQUIREMENT:
            * An actor who is authenticated as a user must be able to:
            1. Create a rendezvous, which he’s implicitly assumed to attend. Note that a user may edit his or her rendezvouses as long as they aren’t saved them in final mode. Once a rendezvous is saved in final mode, it cannot be edited or deleted by the creator.
             *  To check the create of an answers in our system, the system must check the different attribute of the entity.
    *
    *  En este test, se comprueba el crear un rendezvous.
    *  Para ello  simulamos el logueo de distintos usuario autentificados y crear rendezvouses.
    *
    * Como casos positivos:

    * .Simular el logueo como user1, rellenar correctamente todos los campos.

    * Para forzar el error pueden darse varios casos negativos, como son:

    * .Simular el logueo como manager.
    * .Poner el campo de momento en el pasado
    * .Poner el usuario a null.
    */

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

     /*  FUNCTIONAL REQUIREMENT:
            * An actor who is authenticated as a user must be able to:
            1. Update or delete the rendezvouses that he or she’s created. Deletion is virtual, that is: the information is not removed from the database, but the rendezvous cannot be updated. Deleted rendezvouses are flagged as such when they are displayed.

    *  En este test, se comprueba el editar un rendezvous existente.
    *  Para ello  simulamos el logueo de distintos usuario autentificados e editar un rendezvouse.
    *
    * Como casos positivos:

    * .Simular el logueo como user1, rellenar correctamente todos los campos.

    * Para forzar el error pueden darse varios casos negativos, como son:

        * .Poner el usuario a null.
        * .Poner modo final a true.
        * .Logueo como manager.
        * .Meter un script en description
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
     *
     *  /*  FUNCTIONAL REQUIREMENT:
            * An actor who is authenticated as a user must be able to:
            1. Update or delete the rendezvouses that he or she’s created. Deletion is virtual, that is: the information is not removed from the database, but the rendezvous cannot be updated. Deleted rendezvouses are flagged as such when they are displayed.
            An actor who is authenticated as an administrator must be able to:
            2. Remove a rendezvous that he or she thinks is inappropriate.


    *  En este test, se comprueba borrar un rendezvous existente.
    *  Para ello  simulamos el logueo de distintos usuario autentificados (admin y user)
    *
    * Como casos positivos:

    * .Simular el logueo como user1, rellenar correctamente todos los campos.
    * .Simular el logueo como admin,rellenar correctamente todos los campos.

    * Para forzar el error pueden darse varios casos negativos, como son:

        * .Poner el usuario a null.
        * .Poner modo final a true.
    */


    public void deleteRendezvousTest(final String username, String rendezvousBean,Boolean finalMode, final Class<?> expected) {
        Class<?> caught = null;

        try {
            Rendezvous result= rendezvousService.findOne(super.getEntityId(rendezvousBean));

            this.authenticate(username);
            Actor actor = actorService.findByPrincipal();
            if(actorService.isUser()) {
                this.rendezvousService.delete(result);
            }else if(actorService.isAdministrator()){
                this.rendezvousService.deleteAdmin(result);
            }
            this.unauthenticate();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
    }

    //Drivers
    // ===================================================

    //@Test
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
    //@Test
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
    // Crear un rendezvous logueado como manager  -> false
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
                // Editar un rendezvous estando logueado como user -> true
              {
                        "user1", "name1","description1","12/03/2020 12:00","http://www.picture.com",20.99,13.09,false,false,false,"rendezvous1", null
               },
                // Editar un rendezvous sin estar logueado --> false
               {
                        null,"name1","description1","12/03/2020 12:00","http://www.picture.com",20.99,13.09,false,false,false,"rendezvous1", IllegalArgumentException.class
                },
                // Editar el modo final y pasarlo a true -> true
               {
                        "user1","name1","description1","12/03/2020 12:00","http://www.picture.com",20.99,13.09,true,false,false,"rendezvous1", null
                },
                // Editar un rendezvous logueado como manager  -> false
                {
                        "manager1","name1","description1","12/03/2020 12:00","http://www.picture.com",20.99,13.09,true,false,false, "rendezvous1",IllegalArgumentException.class
               },
                // Edit la description con un script -> false
                {
                "user1","name1","<script>","12/03/2020 12:00","http://www.picture.com",20.99,13.09,true,false,false,"rendezvous1", ConstraintViolationException.class
            }


        };
        for (int i = 0; i < testingData.length; i++)
            this.editRendezvousTest((String) testingData[i][0], (String) testingData[i][1],(String) testingData[i][2],(String)testingData[i][3] , (String)testingData[i][4],(Double) testingData[i][5],(Double) testingData[i][6],(Boolean) testingData[i][7],(Boolean) testingData[i][8],(Boolean) testingData[i][9],(String) testingData[i][10], (Class<?>) testingData[i][11]);

    }


    @Test
    public void driverDeleteRendezvousTest() {

        final Object testingData[][] = {
                // Borrar un rendezvous estando logueado como user -> true
                {
                        "user1", "rendezvous1",false, null
                },

                // Borrar un rendezvous estando logueado como admin -> true
                {
                        "administrator", "rendezvous2",false, null
                },
                // Borrar un rendezvous sin estar logueado -> false
                {
                        null, "rendezvous1",false, IllegalArgumentException.class
                },
                // Borrar un rendezvous que no esta en modo final -> false
                {
                        "user1","rendezvous2",true, IllegalArgumentException.class
               },

        };
        for (int i = 0; i < testingData.length; i++)
            this.deleteRendezvousTest((String) testingData[i][0], (String) testingData[i][1],(Boolean) testingData[i][2], (Class<?>) testingData[i][3]);
    }



}
