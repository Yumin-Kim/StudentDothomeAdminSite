package kr.ac.seowon.media.studentadminsite.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.seowon.media.studentadminsite.dto.admin.AdminDtoRes;
import kr.ac.seowon.media.studentadminsite.dto.admin.AdminReq;
import kr.ac.seowon.media.studentadminsite.service.admin.AdminRootCommandService;
import kr.ac.seowon.media.studentadminsite.service.admin.AdminRootQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//TODO 기존 테스트 수정은 완료 했으나 API 재수정하고 Disabled 해제 후 확
@WebMvcTest(controllers = {AdminRootInfoAPIController.class})
@Disabled
class AdminRootInfoAPIControllerTest {

    @MockBean
    AdminRootQueryService adminRootService;

    @MockBean
    AdminRootCommandService adminRootCommandService;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void configure() {
        // TODO 테스트 간에 한글 깨짐 방지
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("admin 생성 controller")
    @Disabled
    void createAdmin_controller() throws Exception {
        //given
        AdminReq.AdminDto adminDto = new AdminReq.AdminDto("name", "hashcode", "123123", "password");
        AdminDtoRes.BasicAdmin basicAdmin = new AdminDtoRes.BasicAdmin(any(),adminDto.getName(), adminDto.getHashCode(), adminDto.getPhoneNumber(),any());
        given(adminRootCommandService.createAdmin(any()))
                .willReturn(basicAdmin);
        //when
        mockMvc.perform(post("/api/admin/rootinfo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(adminDto))
        )
                //then
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("data.name", is(adminDto.getName())))
                .andExpect(jsonPath("data.hashCode", is(adminDto.getHashCode())));
    }

    @Test
    @DisplayName("admin 생성 controller validation Exception")
    void createAdmin_controller_error() throws Exception {
        //given
        AdminReq.AdminDto adminDto = new AdminReq.AdminDto(null, "hashcode", "", "");
        AdminDtoRes.BasicAdmin basicAdmin = new AdminDtoRes.BasicAdmin(any(),adminDto.getName(), adminDto.getHashCode(), adminDto.getPhoneNumber(),any());

        given(adminRootCommandService.createAdmin(any()))
                .willReturn(basicAdmin);
        mockMvc.perform(post("/api/admin/rootinfo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(adminDto))
        )
                //then
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("data.password[0]", is("password가 공백입니다")))
                .andExpect(jsonPath("data.phoneNumber[0]", is("phoneNumber가 공백입니다")))
                .andExpect(jsonPath("data.name[0]", is("name이 공백입니다")));
    }

    @Test
    @DisplayName("admin 로그인시 controller validtion Exception")
    void loginAdmin_controller_validation() throws Exception{
        //given
        AdminReq.AdminDto adminDto = new AdminReq.AdminDto("asd", "hashcode", "qwe", "qwe");
        AdminDtoRes.BasicAdmin basicAdmin = new AdminDtoRes.BasicAdmin(any(),adminDto.getName(), adminDto.getHashCode(), adminDto.getPhoneNumber(),any());

        given(adminRootService.loginAdmin(any()))
                .willReturn(basicAdmin);
        //when
        mockMvc.perform(post("/api/admin/rootinfo/login")
                .param("name", "name")
                .param("password", "")
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", is("입력한 정보를 다시 확인해주세요")))
                .andExpect(jsonPath("data.password[0]", is("password가 공백입니다")));

        //then
    }


    @Test
    @DisplayName("admin 계정 수정 controller validtion Exception")
    void modifyAdmin_controller_validation() throws Exception{
        //given
        AdminReq.AdminDto adminDto = new AdminReq.AdminDto(null, null, null, null);
        AdminDtoRes.BasicAdmin basicAdmin = new AdminDtoRes.BasicAdmin(any(),adminDto.getName(), adminDto.getHashCode(), adminDto.getPhoneNumber(),any());

        given(adminRootService.loginAdmin(any()))
                .willReturn(basicAdmin);
        //when
        mockMvc.perform(put("/api/admin/rootinfo/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(basicAdmin))
        )
                //then
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("admin 페이징")
    void findAllPagingAdmin_controller() throws Exception{
        //given
        ArrayList<AdminDtoRes.BasicAdmin> collect = new ArrayList<>();
        for (int i = 0 ; i < 20; i++) {
            AdminReq.AdminDto adminDto = new AdminReq.AdminDto("name"+i, "hashcode", "123123", "password");
            AdminDtoRes.BasicAdmin basicAdmin = new AdminDtoRes.BasicAdmin(any(),adminDto.getName(), adminDto.getHashCode(), adminDto.getPhoneNumber(),any());

            collect.add(basicAdmin);
        }
        AdminDtoRes.BasicPagingAdmin basicPagingAdmin = new AdminDtoRes.BasicPagingAdmin(collect, 0, 5, 20, 100);
        given(adminRootService.findAllPagingV1(any()))
                .willReturn(basicPagingAdmin);
        //when
        mockMvc.perform(get("/api/admin/rootinfo/adminall")
                .param("page", "0")
                .param("size", "20")
                .param("sort", "name,asc")
        )
                .andDo(print());

        //then
    }

}