package autodev.ddd.platform.test;

import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestDataRunner implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(TestDataRunner.class);


    private final TestDataMapper mapper;

    @Inject
    public TestDataRunner(TestDataMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Insert test data");

        String userId = "1";
        mapper.insertUser(userId, "John Smith", "john.smith@email.com");
    }
}
