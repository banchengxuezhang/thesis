package com.jxufe.ljw.thesis;
import com.jxufe.ljw.thesis.bean.StudentInfo;
import com.jxufe.ljw.thesis.bean.TeacherInfo;
import com.jxufe.ljw.thesis.bean.User;
import com.jxufe.ljw.thesis.dao.StudentInfoDao;
import com.jxufe.ljw.thesis.dao.TeacherInfoDao;
import com.jxufe.ljw.thesis.dao.UserDao;
import com.jxufe.ljw.thesis.service.IMailService;
import com.jxufe.ljw.thesis.util.ChineseName;
import com.jxufe.ljw.thesis.util.Md5Tools;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ThesisApplicationTests {
    @Autowired
    private UserDao userDao;
    @Autowired
    private TeacherInfoDao teacherInfoDao;
    @Autowired
    private StudentInfoDao studentInfoDao;

    @Test
    void contextLoads() {
        String[] emails={"1213159526@qq.com","1213159526@qq.com","1213159526@qq.com"};
        String[] telephones={"13125330568","15575580459"};
        String[] majors={"软件工程","物联网工程","通信工程"};
        String[] titles={"讲师","教授","副教授"};
        String[] educations={"研究生导师","博士","博士生导师"};
        for(int i=164809;i<=165008;i++){
            User user=new User();
            String userId="USER"+System.currentTimeMillis();
            user.setUserId(userId);
            user.setUserType(3);
            user.setUserAccount("0"+i);
            user.setUserPassword(Md5Tools.convertMD5("0"+i));
            StudentInfo studentInfo=new StudentInfo();
            studentInfo.setUserId(userId);
            studentInfo.setStudentId("STUDENT"+System.currentTimeMillis());
            studentInfo.setStudentStage("待选题");
            studentInfo.setStudentClass("软件"+((i-164809)/50+1)+"班");
            studentInfo.setStudentEmail(emails[i%3]);
            studentInfo.setStudentInstructor("周杰伦");
            studentInfo.setStudentNo("0"+i);
            studentInfo.setStudentName(ChineseName.getName());
            studentInfo.setStudentMajor(majors[i%3]);
            studentInfo.setStudentPhone(telephones[i%2]);
            userDao.addUser(user);
            studentInfoDao.addStudentInfo(studentInfo);
        }
        for(int i=1604809;i<=1604858;i++){
            User user=new User();
            String userId="USER"+System.currentTimeMillis();
            user.setUserId(userId);
            user.setUserType(2);
            user.setUserAccount("220"+i);
            user.setUserPassword(Md5Tools.convertMD5("220"+i));
            TeacherInfo teacherInfo=new TeacherInfo();
            teacherInfo.setUserId(userId);
            teacherInfo.setTeacherNo("220"+i);
            teacherInfo.setTeacherEmail(emails[i%3]);
            teacherInfo.setTeacherPhone(telephones[i%2]);
            teacherInfo.setTeacherId("TEACHER"+System.currentTimeMillis());
            teacherInfo.setTeacherName(ChineseName.getName());
            teacherInfo.setTeacherEducation(educations[i%3]);
            teacherInfo.setTeacherTitle(titles[i%3]);
            userDao.addUser(user);
            teacherInfoDao.addTeacherInfo(teacherInfo);
        }
    }

}
