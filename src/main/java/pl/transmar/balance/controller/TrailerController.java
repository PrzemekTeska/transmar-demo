package pl.transmar.balance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pl.transmar.balance.model.Trailer;
import pl.transmar.balance.service.TrailerService;

@Controller
public class TrailerController {

    private TrailerService trailerService;

    @Autowired
    public TrailerController(TrailerService trailerService) {
        this.trailerService = trailerService;
    }

    @RequestMapping("/trailers")
    public String trailers(Model model) {
        model.addAttribute("trailerList", trailerService.getAllTrailers());
        return "trailers";
    }

    @RequestMapping("/add-trailer")
    public ModelAndView addTrailer() {
        return new ModelAndView("add-trailer", "trailer", new Trailer());
    }

    @RequestMapping("/save-trailer")
    public ModelAndView saveTrailer(Trailer trailer) {
        trailerService.addTrailer(trailer);
        return new ModelAndView("redirect:/trailers");
    }

    @RequestMapping(value = "/trailers/delete/{id}", method = RequestMethod.GET)
    public String deleteTrailer(@PathVariable Long id) {
        trailerService.deleteTrailer(id);
        return "redirect:/trailers";
    }

    @RequestMapping(value = "trailers/edit/{id}", method = RequestMethod.POST)
    public String updateTrailer(@PathVariable Long id, Trailer trailer) {
        trailerService.updateTrailer(id, trailer);
        return "redirect:/trailers";
    }

    @RequestMapping("/edit-trailer/{id}")
    public String editTrailer(@PathVariable Long id, Model model) {
        model.addAttribute("trailer", trailerService.getTrailerById(id));
        return "edit-trailer";
    }
}
