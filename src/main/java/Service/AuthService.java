package Service;

import Repository.UserRepositry;
import com.sun.xml.bind.v2.runtime.output.SAXOutput;
import dto.RegisterRequest;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired // no need to create instance everytime, just autowire and spring will add dependency automatically
    private UserRepositry userRepositry;

    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUserName(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        user.setEmail(registerRequest.getEmail());
        userRepositry.save(user);
        System.out.println(registerRequest.toString());
    }
}
