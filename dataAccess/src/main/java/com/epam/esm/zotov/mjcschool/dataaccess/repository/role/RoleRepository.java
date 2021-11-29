package com.epam.esm.zotov.mjcschool.dataaccess.repository.role;

import com.epam.esm.zotov.mjcschool.dataaccess.model.Role;
import com.epam.esm.zotov.mjcschool.dataaccess.repository.CustomRepository;

public interface RoleRepository extends CustomRepository<Role, Short> {
    Role findByName(String name);
}