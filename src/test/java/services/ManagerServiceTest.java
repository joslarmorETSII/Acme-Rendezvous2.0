
package services;

import javax.transaction.Transactional;

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

            this.managerService.save(result);

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
                        "managerTest", "managerTest", "12345", "managerTestName", "managerTestSurname","123-ASD", "+34 123456789", "managerTest@managerTest.com", "addressTest", IllegalArgumentException.class
                },
                // Todos los campos completados, excepto la direccion postal -> true
                {
                        "managerTest", "managerTest", "managerTest", "managerTestName", "managerTestSurname", "123-ASD","+34 123456789", "managerTest@managerTest.com", "addressTest", null
                },
                // Patrón del telefono erroneo -> false
                {
                        "managerTest", "managerTest", "managerTest", "managerTestName", "managerTestSurname","123-ASD", "57635", "managerTest@managerTest.com", "12345", IllegalArgumentException.class
                },
                // Patrón del vat erroneo -> false
                {
                        "managerTest", "managerTest", "managerTest", "managerTestName", "managerTestSurname","12 ASD", "57635", "managerTest@managerTest.com", "12345", IllegalArgumentException.class
                }

        };
        for (int i = 0; i < testingData.length; i++)
            this.managerRegisterTest((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
                    (String) testingData[i][7],(String) testingData[i][8], (Class<?>) testingData[i][9]);
    }

}

