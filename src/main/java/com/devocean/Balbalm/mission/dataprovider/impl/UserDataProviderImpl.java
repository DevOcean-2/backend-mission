package com.devocean.Balbalm.mission.dataprovider.impl;

import com.devocean.Balbalm.mission.dataprovider.UserDataProvider;
import com.devocean.Balbalm.mission.domain.UserInfo;
import com.devocean.Balbalm.mission.domain.entity.User;
import com.devocean.Balbalm.mission.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserDataProviderImpl implements UserDataProvider {
    private final UserRepository userRepository;

    @Override
    public List<UserInfo> getAllUserList() {
        return UserDataProviderImplMapper.MAPPER.toUserInfoList(userRepository.findAll());
    }

    @Mapper
    public interface UserDataProviderImplMapper {
        UserDataProviderImpl.UserDataProviderImplMapper MAPPER = Mappers.getMapper(UserDataProviderImpl.UserDataProviderImplMapper.class);
        List<UserInfo> toUserInfoList(List<User> userList);
    }
}
