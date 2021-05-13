package com.example.springwebapp.repos;

import com.example.springwebapp.model.Checkout;
import org.springframework.data.repository.CrudRepository;

public interface CheckRepos extends CrudRepository<Checkout,Long> {

}
