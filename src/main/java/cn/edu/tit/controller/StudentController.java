/**
 * 
 */
package cn.edu.tit.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import cn.edu.tit.bean.Category;
import cn.edu.tit.bean.Course;
import cn.edu.tit.bean.ResourceType;
import cn.edu.tit.bean.Student;
import cn.edu.tit.bean.Teacher;
import cn.edu.tit.common.Common;
import cn.edu.tit.iservice.IAdminService;
import cn.edu.tit.iservice.IResourceService;
import cn.edu.tit.iservice.IStudentService;
import cn.edu.tit.iservice.ITeacherService;

/**
 * @author LiMing
 *
 */
@RequestMapping("/student")
@Controller
public class StudentController {
	/**
	 * 声明变量
	 * */
	@Autowired
	private ITeacherService teacherService;
	@Autowired
	private IResourceService resourceService;
	private static List<Category> categories = null;//将  分类 信息作为全局变量，避免多次定义,在首次登陆教师页面时 在  方法teacherCourseList（） 处即初始化成功
	private static List<ResourceType> resourceCategories = null;//将资源分类作为全局变量
	@Autowired
	private IStudentService studentService;

	@RequestMapping(value="LoginStudent")
	public ModelAndView LoginStudent(@RequestParam(value="employeeNum") String employeeNum,@RequestParam(value="password") String password,HttpServletRequest request) {			
		ModelAndView mv = new ModelAndView();
		String readResult =null;
		String psw = null;
		Student student = new Student();
		request.getSession().setAttribute("studentId", null);
		try {
			student = studentService.studentLoginByEmployeeNum(employeeNum);
			psw = Common.eccryptMD5(password);
			if(!psw.equals(student.getStudentPassword()))
			{
				mv.addObject("readResult", "密码错误");//返回信息
				mv.setViewName("/jsp/Teacher/index");//设置返回页面
			}
			else {
				mv.addObject("readResult", readResult);//返回信息
				request.getSession().setAttribute("studentId", student.getStudentId());
				request.getSession().setAttribute("student", student);
				mv=mainController.toMain(request); //去首页
			}

		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("readResult", "密码错误");//返回信息
			mv.setViewName("/jsp/Teacher/index");//设置返回页面
		}
		return mv;
	}

	/**
	 * 跳转到学生中心,此方法作为第一入口
	 * @throws Exception 
	 * */
	@RequestMapping(value="toStudentPage")
	public ModelAndView toStudentPage(HttpServletRequest request) throws Exception {			
		ModelAndView mv = new ModelAndView();
		Student student = (Student) request.getSession().getAttribute("student");
		List<String> listCourseId_MyCourse = new ArrayList<String>();
		List<String> listCourseId_Myattention = new ArrayList<String>();
		List<Course> listCourse_MyCourse = new ArrayList<Course>();
		List<Course> listCourse_Myattention = new ArrayList<Course>();
		//获取所有关注课程ID
		listCourseId_MyCourse = studentService.getStudentCourse("0",student.getStudentId());
		//获取所有加入课程ID
		listCourseId_Myattention = studentService.getStudentCourse("1",student.getStudentId());
		//获取课程ID对应的课程
		listCourse_MyCourse = teacherService.courseList(listCourseId_MyCourse);
		listCourse_Myattention = teacherService.courseList(listCourseId_Myattention);
		mv.addObject("listCourse_MyCourse", listCourse_MyCourse);//返回信息
		mv.addObject("listCourse_Myattention", listCourse_Myattention);//返回信息
		mv.setViewName("/jsp/StudentJsp/studentCenter_MyCourse");//设置返回页面
		return mv;
	}

	@RequestMapping(value="toStudentCenter_MyCourse")
	public ModelAndView toStudentCenter_MyCourse() {			
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/jsp/StudentJsp/studentCenter_MyCourse");//设置返回页面
		return mv;
	}

	@RequestMapping(value="toStudentCenter_MyClass")
	public ModelAndView toStudentCenter_MyClass() {			
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/jsp/StudentJsp/studentCenter_MyClass");//设置返回页面
		return mv;
	}
	@RequestMapping(value="toStudentCenter_MyDiscuss")
	public ModelAndView toStudentCenter_MyDiscuss() {			
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/jsp/StudentJsp/studentCenter_MyDiscuss");//设置返回页面
		return mv;
	}
	
	@RequestMapping(value="toStudentCenter_MyInfo")
	public ModelAndView toStudentCenter_MyInfo(HttpServletRequest request) throws Exception {			
		ModelAndView mv = new ModelAndView();
		Student student = (Student) request.getSession().getAttribute("student");
		Category category = new Category();
		category = studentService.getCategoryById(student.getStudentCategory());
		mv.addObject("student", student);//返回信息
		mv.addObject("category", category);//返回信息
		mv.setViewName("/jsp/StudentJsp/studentCenter_MyInfo");//设置返回页面
		return mv;
	}
}
