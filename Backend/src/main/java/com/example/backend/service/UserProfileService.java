package com.example.backend.service;
import com.example.backend.dto.UserProfileResponse;
import com.example.backend.entity.UserProfile;
import com.example.backend.entity.Users;
import com.example.backend.repository.UserProfileRepository;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserProfileResponse getProfile(String username) {
        Optional<UserProfile> userProfileOpt = userProfileRepository.findById(username);
        Optional<Users> userEmail = userRepository.findByUsername(username);
        if (userProfileOpt.isPresent()) {
            UserProfile userProfile = userProfileOpt.get();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return new UserProfileResponse(
                    userProfile.getUsername(),
                    userProfile.getFullName(),
                    userProfile.getGender(),
                    userProfile.getPhone(),
                    userProfile.getAddress(),
                    userProfile.getBirthday() != null ? dateFormat.format(userProfile.getBirthday()) : null,
                    userProfile.getAvatarUrl(),
                    userEmail.isPresent() ? userEmail.get().getEmail() : null);
        } else {
            return new UserProfileResponse(null, null, null, null, null, null, null,null);
        }
    }

}
