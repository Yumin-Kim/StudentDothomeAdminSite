package kr.ac.seowon.media.studentadminsite.service.was;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

//CRUD
public interface CommandService<T> {
    //생성
    //수정
    //삭제 => 실질적으로 데이터 삭제는 하지 않음
    //읽기
    @GetMapping
    public T getWasInfo();
}
