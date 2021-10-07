package kr.ac.seowon.media.studentadminsite.service;

public interface CommandService<T> {
    //정보 생성
    T createEntity();
    //정보 수정
    T deleteEntity();
    //정보 삭제
    T modifyEntity();
}
