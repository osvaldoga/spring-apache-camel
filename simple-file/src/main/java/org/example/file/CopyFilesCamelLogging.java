package org.example.file;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import java.util.concurrent.TimeUnit;

public class CopyFilesCamelLogging {
    public static void main(String[] args) {
        CamelContext context = new DefaultCamelContext();

        try {
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("file:data/input?noop=true")
                            .to("log:?level=DEBUG&showAll=true")
                            .log("Aca voy...")
                            .to("file:data/output");
                }
            });

            context.start();

            TimeUnit.SECONDS.sleep(5);

            context.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
