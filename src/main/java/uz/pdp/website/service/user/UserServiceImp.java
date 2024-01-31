package uz.pdp.website.service.user;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.website.dto.request.AuthDto;
import uz.pdp.website.dto.request.UserRequestDto;
import uz.pdp.website.entity.UserEntity;
import uz.pdp.website.entity.enums.Role;
import uz.pdp.website.exception.DataNotFoundException;
import uz.pdp.website.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }



    @Override
    public UserEntity create(UserRequestDto userRequestDto) {
        UserEntity userEntity = modelMapper.map(userRequestDto, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setUserRoles(List.of(Role.USER));
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity getbyId(UUID id) {
            if(userRepository.findById(id).isPresent()){
                return userRepository.findById(id).get();
            }
            else {
                throw new DataNotFoundException("user not found");
            }
    }

    @Override
    public void deletebyId(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserEntity login(AuthDto auth) {
        UserEntity userEntity = userRepository.findByUsername(auth.getUsername())
                .orElseThrow(() -> new DataNotFoundException("user not found"));
        if (passwordEncoder.matches(auth.getPassword(), userEntity.getPassword())) {
            return userEntity;
        }
        throw new DataNotFoundException("user or password incorrect");
    }



    @Override
    public void update(UserRequestDto dto) {
        UserEntity map = modelMapper.map(dto, UserEntity.class);
        userRepository.save(map);
    }

    public void update(String username, String password, UUID id){
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("user not found"));
        try {
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
        }
        catch (Exception e){
        throw new DataNotFoundException("user does not exist");
        }
    }

    @Override
    public void updateUserInfo(String address, String direction,String password, UUID id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("user not found"));
        try {
            user.setAddress(address);
            user.setDirection(direction);
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
        }
        catch (Exception e){
            throw new DataNotFoundException("user doest not exist");
        }

    }

    @Override
    public UserEntity myInfo(UUID id) {
        for (UserEntity user: userRepository.findAll()){
        if(user!=null){
            if(user.getId().equals(id)){
                return UserEntity.builder().build();
            }
        }
        }
        throw new DataNotFoundException("user not found");
    }


}
