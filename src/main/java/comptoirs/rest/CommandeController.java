package comptoirs.rest;

import comptoirs.dto.CommandeDTO;
import comptoirs.dto.EnTeteCommandeDTO;
import comptoirs.dto.LigneDTO;
import comptoirs.entity.Commande;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import comptoirs.service.CommandeService;

import java.util.List;
import java.util.ArrayList;

@RestController // Cette classe est un contrôleur REST
@RequestMapping(path = "/services/commandes") // chemin d'accès
@Slf4j
public class CommandeController {
	private final CommandeService commandeService;
	private final ModelMapper mapper;
	// @Autowired
	public CommandeController(CommandeService commandeService, ModelMapper mapper) {
		this.commandeService = commandeService;
		this.mapper = mapper;
	}

	@PostMapping("ajouterPour/{clientCode}")
	public CommandeDTO ajouter(@PathVariable @NonNull String clientCode) {
        log.info("ajouterPour {}", clientCode);
		Commande commande = commandeService.creerCommande(clientCode);
		return mapper.map(commande, CommandeDTO.class);
	}

	@PostMapping("expedier/{commandeNum}")
	public EnTeteCommandeDTO expedier(@PathVariable Integer commandeNum) {
        log.info("expedier {}", commandeNum);
		return mapper.map(commandeService.enregistreExpedition(commandeNum), EnTeteCommandeDTO.class);
	}

	@PostMapping("ajouterLigne")
	public LigneDTO ajouterLigne(@RequestParam int commandeNum, @RequestParam int produitRef, @RequestParam int quantite) {
        log.info("ajouterLigne {} {} {}", commandeNum, produitRef, quantite);
		var ligne = commandeService.ajouterLigne(commandeNum, produitRef, quantite);
		return mapper.map(ligne, LigneDTO.class);
	}

    @GetMapping("{commandeNum}")
    public CommandeDTO getCommande(@PathVariable Integer commandeNum) {
        log.info("getCommande {}", commandeNum);
        return mapper.map(commandeService.getCommande(commandeNum), CommandeDTO.class);
    }

    @GetMapping("enCoursPour/{clientCode}")
    public List<EnTeteCommandeDTO> getCommandeEnCoursPour(@PathVariable @NonNull String clientCode) {
        log.info("getCommandeEnCoursPour {}", clientCode);
        List<Commande> commandes = commandeService.getCommandeEnCoursPour(clientCode);
        List<EnTeteCommandeDTO> commandesDTO = new ArrayList<>();
        for (Commande commande : commandes) {
            commandesDTO.add(mapper.map(commande, EnTeteCommandeDTO.class));
        }
        return commandesDTO;
    }
}
