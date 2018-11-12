package com.zhangyuwei.people.controller;

import com.sun.deploy.net.HttpResponse;
import com.zhangyuwei.people.commons.util.MD5Utils;
import com.zhangyuwei.people.dao.IpeopleDao;
import com.zhangyuwei.people.entities.People;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/PeopleServlet")
public class peopleServlet {
    ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
    IpeopleDao dao=ctx.getBean(IpeopleDao.class);
    /*test*/
    @RequestMapping("/test")
    public String test(Model model){
        model.addAttribute("message","测试");
        return "index";
    }
    /*查询所有*/
    @RequestMapping("/selectAllPeople")
    @ResponseBody
    public List<People> selectAllPeople(){
        return dao.selectAllPeople();
    }
    /*登录*/
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public void login(@RequestBody List<People> peopleList, HttpServletResponse response,HttpServletRequest request) throws IOException {
        /*解决服务端的乱码问题*/
        response.setCharacterEncoding("utf-8");
        String pname=peopleList.get(0).getPname();
        String password=peopleList.get(0).getPpassword();
        String passwordMD5=MD5Utils.getMD5String(password);
        People people=new People();
        people.setPname(pname);people.setPpassword(passwordMD5);
        if(dao.selectByNameandPassword(people).size()>0){
            HttpSession session=request.getSession();
            session.setAttribute("loginSuccess","loginSuccess");
            response.getWriter().print("登录成功!");

        }else{
            response.getWriter().print("登录失败!");
        }
    }
    /*添加*/
    @RequestMapping(value = "/insertPeople",method = RequestMethod.POST)
    @ResponseBody
    public void insertPeople(@RequestBody List<People> peopleList, HttpServletResponse response) throws IOException {
        /*解决服务端的乱码问题*/
        response.setCharacterEncoding("utf-8");
        String password=MD5Utils.getMD5String(peopleList.get(0).getPpassword());
        People people=new People();
        people.setPname(peopleList.get(0).getPname());
        people.setPphone(peopleList.get(0).getPphone());
        people.setPemail(peopleList.get(0).getPemail());
        people.setPpassword(password);
        int row=dao.insertPeople(people);
        if(row>0){
            response.getWriter().print("注册成功!");
        }else{
            response.getWriter().print("注册失败!");
        }
    }
    /*删除*/
    @RequestMapping(value = "/deletePeople",method = RequestMethod.POST)
    @ResponseBody
    public void deletePeople(@RequestBody Integer pid,HttpServletResponse response) throws IOException {
        /*解决服务端的乱码问题*/
        response.setCharacterEncoding("utf-8");
        int row=dao.deletePeople(pid);
        if(row>0){
            response.getWriter().print("删除成功!");
        }else{
            response.getWriter().print("删除失败!");
        }
    }
    /*修改*/
    @RequestMapping(value = "/updatePeople",method = RequestMethod.POST)
    @ResponseBody
    public void updatePeople(@RequestBody List<People> peopleList, HttpServletResponse response) throws IOException {
        /*解决服务端的乱码问题*/
        response.setCharacterEncoding("utf-8");
        People people=new People();
        people.setPname(peopleList.get(0).getPname());
        people.setPphone(peopleList.get(0).getPphone());
        people.setPemail(peopleList.get(0).getPemail());
        people.setPid(peopleList.get(0).getPid());
        int row=dao.updatePeople(people);
        if(row>0){
            response.getWriter().print("修改成功!");
        }else{
            response.getWriter().print("修改失败!");
        }
    }
    /*查询数量*/
    @RequestMapping("/selectPeopleCount")
    @ResponseBody
    public int selectPeopleCount(@RequestBody List<Object> objectList){
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("pname",objectList.get(0));map.put("pphone",objectList.get(1));
        map.put("pemail",objectList.get(2));
        int count=dao.selectPeopleCount(map);
        return count;
    }
    /*分页*/
    @RequestMapping("/selectPeoplePageByIf")
    @ResponseBody
    public List<People> selectPeoplePageByIf(@RequestBody List<Object> integerList){
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("pname",integerList.get(0));map.put("pphone",integerList.get(1));
        Object pages=integerList.get(3);
        int page1=(int)integerList.get(3)-1;
        int page2=page1*(int)integerList.get(4);
        map.put("pemail",integerList.get(2));map.put("page",page2);
        map.put("limit",integerList.get(4));
        return dao.selectPeoplePageByIf(map);
    }
    /*重置密码*/
    @RequestMapping(value = "/updatePassword",method = RequestMethod.POST)
    @ResponseBody
    public void updatePassword(@RequestBody Integer pid,HttpServletResponse response) throws IOException {
        /*解决服务端的乱码问题*/
        response.setCharacterEncoding("utf-8");
        People people=new People();
        people.setPid(pid);
        people.setPpassword(MD5Utils.getMD5String("000000"));
        int row=dao.updatePassword(people);
        if(row>0){
            response.getWriter().print("重置成功！");
        }else{
            response.getWriter().print("重置失败!");
        }
    }
    /*导出数据到Excel表格*/
    @RequestMapping(value = "/joinxml",method = RequestMethod.GET)
    @ResponseBody
    public void joinXml(HttpServletResponse response) throws IOException {
        //数据的来源
        List<People> entity=dao.selectAllPeople();
        //设置标题
        String head = "商品信息详细展示";
        //设置表头行
        String[] headrow = {"编号", "用户名", "手机号","邮箱","注册日期"};
        if (null != entity && entity.size() > 0) {
            String fileName = "用户信息.xls";//定义导出头
            response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));    //设置文件头编码格式
            response.setContentType("APPLICATION/OCTET-STREAM;charset=UTF-8");//设置类型
            response.setHeader("Cache-Control", "no-cache");//设置头
            response.setDateHeader("Expires", 0);//设置日期头
            //创建工作簿HSSFWorkbook 对象
            HSSFWorkbook book = new HSSFWorkbook();
            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
            HSSFSheet sheet = book.createSheet();
            // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
            HSSFRow row = sheet.createRow(0);
            //由工作簿创建表HSSFSheet对象
            CellStyle cellStyle = book.createCellStyle();

