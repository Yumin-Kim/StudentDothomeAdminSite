package kr.ac.seowon.media.studentadminsite;

import kr.ac.seowon.media.studentadminsite.domain.Admin;
import kr.ac.seowon.media.studentadminsite.domain.Student;
import kr.ac.seowon.media.studentadminsite.dto.AdminReq;
import kr.ac.seowon.media.studentadminsite.dto.StudentReq;
import kr.ac.seowon.media.studentadminsite.exception.domainexception.AdminException;
import kr.ac.seowon.media.studentadminsite.exception.domainexception.StudentException;
import kr.ac.seowon.media.studentadminsite.repository.AdminRepository;
import kr.ac.seowon.media.studentadminsite.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

@Component
public class SessionFactory {

    @Autowired
    private  AdminRepository adminRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Transactional
    public void makeSession(HttpServletRequest request, String sessionName, Object data) {
        HttpSession session = request.getSession();
        if (sessionName == "admin"){
            AdminReq.AdminLoginDto adminLoginDto = (AdminReq.AdminLoginDto) data;
            Admin findByAdmin = adminRepository.findByNameAndPassword(adminLoginDto.getName(), adminLoginDto.getPassword())
                    .orElseThrow(() -> new AdminException("존재하지 않는 관리자 입니다"));
            session.setAttribute(sessionName, findByAdmin.getId());
        }else{
            StudentReq.StudentDto studentDto = (StudentReq.StudentDto) data;
            Student findByStudent = studentRepository.findByStudentCodeAndName(studentDto.getStudentCode(), studentDto.getName())
                    .orElseThrow(() -> new StudentException("존재하지 않는 학생 입니다"));
            session.setAttribute(sessionName, findByStudent.getId());
        }


    }

    @Transactional
    public void validationSession(HttpServletRequest request, String sessionName) {
        HttpSession session = request.getSession();
        Integer entityId = (Integer)session.getAttribute(sessionName);
        if (entityId == null) throw new AdminException("세션이 포함되어 있지 않습니다");
        else if(sessionName == "admin" && entityId != null) adminRepository.findById(entityId).orElseThrow(()-> new AdminException("존재하지 않는 관리자 입니다."));
        else if(sessionName == "student" && entityId != null) studentRepository.findById(entityId).orElseThrow(()-> new AdminException("존재하지 않는 학생 입니다."));

    }

    public void removeSession(HttpServletRequest request,String sessionName) {
        HttpSession session = request.getSession();
        session.removeAttribute(sessionName);
    }
}
