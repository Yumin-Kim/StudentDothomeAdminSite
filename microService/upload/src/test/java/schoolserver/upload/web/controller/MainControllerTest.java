package schoolserver.upload.web.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import schoolserver.upload.web.dto.PortFolioForm;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class MainControllerTest {

    @Test
    @DisplayName("")
    void MainControllerTest() throws Exception{
        // given
        final PortFolioForm portFolioForm = new PortFolioForm();
        portFolioForm.setSpeechLink("asd");
        // when
        final String s = portFolioForm.toString();
        final Field[] declaredFields = portFolioForm.getClass().getDeclaredFields();
        final List<String> collect = Arrays.stream(declaredFields)
                .filter(a -> a.getType() != String.class)
                .map(data -> data.getName())
                .collect(Collectors.toList());
        for (String s1 : collect) {
            System.out.println("s1 = " + s1);
            final Method declaredMethod = portFolioForm.getClass().getDeclaredMethod("get" + upperCaseFirst(s1));
            final Object invoke1 = declaredMethod.invoke(portFolioForm);
            System.out.println("invoke1 = " + invoke1);
        }

        // then
    }
    private String upperCaseFirst(String val) {
        char[] arr = val.toCharArray();
        arr[0] = Character.toUpperCase(arr[0]);
        return new String(arr);
    }

}