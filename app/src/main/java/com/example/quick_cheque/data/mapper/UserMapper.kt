package com.example.quick_cheque.data.mapper

import com.example.quick_cheque.R
import com.example.quick_cheque.data.local.entity.UserEntity
import com.example.quick_cheque.data.remote.dto.UserDto
import com.example.quick_cheque.domain.model.User

fun UserDto.toUser(): User {
    return User(
        id = id,
        name = name,
        icon = R.drawable.person_filled,
        email = email
    )
}

fun UserEntity.toUser(): User {
    return User(
        name = name,
        email = email,
        icon = R.drawable.person_filled
    )
}

fun UserDto.toUserEntity(): UserEntity {
    return UserEntity(
        id = id,
        email = email,
        name = name
    )
}

fun User.toUserDto(): UserDto {
    return UserDto(
        id = id,
        email = email,
        name = name,
    )
}
