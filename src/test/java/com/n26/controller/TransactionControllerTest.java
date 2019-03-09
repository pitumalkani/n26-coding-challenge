package com.n26.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.n26.Application;

@RunWith ( SpringRunner.class )
@SpringBootTest ( webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = {Application.class} )
@ComponentScan ( basePackages = {"com.n26"} )
@AutoConfigureMockMvc
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void testPostForSuccess() throws Exception {
        StringBuilder json = new StringBuilder();
        json.append( "{" );
        json.append( "\"amount\": 5.0" );
        json.append( "," );
        json.append( "\"timestamp\":\"" + LocalDateTime.now().toString() + "\"" );
        json.append( "}" );
        this.mockMvc.perform( post( "/transactions" ).contentType( MediaType.APPLICATION_JSON ).content( json.toString() ).accept( MediaType.APPLICATION_JSON ) ).andExpect( status().isCreated() );
    }

    @Test
    public void testPostForExpiredTransaction() throws Exception {
        StringBuilder json = new StringBuilder();
        json.append( "{" );
        json.append( "\"amount\": 5.0" );
        json.append( "," );
        json.append( "\"timestamp\":\"" + LocalDateTime.now().minusMinutes( 1 ).toString() + "\"" );
        json.append( "}" );
        this.mockMvc.perform( post( "/transactions" ).contentType( MediaType.APPLICATION_JSON ).content( json.toString() ).accept( MediaType.APPLICATION_JSON ) ).andExpect( status().isNoContent() );
    }

    @Test
    public void testPostForFutureTransaction() throws Exception {
        StringBuilder json = new StringBuilder();
        json.append( "{" );
        json.append( "\"amount\": 5.0" );
        json.append( "," );
        json.append( "\"timestamp\":\"" + LocalDateTime.now().plusMinutes( 2 ).toString() + "\"" );
        json.append( "}" );
        this.mockMvc.perform( post( "/transactions" ).contentType( MediaType.APPLICATION_JSON ).content( json.toString() ).accept( MediaType.APPLICATION_JSON ) )
                    .andExpect( status().isUnprocessableEntity() );
    }
    

    @Test
    public void testPostForInvalidTransaction() throws Exception {
        StringBuilder json = new StringBuilder();
        json.append( "{" );
        json.append( "\"amount\": 5.0" );
        json.append( "}" );
        this.mockMvc.perform( post( "/transactions" ).contentType( MediaType.APPLICATION_JSON ).content( json.toString() ).accept( MediaType.APPLICATION_JSON ) )
                    .andExpect( status().isBadRequest() );
    }

}
