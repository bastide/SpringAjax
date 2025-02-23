package comptoirs.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import comptoirs.dao.CategorieRepository;

@Controller
@RequestMapping(path = "/comptoirs/stats")
public class StatsMVCController {

	private final CategorieRepository dao;

	public StatsMVCController(CategorieRepository dao) {
		this.dao = dao;
	}

	/**
     * Handles a GET request to display product statistics grouped by category.
     * Adds all available categories to the model for rendering in the view.
     *
     * @param model The Model object used to pass attributes to the view.
     * @return The name of the view template to render for product statistics by category.
     */
    @GetMapping(path = "produitsPourCategorie")

	public	String montreStatsProduits(Model model) {
		model.addAttribute("categories", dao.findAll());
		return "statsProduitsPourCategorie";
	}
}
