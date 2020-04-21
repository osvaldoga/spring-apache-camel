package cl.osvaldo.camel.democamelspring.route;

import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
@ContextConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SimpleCamelRouteTest {

    @Autowired
    private ProducerTemplate producerTemplate;

    @Value("${fromRoute}")
    private String fromRoute;

    @BeforeClass
    public static void cleanUp() throws IOException {
        FileUtils.cleanDirectory(new File("data/input"));
        FileUtils.deleteDirectory(new File("data/output"));
    }


    @Test
    public void testMoveFile() throws InterruptedException {
        String message = "type,sku#,itemDescription,price\n" +
                "ADD,100,Samsung TV, 500\n" +
                "ADD,101,LG TV, 500";

        String fileName = "filTest.txt";
        producerTemplate.sendBodyAndHeader(fromRoute, message, Exchange.FILE_NAME, fileName);

        TimeUnit.SECONDS.sleep(3);

        File outFile = new File("data/output/" + fileName);

        assertTrue(outFile.exists());
    }
}