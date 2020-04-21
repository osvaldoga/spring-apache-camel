package cl.osvaldo.camel.democamelspring.route;

import cl.osvaldo.camel.democamelspring.domain.Item;
import cl.osvaldo.camel.democamelspring.ex.DataException;
import cl.osvaldo.camel.democamelspring.process.BuildSQLProcessor;
import cl.osvaldo.camel.democamelspring.process.SucessProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.spi.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
@Slf4j
public class SimpleCamelRoute extends RouteBuilder {

    @Autowired
    private BuildSQLProcessor buildSQLProcessor;

    @Autowired
    private SucessProcessor sucessProcessor;

    @Override
    public void configure() throws Exception {
        DataFormat bindy = new BindyCsvDataFormat(Item.class);

        /*
        errorHandler(deadLetterChannel("log:errorInRoute?level=ERROR&showProperties=true")
                .maximumRedeliveries(3)
                .redeliveryDelay(3000)
                .backOffMultiplier(2)
        .retryAttemptedLogLevel(LoggingLevel.ERROR));
         */

        onException(SQLException.class)
                .log(LoggingLevel.ERROR, "SQLException in the route ${body}")
                .maximumRedeliveries(3)
                .redeliveryDelay(3000)
                .backOffMultiplier(2)
                .retryAttemptedLogLevel(LoggingLevel.ERROR);

        onException(DataException.class)
                .log(LoggingLevel.ERROR, "Exception in the route ${body}");

        from("{{startRoute}}")
                .log("Timer invoked and the body ${body}")
                .choice()
                    .when(header("env").isNotEqualTo("mock"))
                        .pollEnrich("{{fromRoute}}")
                    .otherwise()
                        .log("Mock end flow and the body is ${body}")
                    .end()
                .to("{{toRoute1}}")
                .unmarshal(bindy)
                .log("The unmarshaled object is ${body}")
                .split(body())
                    .log("Record is ${body}")
                    .process(buildSQLProcessor)
                    .to("jdbc:dataSource")
                .end()
        .process(sucessProcessor)
        .to("file:data/output?fileName=Success.txt");


    }
}
