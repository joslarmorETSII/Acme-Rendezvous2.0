package services;

import domain.Comment;
import domain.Configuration;
import domain.Rendezvous;
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
public class ConfigurationServiceTest extends AbstractTest {

    @Autowired
    private ConfigurationService configurationService;

    public void editConfigurationTest(String username,String name,String banner,String englishWelcome, String spanishWelcome,String configurationBean, final Class<?> expected){
        Class<?> caught = null;

        try {

            this.authenticate(username);

            Configuration result= configurationService.findOneToEdit(super.getEntityId(configurationBean));

            result.setName(name);
            result.setBanner(banner);
            result.setEnglishWelcome(englishWelcome);
            result.setSpanishWelcome(spanishWelcome);

            configurationService.save(result);
            configurationService.flush();

            this.unauthenticate();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
    }

    @Test
    public void driverConfigurationEditTest() {

            final Object testingData[][] = {
                    // Crear una configuration por defecto -> true
                    {
                        "administrator","nombre del negocio","http://www.banner.com", "englishWelcome", "spanishWelcome","configuration1", null
                    },
                    // Crear una configuration por defecto sin estar logueado -> false
                    {
                        null,"nombre del negocio","http://www.banner.com", "englishWelcome", "spanishWelcome","configuration1", IllegalArgumentException.class
                    },
                    // Crear una configuration por defecto siendo manager-> false
                    {
                        "manager1","nombre del negocio","http://www.banner.com", "englishWelcome", "spanishWelcome","configuration1", IllegalArgumentException.class
                    },
                    // Crear una configuration por defecto con el name vacio -> false
                    {
                            "administrator","","http://www.banner.com", "englishWelcome", "spanishWelcome","configuration1", ConstraintViolationException.class
                    },
                    // Crear una configuration por defecto con el englishWelcome vacio -> false
                    {
                            "administrator","nombre del negocio","http://www.banner.com", "", "spanishWelcome","configuration1", ConstraintViolationException.class
                    },
                    // Crear una configuration por defecto con el spanishWelcome vacio -> false
                    {
                            "administrator","nombre del negocio","http://www.banner.com", "englishWelcome", "","configuration1", ConstraintViolationException.class
                    },
                    // Crear una configuration por defecto con el spanishWelcome con <script> -> false
                    {
                            "administrator","nombre del negocio","http://www.banner.com", "englishWelcome", "<script>","configuration1", ConstraintViolationException.class
                    },
                    // Crear una configuration por defecto con el englishWelcome con <script> -> false
                    {
                            "administrator","nombre del negocio","http://www.banner.com", "<script>", "spanishWelcome","configuration1", ConstraintViolationException.class
                    }

            };
            for (int i = 0; i < testingData.length; i++)
                this.editConfigurationTest((String) testingData[i][0], (String) testingData[i][1],(String) testingData[i][2],(String) testingData[i][3],(String) testingData[i][4],(String) testingData[i][5], (Class<?>) testingData[i][6]);

    }

}
