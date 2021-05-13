package com.example.springwebapp.repos;

import com.example.springwebapp.model.Auto;
import org.springframework.data.repository.CrudRepository;

public interface AutoRepos extends CrudRepository<Auto,Long> {
}
