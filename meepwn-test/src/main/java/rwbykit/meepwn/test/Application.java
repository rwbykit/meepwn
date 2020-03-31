package rwbykit.meepwn.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import rwbykit.meepwn.test.service.Client;


@SpringBootApplication(scanBasePackages = "rwbykit.meepwn")
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        Client client = context.getBean(Client.class);
        client.send();
    }


}
