package org.rbernalop.strategy.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rbernalop.strategy.entity.User;
import org.rbernalop.strategy.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserCreator {
    private final FileReader fileReader;
    private final UserRepository userRepository;

    public void saveUsersFromFile(MultipartFile multipartFile) {
        List<String> usersFromFile = fileReader.readFile(multipartFile);
        usersFromFile.forEach(this::processUserFromFile);
    }

    private void processUserFromFile(String userFromFile) {
        String[] userData = userFromFile.split(";");

        if (userData.length < 4) {
            log.warn("Ignoring invalid user from file {}", userFromFile);
            return;
        }

        try {
            int id = (int) Double.parseDouble(userData[0]);
            String name = userData[1];
            int age = (int) Double.parseDouble(userData[2]);
            String relationshipStatus = userData[3];

            User user = new User();
            user.setId(id);
            user.setName(name);
            user.setAge(age);
            user.setRelationshipStatus(relationshipStatus);

            userRepository.save(user);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format in user data.", e);
        }
    }
}
