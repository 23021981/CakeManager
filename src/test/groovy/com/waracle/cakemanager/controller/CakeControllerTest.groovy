package com.waracle.cakemanager.controller

import groovy.json.JsonOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.csrf.CsrfToken
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CakeControllerTest extends Specification{

    @Autowired
    MockMvc mvc;


    def "when getAllCakeDetails is performed then the response has status 200 and content is '[]'"() {
        expect: "Status is 200 and the response is '[]'"
        OAuth2AuthenticationToken authenticationToken = createToken();
        mvc.perform(get("/cakes").with(authentication(authenticationToken)))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentAsString == "[{\"cakeId\":\"C10\",\"cakeName\":\"Banana cake\",\"cakeDesc\":\"Donkey kongs favourite\",\"imageUrl\":\"http://ukcdn.ar-cdn.com/recipes/xlarge/ff22df7f-dbcd-4a09-81f7-9c1d8395d936.jpg\"}," +
                "{\"cakeId\":\"C11\",\"cakeName\":\"Lemon cheesecake\",\"cakeDesc\":\"A cheesecake made of lemon\",\"imageUrl\":\"https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg\"}]"
    }

    def "when getCakeDetail is performed then the response has status 200 and return content"() {
        expect: "Status is 200 and the response is 'C10 details'"
        OAuth2AuthenticationToken authenticationToken = createToken();
        mvc.perform(get("/cakes/{cakeId}","C10").with(authentication(authenticationToken)))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentAsString == "{\"cakeId\":\"C10\",\"cakeName\":\"Banana cake\",\"cakeDesc\":\"Donkey kongs favourite\",\"imageUrl\":\"http://ukcdn.ar-cdn.com/recipes/xlarge/ff22df7f-dbcd-4a09-81f7-9c1d8395d936.jpg\"}";
    }

    def "when addNewCake is performed then the response has status 200 and check content"() {
        given:
        Map cake = [
                cakeId:'C01',
                cakeName:'TestCake',
                cakeDesc:'Round TestCake',
                imageUrl:'wwww.test.com/image01.jpg'
        ]

        OAuth2AuthenticationToken authenticationToken = createToken();

        HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
        String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";

        when:
        def results = mvc.perform(post("/addCake")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .with(authentication(authenticationToken))
                .contentType("application/json")
                        .content(JsonOutput.toJson(cake)));

        then:
        results.andExpect(status().isOk())

       and:
       results.andExpect(jsonPath('$.cakeId').value('C01'))
       results.andExpect(jsonPath('$.cakeName').value('TestCake'))
       results.andExpect(jsonPath('$.cakeDesc').value('Round TestCake'))
       results.andExpect(jsonPath('$.imageUrl').value('wwww.test.com/image01.jpg'))
    }

    def "when getCakeDetail is performed then the response has exception"() {
        given:
        OAuth2AuthenticationToken authenticationToken = createToken();
        when:
        def result = mvc.perform(get("/cakes/{cakeId}","C02").with(authentication(authenticationToken)))
                .andReturn();

        then:
        result.response.status == HttpStatus.INTERNAL_SERVER_ERROR.value()
    }

    def "when generateFile is performed then then the response has status 200"() {
        given:
        OAuth2AuthenticationToken authenticationToken = createToken();
        when:
        def result = mvc.perform(get("/cakeJsonData").with(authentication(authenticationToken)))
                .andReturn();;

        then:
        result.response.status == HttpStatus.OK.value()
    }

    private OAuth2AuthenticationToken createToken() {
        Set<GrantedAuthority> authorities = new HashSet<>(AuthorityUtils.createAuthorityList("USER"));
        OAuth2User oAuth2User = new DefaultOAuth2User(authorities,
                Collections.singletonMap("name", "Atul"), "name");
        return new OAuth2AuthenticationToken(oAuth2User, authorities,
                "google");
    }
}


