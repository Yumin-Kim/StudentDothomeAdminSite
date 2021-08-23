package kr.ac.seowon.media.studentadminsite.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.seowon.media.studentadminsite.domain.Student;
import kr.ac.seowon.media.studentadminsite.dto.AdminObserveReq;
import kr.ac.seowon.media.studentadminsite.exception.AdminObserveException;
import kr.ac.seowon.media.studentadminsite.exception.InsertDuplicateException;
import kr.ac.seowon.media.studentadminsite.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class AdminStudentObserveService {

    private final StudentRepository studentRepository;

    public void insertStudentInfo(AdminObserveReq.BasicStudentDto basicStudentDto) {
        studentRepository.findByStudentCodeAndName(basicStudentDto.getStudentCode(), basicStudentDto.getName())
                .ifPresent(student -> new AdminObserveException("존재하는 학번 저장을 시도하였습니다."));
        Student student = Student.createStudent(basicStudentDto);
        studentRepository.save(student);
    }

    public void concurrentInsertStudentsInfo(List<AdminObserveReq.BasicStudentDto> basicStudentDtos) throws JsonProcessingException {
        List<Integer> studentCodes = basicStudentDtos.stream()
                .map(AdminObserveReq.BasicStudentDto::getStudentCode)
                .collect(toList());
        List<Student> findByStudentCodes = studentRepository.findByStudentCodeIn(studentCodes);
        if (findByStudentCodes.size() != 0) {
            List<Integer> duplicateStudents = findByStudentCodes.stream()
                    .map(Student::getStudentCode)
                    .collect(toList());
            ObjectMapper objectMapper = new ObjectMapper();
            String convertJsonData = objectMapper.writeValueAsString(duplicateStudents);
            throw new InsertDuplicateException(convertJsonData);
        }else{
            basicStudentDtos.stream()
                    .map(Student::createStudent)
                    .forEach(student -> studentRepository.save(student));
        }

    }
}
