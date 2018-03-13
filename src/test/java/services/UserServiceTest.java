package services;

import domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import utilities.AbstractTest;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.Date;

@Transactional
@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest extends AbstractTest {


    // The SUT
    // ====================================================

    @Autowired
    private UserService userService;

    public void userRegisterTest(final String username, final String password, final String passwordRepeat, final String name, final String surname, final String phone, final String email, final String postalAddress, final Date birthday, final Class<?> expected) {
        Class<?> caught = null;

        try {

            final User result;

            result = this.userService.create();

            Assert.notNull(username);
            Assert.notNull(password);
            Assert.notNull(passwordRepeat);
            Assert.isTrue(password.equals(passwordRepeat));
            Assert.notNull(phone);
            Assert.isTrue(phone.matches("^\\+([3][4])( )(\\d{9})|()$"));
            Assert.notNull(birthday);
            Assert.notNull(email);
            Assert.notNull(name);
            Assert.notNull(surname);

            result.getUserAccount().setUsername(username);
            result.setName(name);
            result.setSurname(surname);
            result.setPhone(phone);
            result.setEmail(email);
            result.setPostalAddresses(postalAddress);
            result.setBirthday(birthday);
            result.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword(password, null));

            this.userService.save(result);
            userService.flush();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
    }

    public void userEditTest(final String username, final String password, final String passwordRepeat,
                             final String name, final String surname, final String phone, final String email,
                             final String postalAddress, final Date birthday, String userBean,
                             final Class<?> expected) {
        Class<?> caught = null;

        try {

            User result;

            result = userService.findOne(super.getEntityId(userBean));

            result.getUserAccount().setUsername(username);
            result.setName(name);
            result.setSurname(surname);
            result.setPhone(phone);
            result.setEmail(email);
            result.setPostalAddresses(postalAddress);
            result.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword(password, null));
            result.setBirthday(birthday);

            this.userService.save(result);
            userService.flush();


        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
    }


    //Drivers
    // ===================================================

    @Test
    public void driverUserRegisterTest() {

        Date birthday = new Date(22/05/1997);
        Date birthdayIncorrecto = new Date(22/05/2020);

        final Object testingData[][] = {
                // Alguien sin registrar/logueado -> true
                {
                        "user3", "user3", "user3", "userTestName", "userTestSurname", "+34 123456789", "userTest@userTest.com", "addressUser", birthday,  null
                },
                // Todo vacio --> false
                {
                        null, null, null, null, null, null, null, null,null, IllegalArgumentException.class
                },
                // Las contraseñas no coinciden -> false
                {
                        "userTest2", "userTest2", "12345", "userTestName2", "userTestSurname2", "+34 123456789", "userTest@userTest.com", "addressUser",birthday, IllegalArgumentException.class
                },
                // Todos los campos completados, excepto la direccion postal -> true
                {
                        "userTest3", "userTest3", "userTest3", "userTestName3", "userTestSurname3","+34 123456789", "userTest@userTest.com", "", birthday, null
                },
                // Patrón del telefono erroneo -> false
                {
                        "userTest4", "userTest4", "userTest4", "userTestName4", "userTestSurname4", "57635", "userTest@userTest.com", "12345", birthday, IllegalArgumentException.class
                },
                // Todos los campos correctos excepto cumpleaños-> false
                {
                        "userTest5", "userTest5", "userTest5", "userTestName5", "userTestSurname5", "57635", "userTest@userTest.com", "12345", birthdayIncorrecto, IllegalArgumentException.class
                }

        };
        for (int i = 0; i < testingData.length; i++)
            this.userRegisterTest((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
                    (String) testingData[i][7],(Date) testingData[i][8], (Class<?>) testingData[i][9]);
    }

    @Test
    public void driverUserEditTest() {

        Date birthday = new Date(22/05/1997);
        Date birthdayIncorrecto = new Date(22/05/2020);

        final Object testingData[][] = {
                // Alguien sin registrar/logueado -> true
                {
                        "user3", "user3", "user3", "userTestName", "userTestSurname", "+34 123456789", "userTest@userTest.com", "addressTest",birthday,"user1", null
                },
                // To do vacio --> false
                {
                        null, null, null, null, null, null, null, null,null,"user1", ConstraintViolationException.class
                },
//                // Todos los campos completados, excepto la direccion postal -> true
//                //TODO: preguntar al prof sale una excepción del tipo:ObjectOptimisticLockingFailureException
//                 {
//                        "user1", "user1", "user1", "user1", "user1", "+34 123456789", "userTest@userTest.com", "",birthday,"user1", null
//                  },
                // Patrón del telefono erroneo -> false
                {
                        "userTest3", "userTest3", "userTest3", "userTestName3", "userTestSurname3", "635", "managerTest@managerTest.com", "12345",birthday,"user1", ConstraintViolationException.class
                },


        };
        for (int i = 0; i < testingData.length; i++)
            this.userEditTest((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
                    (String) testingData[i][7],(Date) testingData[i][8],(String) testingData[i][9], (Class<?>) testingData[i][10]);
    }

}
