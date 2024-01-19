package com.fst.gestiondevente.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fst.gestiondevente.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository <Category,Long> {

}
