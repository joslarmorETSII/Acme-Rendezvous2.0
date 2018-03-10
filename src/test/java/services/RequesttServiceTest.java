package services;

import domain.Rendezvous;
import domain.Requestt;
import domain.Servise;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import services.RendezvousService;
import services.RequesttService;
import services.ServiseService;
import utilities.AbstractTest;

import javax.transaction.Transactional;

@Transactional
@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class RequesttServiceTest extends AbstractTest {

    // The SUT ---------------------------------
    @Autowired
    private RequesttService requesttService;

    @Autowired
    private ServiseService serviseService;

    @Autowired
    private RendezvousService rendezvousService;



    public void requesttCreateTest(String username, String serviseBean, String rendezvousBean,String comment, Class<?> expected) {
        Class<?> caught=null;

        try {
            int serviseId = super.getEntityId(serviseBean);
            int rendezvousId = super.getEntityId(rendezvousBean);
            Servise servise = serviseService.findOne(serviseId);
            Rendezvous rendezvous = rendezvousService.findOne(rendezvousId);

            this.authenticate(username);

            Requestt result = this.requesttService.create();
            result.setServise(servise);
            result.setRendezvous(rendezvous);
            result.setComment(comment);

            requesttService.save(result);
            this.unauthenticate();

        } catch (final Throwable oops) {
            caught = oops.getClass();
        }
            this.checkExceptions(expected, caught);

    }

    // Drivers
    // ===================================================

    @Test
    public void driverRequesttCreateTest() {

        final Object testingData[][] = {
                // Crear una requestt estando logueado como user -> true
                {
                        "user1", "servise1", "rendezvous1","", null
                },
                // Crear una requestt estando logueado como user -> true
                {
                        "user1", "servise1", "rendezvous1","comentario test", null
                },
                // Crear una requestt sin estar logueado --> false
                {
                        null, "servise2", "rendezvous1", "",IllegalArgumentException.class
                },
                // Crear una peticion logueado como manager -> false
                {
                        "manager1", "servise2", "rendezvous1","", IllegalArgumentException.class
                }
        };
        for (int i = 0; i < testingData.length; i++)
            requesttCreateTest((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2],
                    (String) testingData[i][3],(Class<?>) testingData[i][4]);

    }


}
