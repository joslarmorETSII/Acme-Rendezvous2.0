
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import domain.Rendezvous;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Manager;

@Transactional
@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class ManagerServiceTest extends AbstractTest {

    // The SUT
    // ====================================================

    @Autowired
    private ManagerService	managerService;


    // Tests
    // ====================================================

    /*
     * To check the validity of a new manager in our system, the system must check the vat,
     * the passwords, the name, the surname, the phone, the email and the postal address.
     *
     * En este test, se comprueba el registro de un nuevo manager/a.
     * Para ello introducimos valores correctos e incorrectos para observa el comportamiento de la aplicación.
     */

    /*
     * Register a new manager.
     *
     * En este caso de uso simularemos el registro de un candidato.
     */

    public void managerRegisterTest(final String username, final String password, final String passwordRepeat, final String name, final String surname,final String vat, final String phone, final String email, final String postalAddress, final Class<?> expected) {
        Class<?> caught = null;

        try {

            final Manager result = this.managerService.create();

            Assert.notNull(username);
            Assert.notNull(password);
            Assert.notNull(passwordRepeat);
            Assert.isTrue(password.equals(passwordRepeat));
            Assert.notNull(phone);
            Assert.isTrue(phone.matches("^\\+([3][4])( )(\\d{9})|()$"));
            Assert.notNull(vat);
            Assert.notNull(email);
            Assert.notNull(name);
            Assert.notNull(surname);

            result.getUserAccount().setUsername(username);
            result.setName(name);
            result.setSurname(surname);
            result.setPhone(phone);
            result.setEmail(email);
            result.setPostalAddresses(postalAddress);
            result.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword(password, null));
            result.setVat(vat);

            this.managerService.save(result);
            managerService.flush();


        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
    }

    /*
     * Edit a manager.
     *
     * En este caso de uso editamos los datos de una manager registrado.
     */

    public void managerEditTest(final String username, final String password, final String passwordRepeat, final String name, final String surname,final String vat, final String phone, final String email, final String postalAddress,String managerBean, final Class<?> expected) {
        Class<?> caught = null;

        try {

            Manager result= managerService.findOne(super.getEntityId(managerBean));



            result.getUserAccount().setUsername(username);
            result.setName(name);
            result.setSurname(surname);
            result.setPhone(phone);
            result.setEmail(email);
            result.setPostalAddresses(postalAddress);
            result.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword(password, null));
            result.setVat(vat);

            this.managerService.save(result);
            managerService.flush();


        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
    }


    //Drivers
    // ===================================================

    @Test
    public void driverManagerRegisterTest() {

        final Object testingData[][] = {
                // Alguien sin registrar/logueado -> true
               {
                        "manager3", "manager3", "manager3", "managerTestName", "managerTestSurname","123-ASD", "+34 123456789", "managerTest@managerTest.com", "addressTest", null
                },
                // Todo vacio --> false
               {
                      null, null, null, null, null, null, null, null,null, IllegalArgumentException.class
               },
                // Las contraseñas no coinciden -> false
                {
                        "managerTest1", "managerTest1", "12345", "managerTestName1", "managerTestSurname1","143-ASD", "+34 123456789", "managerTest@managerTest.com", "addressTest", IllegalArgumentException.class
               },
                // Todos los campos completados, excepto la direccion postal -> true
                {
                        "managerTest2", "managerTest2", "managerTest2", "managerTestName2", "managerTestSurname2", "193-ASD","+34 123456789", "managerTest@managerTest.com", "", null
                },
                // Patrón del telefono erroneo -> false
                {
                       "managerTest3", "managerTest3", "managerTest3", "managerTestName3", "managerTestSurname3","153-ASD", "635", "managerTest@managerTest.com", "12345", IllegalArgumentException.class
                },
                // Patrón del vat erroneo -> false
                {
                        "manager400", "manager400", "manager400", "managerName4", "managerSurname4","000+AAA", "+34 123456789", "manager@manager.com", "12345", ConstraintViolationException.class
                },


//todo Preguntar al profesor
        };
        for (int i = 0; i < testingData.length; i++)
            this.managerRegisterTest((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
                    (String) testingData[i][7],(String) testingData[i][8], (Class<?>) testingData[i][9]);
    }


    // Driver Edit
    @Test
    public void driverManagerEditTest() {

        final Object testingData[][] = {
                // Alguien sin registrar/logueado -> true
                {
                        "manager3", "manager3", "manager3", "managerTestName", "managerTestSurname","123-ASD", "+34 123456789", "managerTest@managerTest.com", "addressTest","manager1", null
                },


                // Todos los campos completados, excepto la direccion postal -> true
                //TODO: preguntar al prof sale una excepción del tipo: ObjectOptimisticLockingFailureException
                {
                        "manager2", "manager2", "manager2", "manager2", "manager2", "193-ASD","+34 123456789", "managerTest@managerTest.com", "","manager2", null
               },
                // Patrón del telefono erroneo -> false
                {
                                "managerTest3", "managerTest3", "managerTest3", "managerTestName3", "managerTestSurname3","153-ASD", "635", "managerTest@managerTest.com", "12345","manager1", ConstraintViolationException.class
                },
                         // Patrón del vat erroneo -> false
                {
                        "manager400", "manager400", "manager400", "managerName4", "managerSurname4","000+AAA", "+34 123456789", "manager@manager.com", "12345","manager1", ConstraintViolationException.class
                },

                {
                        "", "manager55", "manager55", "managerTestName5", "managerTestSurname5","173-ASD", "+34 123456789", "managerTest@managerTest.com", "addressTest","manager1", ConstraintViolationException.class
                },

                // Todo vacio --> false
                {
                        null, null, null, null, null, null, null, null,null,"manager1", ConstraintViolationException.class
                },

        };
        for (int i = 0; i < testingData.length; i++)
            this.managerEditTest((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
                    (String) testingData[i][7],(String) testingData[i][8],(String) testingData[i][9], (Class<?>) testingData[i][10]);
    }

}