            cellStyle.setDataFormat(book.createDataFormat().getFormat("yyyy-MM-dd"));

            //设置表头
            HSSFCell cell = row.createCell((short) 0);
            cell.setCellValue(head);
            row = sheet.createRow(1);
            for (int i = 0; i < headrow.length; i++) {
                cell = row.createCell((short) i);
                cell.setCellValue(headrow[i]);
            }

            for (int i = 0; i < entity.size(); i++) {
                //实体类对象
                row = sheet.createRow((i + 2));
                Date times=entity.get(i).getPtime();
                row.createCell((short) 0).setCellValue(entity.get(i).getPid());
                row.createCell((short) 1).setCellValue(entity.get(i).getPname());
                row.createCell((short) 2).setCellValue(entity.get(i).getPphone());
                row.createCell((short) 3).setCellValue(entity.get(i).getPemail());
                row.createCell((short) 4).setCellValue(times.toLocaleString());
            }
            //写出流  刷新缓冲流  关闭流对象
            book.write(response.getOutputStream());
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }
    }
    /**
     * 导入数据到Excel表格
     *excel表格的形式为xlsx
     * @throws IOException
     */
    @RequestMapping(value = "/outxml",method = RequestMethod.POST)
    @SuppressWarnings("resource")
    public String excels(MultipartFile files, HttpServletResponse response, HttpServletRequest request) throws IOException, ServletException {
        //文件存放的位置
        String path=request.getSession().getServletContext().getRealPath("/files2");
        File f=new File(path);
        if(!f.exists()){
            f.mkdir();
        }
        //保存文件3
        File tempFile=new File(path, files.getOriginalFilename());
        files.transferTo(tempFile);//把文件从内存存到磁盘中
        System.out.println(path+"\\"+files.getOriginalFilename());

        //Excel导入数据
        InputStream is = new FileInputStream(path+"\\"+files.getOriginalFilename());
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        People emp = new People();
        // 循环工作表Sheet
        int row=0;int len=0;
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            // 循环行Row
            for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                    len++;
                    XSSFCell pname = xssfRow.getCell(0);
                    XSSFCell pphone = xssfRow.getCell(1);
                    XSSFCell pemail = xssfRow.getCell(2);
                    emp.setPname(getValue(pname));
                    emp.setPphone(getValue(pphone));
                    emp.setPemail(getValue(pemail));
                    if(dao.insertPeople(emp)>0){
                        row++;
                    }
                }
            }
        }
        if(row==len){
            try {
                response.getWriter().print("添加成功!");
            } catch (IOException es) {
                es.printStackTrace();
            }
        }else {
            try {
                response.getWriter().print("添加失败!");
            } catch (IOException es) {
                es.printStackTrace();
            }
        }
        //request.getRequestDispatcher("index.html").forward(request, response);
        return "redirect:http://localhost:8080/main.html";
    }
    @SuppressWarnings("static-access")
    private String getValue(XSSFCell xssfCell) {
        if (xssfCell.getCellType() == xssfCell.CELL_TYPE_BOOLEAN) {
            // 返回布尔类型的值
            return String.valueOf(xssfCell.getBooleanCellValue());
        } else if (xssfCell.getCellType() == xssfCell.CELL_TYPE_NUMERIC) {
            // 返回数值类型的值
            return String.valueOf(xssfCell.getNumericCellValue());
        } else {
            // 返回字符串类型的值
            return String.valueOf(xssfCell.getStringCellValue());
        }
    }

}
