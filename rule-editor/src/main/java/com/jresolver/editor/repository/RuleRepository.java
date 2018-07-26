package com.jresolver.editor.repository;

import com.jresolver.editor.bean.Rule;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RuleRepository {

  Rule save(Rule entity);

  Iterable<Rule> saveAll(Iterable<Rule> entities);

  Optional<Rule> findById(Integer id);

  boolean existsById(Integer id);

  Iterable<Rule> findAll();

  Iterable<Rule> findAllById(Iterable<Integer> ids);

  long count();

  void deleteById(Integer id);

  void delete(Rule entity);

  void deleteAll(Iterable<Rule> entities);

  void deleteAll();
}
