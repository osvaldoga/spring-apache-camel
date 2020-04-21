package cl.osvaldo.camel.democamelspring.process;

import cl.osvaldo.camel.democamelspring.domain.Item;
import cl.osvaldo.camel.democamelspring.ex.DataException;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Slf4j
@Component
public class BuildSQLProcessor implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Item item = (Item) exchange.getIn().getBody();

        log.info("Item in processor is: {}", item);

        StringBuilder query = new StringBuilder();

        if (ObjectUtils.isEmpty(item.getSku())) {
            throw new DataException("SKU is null for " + item.getItemDescription());
        }

        if (item.getTransactionType().equals("ADD")) {
            query.append("insert into items_9(sku, description, price) values(");
            query.append("'" + item.getSku() + "'");
            query.append(", '" + item.getItemDescription() + "'");
            query.append(", " + item.getPrice());
            query.append(");");
        } else if (item.getTransactionType().equals("UPDATE")) {
            query.append("update items set ");

            query.append("price = " + item.getPrice());
            query.append(" where sku = '" + item.getSku() + "'");

        } else if (item.getTransactionType().equals("DELETE")) {
            query.append("delete from items where sku = '" + item.getSku() + "'");
        }

        log.info("Final query is: " + query);

        exchange.getIn().setBody(query.toString());
    }
}
