package devxp-tech.apptemplate.controller;

import devxp-tech.Application;
import devxp-tech.apptemplate.repository.UserRepository;
import devxp-tech.bootstrap.test.BaseControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
public class UserControllerTest extends BaseControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void find_all_users_empty() throws Exception {
        mvc.perform(get("/user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(readJson("json/user/find_all_empty_success.json")));
    }

    @Test
    public void find_one_not_found() throws Exception {
        mvc.perform(get("/user/fc2f789d-f126-46b7-a1a4-b9791f07ab55")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void create_user() throws Exception {
        mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(readJson("json/user/request/create_user.json")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(readJson("json/user/create_user_success.json")))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }


    @Test
    public void find_all_users_one_result() throws Exception {
        mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(readJson("json/user/request/create_user.json")))
                .andExpect(status().isOk());

        mvc.perform(get("/user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(readJson("json/user/find_all_one_user_success.json")));
    }

    @Test
    public void find_one() throws Exception {
        mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(readJson("json/user/request/create_user.json")))
                .andExpect(status().isOk());

        UUID id = userRepository.findAll().get(0).getId();

        mvc.perform(get("/user/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(readJson("json/user/create_user_success.json")))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }
}
