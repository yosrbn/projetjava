package com.fst.gestiondevente.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fst.gestiondevente.entities.Fournisseur;

@Repository
public interface FournisseurRepository extends JpaRepository <Fournisseur,Long> {

}
