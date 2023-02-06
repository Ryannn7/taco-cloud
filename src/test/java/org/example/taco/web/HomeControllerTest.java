package org.example.taco.web;

import static org.hamcrest.Matchers.containsString;
import static
        org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static
        org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static
        org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static
        org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author liuzongshuai
 * @date 2022/11/16 10:01
 */
@RunWith(SpringRunner.class)
/** * 这是Spring Boot所提供的一个特殊测试注解，它会让这个测试在Spring MVC应用的上下文中执行。更具体来讲，在本例中，它会将HomeController注册到Spring MVC中，这样的话，我们就可以向它发送请求了。 */
@WebMvcTest(HomeController.class)
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
   public void testHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(content().string(
        containsString("Welcome to...")));
    }
}