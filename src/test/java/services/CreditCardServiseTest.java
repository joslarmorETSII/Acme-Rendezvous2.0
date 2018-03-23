package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import utilities.AbstractTest;
import domain.CreditCard;

@ContextConfiguration(locations = "classpath:spring/junit.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CreditCardServiseTest extends AbstractTest {

    @Autowired
    private CreditCardService	creditCardService;

    // Tests ------------------------------------------------------------------

    /*  FUNCTIONAL REQUIREMENT:
     *
     *   An actor who is authenticated as a user must be able to:
     * - He or she must specify a valid credit card in every request for a service.
     *
     * WHAT WILL WE DO?
     *
     * En este caso de uso un usuario va a crear una creditCard:
     *
     * POSITIVE AND NEGATIVE CASES
     *
     * Como caso positivo:
     *
     * · Editar credit card de su user y atributos correctos (todos).
     *
     * Para forzar el error pueden darse varios casos negativos, como son:
     *
     * · Editar credit card con administrador autentificado.
     * . Editar credit card sin autentificar.
     * . Editar credit card con atributos incorrectos.
     * . Editar credit card con un script.
     * . Editar creditCard estando logueado con to do vacio.
     */

    public void creditCardEdit(final String username, final String holder, final String brand,
                               final String number, final Integer expirationMonth, final Integer expirationYear,
                               final Integer cvv, String creditCardBean,  final Class<?> expected) {

        Class<?> caught = null;

        try {

            this.authenticate(username);

            // Inicializamos los atributos para la edición
            CreditCard creditCard;
            creditCard = creditCardService.findOne(super.getEntityId(creditCardBean));

            creditCard.setHolder(holder);
            creditCard.setBrand(brand);
            creditCard.setNumber(number);
            creditCard.setExpirationMonth(expirationMonth);
            creditCard.setExpirationYear(expirationYear);
            creditCard.setCvv(cvv);

            // Guardamos
            this.creditCardService.save(creditCard);
            creditCardService.flush();

            this.unauthenticate();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
    }

    /*  FUNCTIONAL REQUIREMENT:
     *
     *   An actor who is authenticated as a user must be able to:
     * - He or she edit a valid credit card.
     *
     * WHAT WILL WE DO?
     *
     * En este caso de uso un usuario va a crear una creditCard:
     *
     * POSITIVE AND NEGATIVE CASES
     *
     * Como caso positivo:
     *
     * · Crear creditCard con atributos válidos.
     *
     * Para forzar el error pueden darse varios casos negativos, como son:
     *
     * · Crear creditCard sin autentificar.
     * · Crear creditCard con atributos inválidos.
     * · Crear creditCard autenticado como manager.
     * . Crear creditCard estando logueado con todos los campos vacio.
     */

    public void creditCardCreate(final String username, final String holder, final String brand, final String number, final Integer expirationMonth, final Integer expirationYear, final Integer cvv, final Class<?> expected) {

        Class<?> caught = null;

        try {

            this.authenticate(username);

            // Inicializamos los atributos para la edición
            final CreditCard creditCard = this.creditCardService.create();

            creditCard.setHolder(holder);
            creditCard.setBrand(brand);
            creditCard.setNumber(number);
            creditCard.setExpirationMonth(expirationMonth);
            creditCard.setExpirationYear(expirationYear);
            creditCard.setCvv(cvv);

            // Guardamos
            this.creditCardService.save(creditCard);
            creditCardService.flush();
            this.unauthenticate();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
    }

    /*  FUNCTIONAL REQUIREMENT:
     *
     *   An actor who is authenticated as a user must be able to:
     * - He or she must specify a valid credit card in every request for a service.
     *
     * WHAT WILL WE DO?
     *
     * En este caso de uso un usuario va a borrar una creditCard:
     *
     * POSITIVE AND NEGATIVE CASES
     *
     * Como caso positivo:
     *
     * · Borrar una creditCard estando logueado como usuario con su creditCard.
     *
     * Para forzar el error pueden darse varios casos negativos, como son:
     *
     * · Borrar una creditCard logueado como usuario sin ser su creditCard.
     * · Borrar una creditCard sin estar logueado.
     * · Borrar una creditCard logueado como manager.
     */

    public void creditCardDelete(final String username, String creditCardBean, final Class<?> expected) {
        Class<?> caught = null;

        try {

            this.authenticate(username);

            // Inicializamos los atributos para la edición
            final CreditCard creditCard;

            creditCard = creditCardService.findOne(super.getEntityId(creditCardBean));

            // Borramos
            this.creditCardService.delete(creditCard);
            creditCardService.flush();

            this.unauthenticate();

        } catch (final Throwable oops) {

            caught = oops.getClass();

        }

        this.checkExceptions(expected, caught);
    }
    // Drivers ----------------------------------------------------------------

    @Test
    public void driverCreditCardEdit() {

        final Object testingData[][] = {
                // Editar credit card de su user y atributos correctos (todos) -> true
                {
                        "user1", "holder", "brand", "4532865767277390", 12, 2020, 100, "creditCard1", null
                },
                // Editar credit card con administrador autentificado -> false
                {
                        "admin", "holder", "brand", "4532865767277390", 12, 2020, 100,"creditCard1", IllegalArgumentException.class
                },
                // Editar credit card sin autentificar -> false
                {
                        null, "holder", "brand", "4532865767277390", 12, 2020, 100,"creditCard1", IllegalArgumentException.class
                },
                // Editar credit card con atributos incorrectos -> false
                {
                        "user1", "holder", "brand", "4532865767277390", null, 2020, 100,"creditCard1", ConstraintViolationException.class
                },
                // Editar credit card con un script -> false
                {
                        "user1", "<script>", "brand", "4532865767277390", 12, 2020, 100,"creditCard1", ConstraintViolationException.class
                },
                // Editar creditCard estando logueado con to do vacio  --> false
                {
                        "user1","", "", "", null, null, null,"creditCard1", ConstraintViolationException.class
                },

        };
        for (int i = 0; i < testingData.length; i++)
            this.creditCardEdit((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Integer) testingData[i][4], (Integer) testingData[i][5], (Integer) testingData[i][6], (String) testingData[i][7],
                    (Class<?>) testingData[i][8]);
    }
    @Test
    public void driverCreateCreditCard() {

        final Object testingData[][] = {

                // Crear creditCard sin autentificar -> false
                {
                        null, "holder", "brand", "4532865767277390", 12, 2020, 100, IllegalArgumentException.class
                },
                // Crear creditCard con atributos válidos -> true
                {
                        "user1", "holder", "brand", "4532865767277390", 12, 2020, 100, null
                },
                // Crear creditCard con atributos inválidos -> false
                {
                        "user1", "holder", "brand", "4532865767277391", 12, 2022, 500, ConstraintViolationException.class
                },
                // Crear creditCard autenticado como manager -> false
                {
                        "manager1", "holder", "brand", "4532865767277390", 12, 2022, 500, IllegalArgumentException.class
                },
                // Crear creditCard estando logueado con todos los campos vacio  --> false
                {
                        "user1","", "", "", null, null, null, ConstraintViolationException.class
                },

        };
        for (int i = 0; i < testingData.length; i++)
            this.creditCardCreate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Integer) testingData[i][4], (Integer) testingData[i][5], (Integer) testingData[i][6], (Class<?>) testingData[i][7]);
    }

    @Test
    public void driverDeleteCreditCard() {

        final Object testingData[][] = {
                // Borrar una creditCard logueado como usuario sin ser su creditCard -> false
                {
                        "user1", "creditCard2", IllegalArgumentException.class
                },
                // Borrar una creditCard sin estar logueado -> false
                {
                        null, "creditCard1", IllegalArgumentException.class
                },
                // Borrar una creditCard logueado como manager -> false
                {
                        "manager1", "creditCard1", IllegalArgumentException.class
                },
                // Borrar una creditCard estando logueado como usuario con su creditCard -> true
                {
                        "user1", "creditCard1", null
                },

        };
        for (int i = 0; i < testingData.length; i++)
            this.creditCardDelete((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
    }
}
