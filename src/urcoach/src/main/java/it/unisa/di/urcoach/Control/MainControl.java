package it.unisa.di.urcoach.Control;

import it.unisa.di.urcoach.Model.Entity.Carrello;
import it.unisa.di.urcoach.Model.Entity.Pacchetto;
import it.unisa.di.urcoach.Model.Entity.PersonalTrainer;
import it.unisa.di.urcoach.Model.Service.AtletaService;
import it.unisa.di.urcoach.Model.Service.PacchettoService;
import it.unisa.di.urcoach.Model.Service.PersonalTrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainControl {

    private final AtletaService atletaService;
    private final PersonalTrainerService personalTrainerService;
    private final PacchettoService pacchettoService;
    private Carrello carrello;

    public MainControl(AtletaService atletaService, PersonalTrainerService personalTrainerService, PacchettoService pacchettoService) {
        this.atletaService = atletaService;
        this.personalTrainerService = personalTrainerService;
        this.pacchettoService = pacchettoService;
    }

    @GetMapping("/homepage")
    public String getHome(Model model) {
        List<Pacchetto> ultimiPacchetti = pacchettoService.findLast();
        model.addAttribute("ultimiPacchetti", ultimiPacchetti);
        return "View/index";
    }
}
