package rs.lab.orders.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import rs.lab.common.config.AsyncExecutorConfiguration;

@Configuration
@Import(value = {AsyncExecutorConfiguration.class})
public class MainConfiguration {
}
