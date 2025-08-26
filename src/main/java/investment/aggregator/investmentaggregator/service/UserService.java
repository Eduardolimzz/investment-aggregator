package investment.aggregator.investmentaggregator.service;

import investment.aggregator.investmentaggregator.dto.CreateUserDto;
import investment.aggregator.investmentaggregator.dto.UpdateUserDto;
import investment.aggregator.investmentaggregator.entity.User;
import investment.aggregator.investmentaggregator.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UUID createUser(CreateUserDto createUserDto){
        var entity = new User();
        entity.setUsername(createUserDto.getUsername());
        entity.setEmail(createUserDto.getEmail());
        entity.setPassword(createUserDto.getPassword());

        var userSaved = userRepository.save(entity);

        return userSaved.getUserid();
    }

    public Optional<User> getUserById(String userId){
        return userRepository.findById(UUID.fromString(userId));
    }

    public List<User> listUsers(){
        return userRepository.findAll();
    }

    public void updateUserById(String userId,
                               UpdateUserDto updateUserDto) {
        var id = UUID.fromString(userId);

        var userExists = userRepository.findById(id);

        if(userExists.isPresent()){
            var user = userExists.get();
            if(updateUserDto.username() != null){
                user.setUsername(updateUserDto.username());
            }
            if(updateUserDto.password() != null){
                user.setPassword(updateUserDto.password());
            }

            userRepository.save(user);
        }
    }

    public void deleteById(String userId){
        var id = UUID.fromString(userId);

        var userExists = userRepository.existsById(id);

        if(userExists){
            userRepository.deleteById(id);
        }
    }
}