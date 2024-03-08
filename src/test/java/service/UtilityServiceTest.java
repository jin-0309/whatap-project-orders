package service;

import static org.junit.jupiter.api.Assertions.*;

import exception.SleepQueryException;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
class UtilityServiceTest {

    @Inject
    UtilityService utilityService;
    @Test
    void sleep() {
        Assertions.assertThrows(SleepQueryException.class, () -> utilityService.sleep(1L));
    }
}