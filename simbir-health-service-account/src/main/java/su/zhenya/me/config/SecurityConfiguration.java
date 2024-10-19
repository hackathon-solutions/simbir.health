package su.zhenya.me.config;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import su.zhenya.me.common.security.core.provider.account.AccountProvider;
import su.zhenya.me.domain.entity.mapper.AccountEntityMapper;
import su.zhenya.me.domain.repository.AccountRepository;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Bean
    public AccountProvider accountProvider(AccountEntityMapper mapper, AccountRepository repository) {
        return accountId -> Optional.ofNullable(mapper.entityToDomain(repository.findById(accountId).get()));
    }
}
