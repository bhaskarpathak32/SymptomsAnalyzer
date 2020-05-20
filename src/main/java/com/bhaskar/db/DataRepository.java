package com.bhaskar.db;

import org.springframework.data.repository.CrudRepository;

import com.bhaskar.beans.FormData;

public interface DataRepository extends CrudRepository<FormData, Long> {

}
