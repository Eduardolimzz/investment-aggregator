package investment.aggregator.investmentaggregator.service;

import investment.aggregator.investmentaggregator.dto.CreateUserDto;
import investment.aggregator.investmentaggregator.entity.User;
import investment.aggregator.investmentaggregator.repository.UserRepository;
import org.springframework.stereotype.Service;

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
}