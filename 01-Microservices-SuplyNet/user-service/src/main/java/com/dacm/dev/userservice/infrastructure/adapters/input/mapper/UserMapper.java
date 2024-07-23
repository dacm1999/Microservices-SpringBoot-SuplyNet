package com.dacm.dev.userservice.infrastructure.adapters.input.mapper;

import com.dacm.dev.userservice.domain.dtos.response.UserDto;
import com.dacm.dev.userservice.infrastructure.adapters.output.persistence.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    /**
     * Converts a UserEntity to a UserDto.
     *
     * @param user The UserEntity to convert.
     * @return UserDto with selected fields from the UserEntity.
     */
    public static UserDto entityToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword());
    }

    /**
     * Converts a User domain model to a UserDto.
     *
     * @param user The User domain model to convert.
     * @return UserDto with selected fields from the User domain model.
     */
    public static UserDto domainToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword()
        );
    }

    /**
     * Converts a UserEntity to a User domain model.
     *
     * @param entity The UserEntity to convert.
     * @return User domain model with data from the UserEntity.
     * Note: This method includes the user's password. Care should be taken when handling and storing passwords.
     */
    public static User toDomain(User entity) {
        if (entity == null) {
            return null;
        }

        return User.builder()
                .username(entity.getUsername())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .id(entity.getId())
                .build();
    }

}