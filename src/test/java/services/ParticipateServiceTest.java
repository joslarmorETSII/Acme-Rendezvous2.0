package services;

import domain.Participate;
import domain.Question;
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
public class ParticipateServiceTest extends AbstractTest {

    // The SUT ---------------------------------
    @Autowired
    private RendezvousService rendezvousService;

    @Autowired ParticipateService participateService;

    public void participateCreateTest(String username, String rendezvousBean,  Class<?> expected) {
        Class<?> caught=null;

        try {
            int rendezvousId = super.getEntityId(rendezvousBean);
            Rendezvous rendezvous = rendezvousService.findOne(rendezvousId);

            this.authenticate(username);

            Participate result = participateService.create();
            result.setRendezvous(rendezvous);

            participateService.save(result);

            this.unauthenticate();


        } catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);

    }

    // Drivers
    // ===================================================

    @Test
    public void driverCreateParticipateTest() {

        final Object testingData[][] = {
                // Participar en un rendezvous estando logueado -> true
                {
                        "user1", "rendezvous3", null
                },
                // Participar en un rendezvous sin loguearse -> false
                {
                        null, "rendezvous3", IllegalArgumentException.class
                },
                // Participar en un rendezvous por segunda vez ->false
                {
                        "user2", "rendezvous3", IllegalArgumentException.class
                }

        };
        for (int i = 0; i < testingData.length; i++)
            this.participateCreateTest((String) testingData[i][0],(String)testingData[i][1] ,(Class<?>) testingData[i][2]);
    }

}
