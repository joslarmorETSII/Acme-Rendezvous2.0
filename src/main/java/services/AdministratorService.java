package services;

import domain.Administrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.AdministratorRepository;
import security.LoginService;
import security.UserAccount;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class AdministratorService {

    // Managed repository -----------------------------------------------------

    @Autowired
    private AdministratorRepository administratorRepository;

    // Supporting services ----------------------------------------------------

    @Autowired
    private UserAccountService userAccountService;

    // Constructors -----------------------------------------------------------

    public AdministratorService() {
        super();
    }

    // Simple CRUD methods ----------------------------------------------------


    public Administrator save(final Administrator administrator) {

        Assert.notNull(administrator);

        Administrator result;

        result = this.administratorRepository.save(administrator);

        return result;
    }

    // Other business methods -------------------------------------------------

    public Administrator findByPrincipal() {
        Administrator result;
        UserAccount userAccount;

        userAccount = LoginService.getPrincipal();
        Assert.notNull(userAccount);

        result = this.findByUserAccount(userAccount);
        Assert.notNull(result);

        return result;
    }

    public Administrator findByUserAccount(final UserAccount userAccount) {

        Assert.notNull(userAccount);
        Administrator result;
        result = this.administratorRepository.findByUserAccountId(userAccount.getId());
        return result;
    }



}
