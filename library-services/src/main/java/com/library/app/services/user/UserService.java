package com.library.app.services.user;

import com.library.app.repositories.persistence.User;
import com.library.app.services.admin.model.GrantAdmin;
import com.library.app.services.registration.model.RegistrationRequest;

import java.util.List;

public interface UserService {
    void save(User user);

    void saveAll(List<User> users);

    void createAdmin(RegistrationRequest request);

    void grantAdminAccess(GrantAdmin grantAdmin);
}
