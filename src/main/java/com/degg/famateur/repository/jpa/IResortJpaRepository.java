package com.degg.famateur.repository.jpa;

import com.degg.famateur.model.Resort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IResortJpaRepository extends JpaRepository<Resort, Long> {
}
