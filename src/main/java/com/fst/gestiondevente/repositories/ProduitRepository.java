package com.fst.gestiondevente.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fst.gestiondevente.entities.Produit;
@Repository
public interface ProduitRepository extends JpaRepository <Produit,Long> {

}