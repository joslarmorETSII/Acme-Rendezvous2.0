package services;

import domain.*;
import forms.ManagerForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import repositories.ManagerRepository;
import security.LoginService;
import security.UserAccount;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class ManagerService {
    // Managed repository -----------------------------------------------------

    @Autowired
    private ManagerRepository managerRepository;

    // Supporting services ----------------------------------------------------

    @Autowired
    private ActorService actorService;

    @Autowired
    private UserAccountService userAccountService;



    // Constructors -----------------------------------------------------------

    public ManagerService() {
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    public Manager create() {
        Manager result;

        result = new Manager();
        result.setServises(new ArrayList<Servise>());
        result.setUserAccount(this.userAccountService.create("MANAGER"));
        return result;
    }

    public Manager findOne(final int managerId) {
        Assert.notNull(managerId);
        Manager result;
        result = this.managerRepository.findOne(managerId);
        return result;
    }

    public Collection<Manager> findAll() {

        Collection<Manager> result;
        result = this.managerRepository.findAll();
        return result;
    }

    public Manager save(final Manager manager) {

        Assert.notNull(manager);

        Manager result;

        if (manager.getId() == 0) {
            result = this.managerRepository.save(manager);
        } else
            result = this.managerRepository.save(manager);

        return result;
    }

    // Other business methods

    public Manager findByPrincipal() {

        Manager result;
        final UserAccount userAccount = LoginService.getPrincipal();
        result = this.findByUserAccountId(userAccount.getId());
        return result;
    }



    public Manager findByUserAccountId(final int userAccountId) {

        Manager result;
        result = this.managerRepository.findByUserAccountId(userAccountId);
        return result;
    }

    public Manager reconstruct(final ManagerForm managerForm, final BindingResult binding) {

        Manager result;

        result = this.create();
        result.getUserAccount().setUsername(managerForm.getUsername());
        result.setName(managerForm.getName());
        result.setSurname(managerForm.getSurname());
        result.setPhone(managerForm.getPhone());
        result.setEmail(managerForm.getEmail());
        result.setPostalAddresses(managerForm.getPostalAddresses());
        result.setVat(managerForm.getVat());

        result.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword(managerForm.getPassword(), null));

        this.comprobarContrasena(managerForm.getPassword(), managerForm.getRepeatPassword(), binding);

        return result;
    }

    private boolean comprobarContrasena(final String password, final String passwordRepeat, final BindingResult binding) {
        FieldError error;
        String[] codigos;
        boolean result;

        if (StringUtils.isNotBlank(password) && StringUtils.isNotBlank(passwordRepeat))
            result = password.equals(passwordRepeat);
        else
            result = false;

        if (!result) {
            codigos = new String[1];
            codigos[0] = "manager.password.mismatch";
            error = new FieldError("managerForm", "password", password, false, codigos, null, "");
            binding.addError(error);
        }

        return result;
    }

   /* public String generateVat(){
        String result;
        Manager manager;
        final Random random = new Random();
        final String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        while (true) {
            final int digit3 = random.nextInt(900) + 100;
            result = digit3 + "-";
            for (int i = 0; i <= 2; i++) {
                final int index = (int) (random.nextFloat() * letters.length());
                result += letters.charAt(index);
            }
            manager = this.managerRepository.findByVat(result);
            if (manager == null)
                break;
        }

        return result;
    }*/

    public Collection<Manager> managersWithMoreServisesThanAvg(){
        return managerRepository.managersWithMoreServisesThanAvg();
    }

    public Collection<Manager> managersWithMoreServisesCancelled() {
        return managerRepository.managersWithMoreServisesCancelled();
    }

    public void flush() {
        managerRepository.flush();
    }
}
