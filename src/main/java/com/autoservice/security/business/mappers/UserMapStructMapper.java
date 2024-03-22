package com.autoservice.security.business.mappers;

import com.autoservice.security.business.repository.UserDAO;
import com.autoservice.security.models.Role;
import com.autoservice.security.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapStructMapper {

    @Mapping(target = "role", source = "role", qualifiedByName = "role")
    UserDAO userToUserDAO(User user);

    @Mapping(target = "role", source = "role", qualifiedByName = "role")
    User userDAOToUser(UserDAO userDAO);

    @Named("role")
    default String enumRoleToStringRole(Role role){
        return role.toString();
    }

    @Named("role")
    default Role StringRoleToEnumRole (String role){
        return Role.valueOf(role);
    }
}
