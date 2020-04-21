package cl.osvaldo.camel.democamelspring.route;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@ContextConfiguration
@ActiveProfiles("mock")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SimpleCamelRouteMockTest {

    @Value("${startRoute}")
    private String startRoute;

    @Value("${spring.profiles.active}}")
    private String activeProfile;

    @Value("${toRoute1}")
    private String toRoute1;

    @Autowired
    private ProducerTemplate producerTemplate;

    //@Test
    public void testMoveFileMock() throws InterruptedException {
        /*
        String message = "type,sku#,itemDescription,price\n" +
                "ADD,100,Samsung TV, 500\n" +
                "ADD,101,LG TV, 500";

        String fileName = "filTest.txt";

        MockEndpoint mockEndpoint = getMockEndpoint(toRoute1);
        mockEndpoint.expectedMessageCount(1);
        mockEndpoint.expectedBodiesReceived(message);

        producerTemplate.sendBodyAndHeader(startRoute, message, "env", activeProfile);

        assertMockEndpointsSatisfied();
         */
    }
}
