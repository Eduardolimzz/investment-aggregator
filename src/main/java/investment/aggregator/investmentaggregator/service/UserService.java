package investment.aggregator.investmentaggregator.service;

import investment.aggregator.investmentaggregator.dto.AccountResponseDto;
import investment.aggregator.investmentaggregator.dto.CreateAccountDto;
import investment.aggregator.investmentaggregator.dto.CreateUserDto;
import investment.aggregator.investmentaggregator.dto.UpdateUserDto;
import investment.aggregator.investmentaggregator.entity.Account;
import investment.aggregator.investmentaggregator.entity.BillingAddress;
import investment.aggregator.investmentaggregator.entity.User;
import investment.aggregator.investmentaggregator.repository.AccountRepository;
import investment.aggregator.investmentaggregator.repository.BillingAddressRepository;
import investment.aggregator.investmentaggregator.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private UserRepository userRepository;

    private AccountRepository accountRepository;

    private BillingAddressRepository billingAddressRepository;

    public UserService(UserRepository userRepository,
                       AccountRepository accountRepository,
                       BillingAddressRepository billingAddressRepository){
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.billingAddressRepository = billingAddressRepository;
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

    public void createAccount(String userId, CreateAccountDto createAccountDto) {

        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var account = new Account();
        account.setUser(user);
        account.setDescription(createAccountDto.description());
        account.setAccountStocks(new ArrayList<>());

        var accountCreated = accountRepository.save(account);

        // Use setters em vez do construtor para BillingAddress
        var billingAddress = new BillingAddress();
        billingAddress.setAccount(accountCreated);  // Isso define automaticamente o ID via @MapsId
        billingAddress.setStreet(createAccountDto.street());
        billingAddress.setNumber(createAccountDto.number());

        billingAddressRepository.save(billingAddress);
    }

    public List<AccountResponseDto> listAccounts(String userId) {
        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return user.getAccounts().stream()
                .map(account -> new AccountResponseDto(account.getAccountId(), account.getDescription()))
                .toList();
    }
}