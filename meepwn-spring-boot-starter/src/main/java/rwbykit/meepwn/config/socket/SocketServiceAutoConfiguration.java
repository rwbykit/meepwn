package rwbykit.meepwn.config.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import rwbykit.meepwn.core.service.socket.AsynchronousScheduler;
import rwbykit.meepwn.core.threadpool.JdkThreadPool;
import rwbykit.meepwn.core.threadpool.ThreadPool;


@Configuration
@ImportResource(locations = "classpath*:meepwn/spring*.xml")
public class SocketServiceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ThreadPool threadPool() {
        return new JdkThreadPool();
    }

    @Autowired
    public void setThreadPool(@Autowired ThreadPool threadPool) {
        AsynchronousScheduler.setThreadPool(threadPool);
    }


}
