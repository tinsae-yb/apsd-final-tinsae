package org.example.crms.dto;

import lombok.Data;
import org.example.crms.entity.Role;


@Data
public class RoleDTO {

    private Role.RoleType roleType;

    public Role toRole() {
        Role role = new Role();
        role.setRoleType(roleType);
        return role;
    }

    public static RoleDTO fromRole(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRoleType(role.getRoleType());
        return roleDTO;
    }
}
