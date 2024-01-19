package com.fst.gestiondevente.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.fst.gestiondevente.entities.Category;
import com.fst.gestiondevente.entities.Produit;
import com.fst.gestiondevente.repositories.CategoryRepository;

@Controller
@RequestMapping("category")
public class CategoryController 
{
	@Autowired
	CategoryRepository categoryRepository;
	
	@GetMapping("/list")
	public String afficher(Model model)
	{
	model.addAttribute("categories",categoryRepository.findAll());
		return "categorie/affichage";	
	}

	@GetMapping("/add")
	public String ajouter()
	{
		return "categorie/ajout";
	}

	@PostMapping("/add")
	public String ajout(@RequestParam("nom") String nom)
	{
	    Category category = new Category();
	    category.setNom(nom);
	    category.setProduits(new ArrayList<>());
	    Category success = categoryRepository.save(category);
	    return "redirect:list";
	}


	@GetMapping("delete/{id}")
	public String supprimer(@PathVariable("id") long id)
	{
	categoryRepository.deleteById(id);
	return "redirect:../list";
	}

	@GetMapping("update/{id}")
	public String maj(Model model,@PathVariable("id") long id)
	{
	Optional<Category> category=categoryRepository.findById(id);
	model.addAttribute("c",category);
	return "categorie/modification";
	}

	@PostMapping("update")
	public String modifier(@ModelAttribute Category category)
	{
	categoryRepository.save(category);
	return "redirect:list";
	}

}

