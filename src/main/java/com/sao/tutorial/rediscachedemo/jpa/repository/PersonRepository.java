package com.sao.tutorial.rediscachedemo.jpa.repository;

import com.sao.tutorial.rediscachedemo.jpa.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
}
