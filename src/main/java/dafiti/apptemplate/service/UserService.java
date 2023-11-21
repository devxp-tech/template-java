package devxp-tech.apptemplate.service;

import devxp-tech.apptemplate.model.UserREQ;
import devxp-tech.apptemplate.model.UserRES;
import devxp-tech.apptemplate.model.entity.UserEntity;
import devxp-tech.apptemplate.repository.UserRepository;
import devxp-tech.bootstrap.model.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserRES> listAll() {
        return userRepository.findAll()
                .stream()
                .map(UserRES::new)
                .collect(Collectors.toList());
    }

    public UserRES findById(UUID userId) {
        return userRepository.findById(userId)
                .map(UserRES::new)
                .orElseThrow(NotFoundException::new);
    }

    public UserRES save(UserREQ userREQ) {
        UserEntity entity = new UserEntity(userREQ);
        UserEntity saved = userRepository.save(entity);
        return new UserRES(saved);
    }
}
