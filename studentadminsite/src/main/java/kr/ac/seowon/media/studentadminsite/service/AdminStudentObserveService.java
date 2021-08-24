package kr.ac.seowon.media.studentadminsite.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.seowon.media.studentadminsite.domain.Admin;
import kr.ac.seowon.media.studentadminsite.domain.Student;
import kr.ac.seowon.media.studentadminsite.dto.AdminObserveReq;
import kr.ac.seowon.media.studentadminsite.exception.AdminObserveException;
import kr.ac.seowon.media.studentadminsite.exception.InsertDuplicateException;
import kr.ac.seowon.media.studentadminsite.repository.AdminRepository;
import kr.ac.seowon.media.studentadminsite.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminStudentObserveService {

    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;

    public void insertStudentInfo(Integer adminId, AdminObserveReq.BasicStudentDto basicStudentDto) {
        Admin observeAdmin = getAdmin(adminId);
        studentRepository.findByStudentCodeAndName(basicStudentDto.getStudentCode(), basicStudentDto.getName())
                .map(student -> {
                    if (student != null) {
                        throw new AdminObserveException("존재하는 학번 저장을 시도하였습니다.");
                    }
                    return null;
                });
        Student student = Student.createStudent(basicStudentDto,observeAdmin);
        studentRepository.save(student);
    }

    public void concurrentInsertStudentsInfo(Integer adminId, List<AdminObserveReq.BasicStudentDto> basicStudentDtos) throws JsonProcessingException {
        Admin observeAdmin = getAdmin(adminId);
        List<Integer> studentCodes = basicStudentDtos.stream()
                .map(AdminObserveReq.BasicStudentDto::getStudentCode)
                .collect(toList());
        Set<Integer> hashstudentCodes = basicStudentDtos.stream()
                .map(AdminObserveReq.BasicStudentDto::getStudentCode)
                .collect(toSet());
        if(studentCodes.size() != hashstudentCodes.size()) throw new AdminObserveException("중복 되는 학번이 존재합니다");
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
                    .map(basicStudentDto -> Student.createStudent(basicStudentDto, observeAdmin))
                    .forEach(student -> studentRepository.save(student));
        }

    }

    private Admin getAdmin(Integer adminId) {
        return adminRepository.findById(adminId).orElseThrow(() -> new AdminObserveException("존재하지 않는 관리자 입니다"));
    }
}
