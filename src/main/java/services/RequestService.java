package services;

import domain.Request;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import repositories.RequestRepository;
import security.Authority;

import java.util.Collection;

@Service
@Transactional
public class RequestService {

    // Managed repository -----------------------------------------------------

    @Autowired
    private RequestRepository requestRepository;

    // Managed services -----------------------------------------------------

    @Autowired
    private UserService userService;


    // Constructors -----------------------------------------------------------

    public RequestService(){
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    public Request create(){
        Request result;

        result =  new Request();

        return result;
    }

    public Request findOne(Integer requestId){
        return requestRepository.findOne(requestId);
    }

    public Request save(Request request){
        User principal;
        Request request1;

        principal = userService.findByPrincipal();
        request1 =requestRepository.save(request);
        principal.getRequests().add(request1);
        userService.save(principal);

        return request1;
    }

    public Collection<Request> findAll(){
        return requestRepository.findAll();
    }

    public void delete(Request request){
        User principal;

        principal = userService.findByPrincipal();
       // Assert(principal.getUserAccount().getAuthorities().contains(Authority.ADMIN));

        requestRepository.delete(request);
    }

}
