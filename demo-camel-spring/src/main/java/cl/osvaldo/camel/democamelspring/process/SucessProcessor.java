package cl.osvaldo.camel.democamelspring.process;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Component
public class SucessProcessor implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        exchange.getIn().setBody("Data Updated Successfully");
    }
}
