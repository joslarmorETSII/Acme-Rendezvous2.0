
package controllers;

import java.util.Collection;

import domain.Rendezvous;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import domain.Category;
import services.RendezvousService;

@Controller
@RequestMapping("/category")
public class CategoryController extends AbstractController{

	// Services ------------------------------------------------------------

	@Autowired
	private CategoryService	categoryService;


	// Constructors --------------------------------------------------------

	// Listing  --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final Integer categoryId) {

		ModelAndView result = null;
		Collection<Category> categories = null;

		if (categoryId == null)
			categories = this.categoryService.findAll();
		else
			categories = this.categoryService.findCategoryChildrenId(categoryId);

		result = new ModelAndView("category/list");
		result.addObject("categories", categories);

		return result;
	}


	@RequestMapping(value = "/listRendezvous", method = RequestMethod.GET)
	public ModelAndView listRendezvous(@RequestParam Integer categoryId) {

		ModelAndView result ;
		Collection<Rendezvous> rendezvouses ;

		rendezvouses = this.categoryService.findAllRendezvousByCategoryId2(categoryId);

		result = new ModelAndView("category/listRendezvous");
		result.addObject("rendezvouses", rendezvouses);

		return result;
	}


	// Creation  --------------------------------------------------------------

	// Edition    --------------------------------------------------------------

	// Ancillary methods ----------------------------------------------------

}
