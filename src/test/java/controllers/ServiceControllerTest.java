package controllers;

import controllers.Manager.ServiseManagerController;
import domain.Manager;
import domain.Servise;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;
import services.CategoryService;
import services.ManagerService;
import services.ServiseService;
import utilities.AbstractTest;

import javax.transaction.Transactional;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml",
        "classpath:spring/config/packages.xml","classpath:spring/junit.xml"})
@WebAppConfiguration
@Transactional
public class ServiceControllerTest extends AbstractTest{

    @Mock
    private ServiseService serviseService;

    @Autowired
    private ServiseManagerController controller;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ManagerService managerService;

    private MockMvc mvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void create() throws Exception {
        authenticate("manager1");
        mvc.perform(get("/servise/manager/create.do")).andExpect(status().isOk()).
                andExpect(view().name("servise/edit")).
                andExpect(model().attribute("actionUri", "servise/manager/edit.do"));
        unauthenticate();
     }

    @Test
    public void list() throws Exception {
        authenticate("manager1");
        Manager principal = managerService.findByPrincipal();
        mvc.perform(get("/servise/manager/list")).andExpect(status().isOk()).
                andExpect(view().name("servise/list")).
                andExpect(model().attribute("servises",principal.getServises()));
        unauthenticate();
    }


    // Editing a Service logged in as Manager
    @Test
    public void editPositive() throws Exception {
        authenticate("manager1");
        int serviceId = super.getEntityId("servise1");
        mvc.perform(get("/servise/manager/edit").param("serviseId",String.valueOf(serviceId))).
                andExpect(status().isOk()).andExpect(forwardedUrl("servise/edit")).
                andExpect(model().attribute("actionUri","servise/manager/edit.do"))
        .andExpect(model().attribute("cancelUri","servise/manager/list.do"));
        unauthenticate();
    }

    // Editing a Service of another manager
    @Test
    public void editNegative() throws Exception {
        authenticate("manager2");
        int serviceId = super.getEntityId("servise1");
        mvc.perform(get("/servise/manager/edit").param("serviseId",String.valueOf(serviceId)))
        .andExpect(forwardedUrl("misc/panic"));
        unauthenticate();
    }


    // Accessing a url that does not exist authentication as user
    @Test
    public void deleteNegative() throws Exception {
        authenticate("user1");
        int serviceId = super.getEntityId("servise2");
        mvc.perform(get("/servise/manager/editDelete").param("serviseId",String.valueOf(serviceId)))
                .andExpect(status().isNotFound());
        unauthenticate();
    }
}
