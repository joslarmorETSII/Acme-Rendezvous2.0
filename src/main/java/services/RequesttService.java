package services;

import domain.*;
import forms.RequesttForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import repositories.RequesttRepository;

import java.util.Collection;

@Service
@Transactional
public class RequesttService {

    // Managed repository -----------------------------------------------------

    @Autowired
    private RequesttRepository requestRepository;

    // Managed services -----------------------------------------------------

    @Autowired
    private UserService userService;

    @Autowired
    private CreditCardService creditCardService;


    // Constructors -----------------------------------------------------------

    public RequesttService(){
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    public Requestt create(){
        Requestt result;

        result =  new Requestt();

        return result;
    }

    public Requestt findOne(Integer requestId){
        return requestRepository.findOne(requestId);
    }

    public Requestt save(Requestt request){
        User principal;
        Requestt request1;
        Rendezvous rendezvous;
        Servise servise;

        principal = userService.findByPrincipal();
        Assert.isTrue(principal.equals(request.getRendezvous().getCreator()),"must be the principal");

        rendezvous = request.getRendezvous();
        servise = request.getServise();
        servise.setAssigned(true);

        rendezvous.getServises().add(servise);
        servise.getRendezvouses().add(rendezvous);

        request1 =requestRepository.save(request);

        return request1;
    }

    public Collection<Requestt> findAll(){
        return requestRepository.findAll();
    }

    public void delete(Requestt request){
        User principal;

        principal = userService.findByPrincipal();
       // Assert(principal.getUserAccount().getAuthorities().contains(Authority.ADMIN));

        requestRepository.delete(request);
    }

    public Requestt reconstruct(RequesttForm requesttForm, BindingResult binding) {
        Requestt result ;
        Rendezvous rendezvous;

        rendezvous = requesttForm.getRendezvous();
        Assert.isTrue(!requesttForm.getServise().getInappropriate());
     //   Assert.isTrue(rendezvous.getServise()==null);
        Assert.isTrue(rendezvous.getCreator()==userService.findByPrincipal());

        result = create();
        result.setRendezvous(rendezvous);
        result.setServise(requesttForm.getServise());
        result.setComment(requesttForm.getComment());

        return result;
    }
    public Collection<Requestt> requestsByManagerId(int managerId){
        return requestRepository.requestsByManagerId(managerId);
    }

}
