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
        final Object invoke = getMainApp.invoke(portFolioForm);
        System.out.println(invoke);
//        Arrays.stream(portFolioForm.getClass().getDeclaredMethods()).forEach(a->{
//            final String name = a.getName();
//            System.out.println("name = " + name);
//        });
        final String s = portFolioForm.toString();
        final Field[] declaredFields = portFolioForm.getClass().getDeclaredFields();
        final List<String> collect = Arrays.stream(declaredFields)
                .filter(a -> a.getType() != String.class)
                .map(data -> data.getName())
                .collect(Collectors.toList());
        for (String s1 : collect) {
            System.out.println("s1 = " + s1);
            portFolioForm.getClass().getDeclaredMethod("get+"s1.to);

        }
        System.out.println("aClass = " + declaredFields);
        System.out.println("portFolioForm = " + s);
        // then
    }

}