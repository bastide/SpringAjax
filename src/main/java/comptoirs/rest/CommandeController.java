package comptoirs.rest;

import comptoirs.dto.CommandeDTO;
import comptoirs.dto.EnTeteCommandeDTO;
import comptoirs.dto.LigneDTO;
import comptoirs.entity.Commande;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
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
        log.info("Contrôleur : ajouter commande pour {}", clientCode);
		Commande commande = commandeService.creerCommande(clientCode);
		return mapper.map(commande, CommandeDTO.class);
	}

	@PostMapping("expedier/{commandeNum}")
	public EnTeteCommandeDTO expedier(@PathVariable Integer commandeNum) {
        log.info("Contrôleur : expédier la commande {}", commandeNum);
		return mapper.map(commandeService.enregistreExpedition(commandeNum), EnTeteCommandeDTO.class);
	}

	@PostMapping("ajouterLigne")
	public LigneDTO ajouterLigne(@RequestParam int commandeNum, @RequestParam int produitRef, @RequestParam int quantite) {
        log.info("Contrôleur : ajouterLigne {} {} {}", commandeNum, produitRef, quantite);
		var ligne = commandeService.ajouterLigne(commandeNum, produitRef, quantite);
		return mapper.map(ligne, LigneDTO.class);
	}

    @DeleteMapping("supprimerLigne/{idLigne}")
    public ResponseEntity<Void>  supprimerLigne(@PathVariable Integer idLigne) {
        log.info("Contrôleur : supprimerLigne {}", idLigne);
        commandeService.supprimerLigne(idLigne);
        // Renvoie : 204 - No Content
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{commandeNum}")
    public CommandeDTO getCommande(@PathVariable Integer commandeNum) {
        log.info("Contrôleur : getCommande {}", commandeNum);
        return mapper.map(commandeService.getCommande(commandeNum), CommandeDTO.class);
    }

    @GetMapping("enCoursPour/{clientCode}")
    public List<EnTeteCommandeDTO> getCommandeEnCoursPour(@PathVariable @NonNull String clientCode) {
        log.info("Contrôleur : getCommandeEnCoursPour {}", clientCode);
        List<Commande> commandes = commandeService.getCommandeEnCoursPour(clientCode);
        List<EnTeteCommandeDTO> commandesDTO = new ArrayList<>();
        for (Commande commande : commandes) {
            commandesDTO.add(mapper.map(commande, EnTeteCommandeDTO.class));
        }
        return commandesDTO;
    }
}
