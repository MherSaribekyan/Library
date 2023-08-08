package com.library.app.services.user;

import com.library.app.repositories.persistence.Address;
import com.library.app.repositories.persistence.PaymentCard;
import com.library.app.repositories.persistence.User;
import com.library.app.repositories.types.Role;
import com.library.app.services.exception.ExceptionCode;
import com.library.app.services.exception.ServiceException;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class UserLoader {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final static String FILE_PATH = "/files/data-tVJ5E-PoXliPdkzyzbeE0.csv";

    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MMM d, yyyy");

    @PostConstruct
    @Transactional
    public void loadUsersFromCSV() {
        final User superAdmin = new User();
        superAdmin.setEmail("Saribekyan3233@gmail.com");
        superAdmin.setPassword(passwordEncoder.encode("fakePassword"));
        superAdmin.setName("Mher");
        superAdmin.setRole(Role.SUPER_ADMIN);
        superAdmin.setPhone("+37441353511");
        userService.save(superAdmin);

        List<User> users = new ArrayList<>();
        try(InputStream inputStream = getClass().getResourceAsStream(FILE_PATH)) {
            try (Reader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
                try (CSVReader csvReader = new CSVReader(reader)) {
                    List<String[]> strings = csvReader.readAll();
                    for (int i = 1; i < strings.size() - 1; i++) {
                        String[] userLine = strings.get(i);
                        final User user = new User();
                        user.setName(userLine[0]);
                        user.setPhone(userLine[1]);
                        user.setEmail(userLine[2]);
                        user.setRole(Role.USER);
                        user.setPassword(passwordEncoder.encode(userLine[6]));
                        final Address address = new Address();
                        address.setHouse(userLine[3]);
                        address.setPostalZip(userLine[4]);
                        address.setCountry(userLine[5]);
                        final PaymentCard card = new PaymentCard();
                        card.setPan(userLine[7]);
                        card.setExpirationDate(LocalDate.parse(userLine[8], FORMATTER));
                        card.setCvv(userLine[9]);

                        user.addAddress(address);
                        user.addPaymentCard(card);

                        users.add(user);
                    }

                    this.userService.saveAll(users);
                } catch (CsvException e) {
                    throw new ServiceException(ExceptionCode.SERVER_ERROR, e.getMessage());
                }
            } catch (IOException e) {
                throw new ServiceException(ExceptionCode.SERVER_ERROR, e.getMessage());
            }
        } catch (IOException e) {
            throw new ServiceException(ExceptionCode.SERVER_ERROR, e.getMessage());
        }
    }


    public UserLoader(final UserService userService,
                      final PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
}
