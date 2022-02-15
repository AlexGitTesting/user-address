package com.example.useraddresses.web;

import com.example.useraddresses.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Sql({"classpath:statements/insert_user.sql",
        "classpath:statements/insert_address.sql"
})
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {
        "classpath:statements/truncate_user.sql",
        "classpath:statements/truncate_address.sql"})
@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createUser() throws Exception {
        final UserDto userDto = UserDto.builder().firstname("First  name").lastname("last name").patronymic("patronymic").email("trener.brovar@ukr.net").build();
        final MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/user/create.json")
                .locale(new Locale("uk"))
                .content(objectMapper.writeValueAsString(userDto))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON);

        mockMvc.perform(post)
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void getUser() throws Exception {
        final MockHttpServletRequestBuilder get = MockMvcRequestBuilders.get("/user/{id}/get-user.json",100).accept(APPLICATION_JSON);
        mockMvc.perform(get).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void deleteUser() {

    }

    @Test
    void getUserForUpdate() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void getFilteredUsers() {
    }
}