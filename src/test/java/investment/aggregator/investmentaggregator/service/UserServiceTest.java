package investment.aggregator.investmentaggregator.service;

import investment.aggregator.investmentaggregator.dto.CreateUserDto;
import investment.aggregator.investmentaggregator.dto.UpdateUserDto;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


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

    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Nested
    class createUser {
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
        void shouldThrowExceptionWhenErroOccurs() {

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

    @Nested
    class getUserById {
        @Test
        @DisplayName("Should get user by id with success when optional is present")
        void shouldGetUserByIdWithSuccessWhenOptionalIsPresent() {

            //Arrange
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@gmail.com",
                    "password",
                    Instant.now(),
                    null
            );
            doReturn(Optional.of(user)).when(userRepository).findById(uuidArgumentCaptor.capture());

            var output = userService.getUserById(user.getUserid().toString());

            assertTrue(output.isPresent());
            assertEquals(user.getUserid(), uuidArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should get user by id with success when optional is empty")
        void shouldGetUserByIdWithSuccessWhenOptionalIsEmpty() {

            //Arrange
            var userId = UUID.randomUUID();
            doReturn(Optional.empty()).when(userRepository).findById(uuidArgumentCaptor.capture());

            var output = userService.getUserById(userId.toString());

            assertTrue(output.isEmpty());
            assertEquals(userId, uuidArgumentCaptor.getValue());
        }
    }

    @Nested
    class listUsers {

        @Test
        @DisplayName("Should return all user with success")
        void shouldReturnAllUserWithSuccess() {

            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@gmail.com",
                    "password",
                    Instant.now(),
                    null
            );
            var userList = List.of(user);
            doReturn(userList).when(userRepository).findAll();

            var output = userService.listUsers();

            assertNotNull(output);
            assertEquals(userList.size(), output.size());
        }
    }

    @Nested
    class deleteById {
        @Test
        @DisplayName("Should delete user with success when user exists")
        void shouldDeleteUserWithSuccessWhenUserExists() {
            //Arrange
            doReturn(true).when(userRepository).existsById(uuidArgumentCaptor.capture());
            doNothing().when(userRepository).deleteById(uuidArgumentCaptor.capture());

            var userId = UUID.randomUUID();

            userService.deleteById(userId.toString());

            var idList = uuidArgumentCaptor.getAllValues();
            assertEquals(userId, idList.get(0));
            assertEquals(userId, idList.get(1));

            verify(userRepository, times(1)).existsById(idList.get(0));
            verify(userRepository, times(1)).deleteById(idList.get(1));
        }

        @Test
        @DisplayName("Should delete user when user not exists")
        void shouldDeleteUserWhenUserNotExists() {
            //Arrange
            doReturn(false).when(userRepository).existsById(uuidArgumentCaptor.capture());

            var userId = UUID.randomUUID();

            userService.deleteById(userId.toString());

            assertEquals(userId, uuidArgumentCaptor.getValue());

            verify(userRepository, times(1)).existsById(uuidArgumentCaptor.getValue());

            verify(userRepository, times(0)).deleteById(any());
        }
    }

    @Nested
    class updateUserById {
        @Test
        @DisplayName("Should update user by id when user exists and username and password is filled")
        void shouldUpdateUserByIdWhenUserExistsAnUsernameAndPasswordIsFilled() {

            var updateUserDto = new UpdateUserDto(
                    "newusername",
                    "new Pasword"
            );
            //Arrange
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@gmail.com",
                    "password",
                    Instant.now(),
                    null
            );
            doReturn(Optional.of(user)).when(userRepository).findById(uuidArgumentCaptor.capture());
            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());

            userService.updateUserById(user.getUserid().toString(), updateUserDto);

            assertEquals(user.getUserid(), uuidArgumentCaptor.getValue());

            var userCaptured = userArgumentCaptor.getValue();

            assertEquals(updateUserDto.username(), userCaptured.getUsername());
            assertEquals(updateUserDto.password(), userCaptured.getPassword());

            verify(userRepository, times(1)).findById(uuidArgumentCaptor.getValue());
            verify(userRepository, times(1)).save(user);

        }

        @Test
        @DisplayName("Should not update user by id when user not exists and username and password is filled")
        void shouldNotUpdateUserByIdWhenUserNotExistsAnUsernameAndPasswordIsFilled() {

            var updateUserDto = new UpdateUserDto(
                    "newusername",
                    "new Pasword"
            );
            var userId = UUID.randomUUID();
            doReturn(Optional.empty()).when(userRepository).findById(uuidArgumentCaptor.capture());

            userService.updateUserById(userId.toString(), updateUserDto);

            assertEquals(userId, uuidArgumentCaptor.getValue());

            verify(userRepository, times(1)).findById(uuidArgumentCaptor.getValue());
            verify(userRepository, times(0)).save(any());

        }
    }
}
