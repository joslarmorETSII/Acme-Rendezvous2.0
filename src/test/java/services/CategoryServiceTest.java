package services;

import domain.Answer;
import domain.Category;
import domain.Question;
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
public class CategoryServiceTest extends AbstractTest {

    // The SUT ---------------------------------
    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private CategoryService categoryService;


    public void categoryEditTest(String username, String name,String description, String beanName,  Class<?> expected) {
        Class<?> caught=null;

        try {

            this.authenticate(username);

            Category result = categoryService.findOneToEdit(getEntityId(beanName));
            result.setName(name);
            result.setDescription(description);
            categoryService.save(result);
            categoryService.flush();

            this.unauthenticate();

        } catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);

    }

    // Drivers
    // ===================================================

    @Test
    public void driverCategoryEditTest() {
        final Object testingData[][] = {

             //   String username, String answer,String answerBean,String questionBean,  Class<?> expected
                // Editar una Categoria que no esta asignada estando logueado un Admin  -> true
                {
                        "administrator","edited CAt","description category1","category3",null
                },
                // Editar una Categoria que esta asignada estando logueado un Admin  -> false

                {
                        "administrator","new name","description category1","category1",IllegalArgumentException.class
                },
                // Editar una Categoria sin loguearse -> true

                {
                        null,"edited CAt","description category1","category3",IllegalArgumentException.class
                },
                // Editar una Categoria que no esta asignada estando logueado un Manager  -> true

                {
                        "manager1","edited CAt","description category1","category3",IllegalArgumentException.class
                },
                // Editar una Categoria que no esta asignada estando logueado un User  -> true

                {
                        "user1","edited CAt","description category1","category3",IllegalArgumentException.class
                }


        };
        for (int i = 0; i < testingData.length; i++)
            categoryEditTest((String) testingData[i][0], (String) testingData[i][1],
                    (String) testingData[i][2],(String) testingData[i][3],(Class<?>) testingData[i][4]);

    }




}

