package za.co.moitrack.config;


import com.mongodb.MongoClient;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.mongo.MongoLockProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShedlockConfig {
    @Bean
    public LockProvider lockProvider(MongoClient mongo) {
        return new MongoLockProvider(mongo, "moiponeGateKeeper");
    }

}
