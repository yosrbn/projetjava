package com.fst.gestiondevente.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.fst.gestiondevente.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.fst.gestiondevente.entities.Category;
import com.fst.gestiondevente.entities.Fournisseur;
import com.fst.gestiondevente.entities.Produit;
import com.fst.gestiondevente.repositories.CategoryRepository;
import com.fst.gestiondevente.repositories.FournisseurRepository;
import com.fst.gestiondevente.repositories.ProduitRepository;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("produit")
public class ProduitController {
    @Autowired
    ProduitRepository produitRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    FournisseurRepository fournisseurRepository;

    @GetMapping("/list")
    public String afficher(Model model) {
        List<Produit> produits = produitRepository.findAll();

        model.addAttribute("produits", produits);
        return "product/affichage";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {

        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("fournisseurs", fournisseurRepository.findAll());
        model.addAttribute("produit", new Produit());
        return "product/ajout";
    }

    @PostMapping("/add")
    public String ajout(@RequestParam("nom") String nom, @RequestParam("description") String description,
                        @RequestParam("prix") double prix, @RequestParam("image") MultipartFile image, @RequestParam("categoryId") Long categoryId, @RequestParam("fournisseurId") Long fournisseurId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        Fournisseur fournisseur = fournisseurRepository.findById(fournisseurId).orElse(null);

        if (category != null && fournisseur != null) {
            Produit produit = new Produit(nom, description, prix);
            produit.setCategory(category);
            produit.setFournisseur(fournisseur);


            String fileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
            produit.setImage(fileName);

            try {
                FileUploadUtil.saveFile("src\\main\\resources\\static\\img", fileName, image);
            } catch (IOException e) {

                e.printStackTrace();
            }
            produitRepository.save(produit);
        }
        return "redirect:list";
    }

    @GetMapping("delete/{id}")
    public String supprimer(@PathVariable("id") long id) {
        produitRepository.deleteById(id);
        return "redirect:../list";
    }

    @GetMapping("update/{id}")
    public String maj(Model model, @PathVariable("id") long id) {
        Produit produit = produitRepository.findById(id).orElseThrow();
        model.addAttribute("p", produit);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("fournisseurs", fournisseurRepository.findAll());
        return "product/modification";
    }

    @PostMapping("update")
    public String modifier(@ModelAttribute Produit produit, @RequestParam("categoryId") Long categoryId, @RequestParam("fournisseurId") Long fournisseurId, @RequestParam("productImage") MultipartFile productImage) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        Fournisseur fournisseur = fournisseurRepository.findById(fournisseurId).orElse(null);

        if ((category != null) && (fournisseur != null)) {
            produit.setCategory(category);
            produit.setFournisseur(fournisseur);
        }
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(productImage.getOriginalFilename()));
        if (!fileName.isBlank()) {
            produit.setImage(fileName);

            try {
                FileUploadUtil.saveFile("src\\main\\resources\\static\\img", fileName, productImage);
            } catch (IOException e) {

                e.printStackTrace();
            }
        } else {
            produit.setImage(produitRepository.findById(produit.getId()).orElseThrow().getImage());
        }
        produitRepository.save(produit);
        return "redirect:list";

    }

    @GetMapping("/images/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) throws IOException {
        byte[] imageBytes = loadImage(imageName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);

    }

    private byte[] loadImage(String imageName) throws IOException {
        Path imagePath = Paths.get("src\\main\\resources\\static\\img", imageName);
        byte[] result;
        try {
            result = Files.readAllBytes(imagePath);
        } catch (Exception e) {
            Path noImage = Paths.get("src\\main\\resources\\static\\img", "no-image.png");
            result = Files.readAllBytes(noImage);
        }
        return result;
    }
}

