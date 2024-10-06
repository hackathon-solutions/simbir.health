package su.zhenya.me;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import su.zhenya.me.account.model.AccountToken;

@RestController
@RequestMapping("/auth")
public class OAuth2Controller {

    @PostMapping("/issuer")
    public AccountToken issuer(HttpServletRequest request) {
        return new AccountToken();
    }
}
