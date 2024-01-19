package com.fst.gestiondevente.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.fst.gestiondevente.entities.Fournisseur;
import com.fst.gestiondevente.entities.Produit;
import com.fst.gestiondevente.repositories.FournisseurRepository;

@Controller
@RequestMapping("fournisseur")
public class FournisseurController 
{
	@Autowired
	FournisseurRepository fournisseurRepository;
	
	@GetMapping("/list")
	public String afficher(Model model)
	{
	
	model.addAttribute("fournisseurs",fournisseurRepository.findAll());
		return "/supplier/affichage";	
	}

	@GetMapping("/add")
	public String ajouter()
	{
		return "supplier/ajout";
	}

	@PostMapping("/add")
	public String ajout(@RequestParam("nom") String nom, @RequestParam("email") String email,
	    @RequestParam("tel") String tel)
	{
	    Fournisseur fournisseur = new Fournisseur();
	    fournisseur.setNom(nom);
        fournisseur.setEmail(email);
        fournisseur.setTel(tel);
        List<Produit> produits = new ArrayList<>();
        fournisseur.setProduits(produits);
	    Fournisseur success = fournisseurRepository.save(fournisseur);
	    return "redirect:list";
	}


	@GetMapping("delete/{id}")
	public String supprimer(@PathVariable("id") long id)
	{
	fournisseurRepository.deleteById(id);
	return "redirect:../list";
	}

	@GetMapping("update/{id}")
	public String maj(Model model,@PathVariable("id") long id)
	{
	Optional<Fournisseur> fournisseur=fournisseurRepository.findById(id);
	model.addAttribute("f",fournisseur);
	return "supplier/modification";
	}

	@PostMapping("update")
	public String modifier(Fournisseur fournisseur)
	{
	fournisseurRepository.save(fournisseur);
	return "redirect:list";
	}

}
