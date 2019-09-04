package com.project.repository.role;

import com.project.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findById(Long id);
    Role findByName(String name);

}
