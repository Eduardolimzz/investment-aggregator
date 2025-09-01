package investment.aggregator.investmentaggregator.service;

import investment.aggregator.investmentaggregator.dto.CreateUserDto;
import investment.aggregator.investmentaggregator.entity.User;
import investment.aggregator.investmentaggregator.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    //Arrange: ele primeiro precisa organizar tudo que precisa para o teste
    //Act: Chamar o texto que a gente quer no nosso teste Unitario
    //Assert: fazer todas as execuções
    @Mock
    private UserRepository userRepository; //uma das dependecias da service, ele cria um mock

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Nested
    class createUser{
        @Test
        @DisplayName("Should create a user with success")
        void shouldCreateAUserWithSuccess() {
            //Arrange:
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@gmail.com",
                    "password",
                    Instant.now(),
                    null
            );
            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());
            var input = new CreateUserDto(
                    "username",
                        "email@gmail.com",
                      "123"
            );

            //Act:
            var output = userService.createUser(input);

            //Assert:
            assertNotNull(output);

            var userCaptured = userArgumentCaptor.getValue();

            assertEquals(input.getUsername(), userCaptured.getUsername());
            assertEquals(input.getEmail(), userCaptured.getEmail());
            assertEquals(input.getPassword(), userCaptured.getPassword());
        }

        @Test
        @DisplayName("Should throw exception when error occurs")
        void shouldThrowExceptionWhenErroOccurs(){

            doThrow(new RuntimeException()).when(userRepository).save(any());
            var input = new CreateUserDto(
                    "username",
                    "email@gmail.com",
                    "123"
            );

            //Act e Assert:
            assertThrows(RuntimeException.class, () -> userService.createUser(input));

        }
    }
}
