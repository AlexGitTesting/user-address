package com.example.useraddresses.web;

import com.example.useraddresses.dto.AddressDto;
import com.example.useraddresses.dto.CountryDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Locale;

@Sql({"classpath:statements/insert_user.sql",
        "classpath:statements/insert_address.sql"
})
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = {
        "classpath:statements/truncate_user.sql",
        "classpath:statements/truncate_address.sql"})
@AutoConfigureMockMvc
@SpringBootTest
class AddressControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private Logger log = LoggerFactory.getLogger(AddressControllerTest.class);

    @Test
    void createAddress() throws Exception {
        final CountryDto countryDto = CountryDto.builder().id(5L).name("kjshglkhg").build();
        final AddressDto addressDto = AddressDto.builder().flatNumber("12").houseNumber("12").street("kklhh").city("city").countryDto(countryDto).build();
        final MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/address/{id}/create.json", 101)
                .locale(new Locale("en"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(List.of(addressDto)));

        mockMvc.perform(post).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    void deleteAddress() {

    }

    @Test
    void updateAddress() {
    }


}