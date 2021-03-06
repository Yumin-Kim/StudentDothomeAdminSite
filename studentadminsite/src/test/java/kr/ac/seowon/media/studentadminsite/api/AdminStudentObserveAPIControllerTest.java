package kr.ac.seowon.media.studentadminsite.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.seowon.media.studentadminsite.api.admin.AdminStudentObserveAPIController;
import kr.ac.seowon.media.studentadminsite.dto.adminobserve.AdminObserveReq;
import kr.ac.seowon.media.studentadminsite.exception.CustomCollectionValidtion;
import kr.ac.seowon.media.studentadminsite.service.adminobserve.AdminStudentObserveService;
import kr.ac.seowon.media.studentadminsite.utils.UtilConfigure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AdminStudentObserveAPIController.class)
@Disabled
class AdminStudentObserveAPIControllerTest {
    @MockBean
    UtilConfigure utilConfigure;

    @MockBean
    AdminStudentObserveService adminStudentObserveService;

    @MockBean
    CustomCollectionValidtion customCollectionValidtion;

    @Autowired
    WebApplicationContext wac;

    ObjectMapper objectMapper = new ObjectMapper();

    MockMvc mockMvc;

    @BeforeEach
    void configure() {
        // TODO ????????? ?????? ?????? ?????? ??????
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @Mock
    BindingResult bindingResult;

    @Test
    @DisplayName("?????? insert,inserts ????????? ?????? _ validation ????????? ??????")
    void insertStudentControllerLogic() throws Exception {
        //given
        AdminObserveReq.BasicStudentDto basicstduentDto = new AdminObserveReq.BasicStudentDto(null, 2016103091);
        AdminObserveReq.BasicStudentDto basicstduentDto1 = new AdminObserveReq.BasicStudentDto(null, 2016103091);
        String convertdata = objectMapper.writeValueAsString(basicstduentDto);
        List<AdminObserveReq.BasicStudentDto> listBasicDto = List.of(basicstduentDto, basicstduentDto1);
        String convertdataList = objectMapper.writeValueAsString(listBasicDto);
        //when
//        doNothing().when(customCollectionValidtion).validate(listBasicDto,bindingResult);
        mockMvc.perform(post("/api/admin/studentinfo/insert/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertdata)
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("data.studentCode[0]", is("?????? ?????? ????????? ?????????????????????. 9????????? ???????????????.")))
                .andExpect(jsonPath("data.name[0]", is("name??? ???????????? ????????????.")));


        mockMvc.perform(post("/api/admin/studentinfo/concurrentinsert/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertdataList)
        )
                .andDo(print());

    }



}