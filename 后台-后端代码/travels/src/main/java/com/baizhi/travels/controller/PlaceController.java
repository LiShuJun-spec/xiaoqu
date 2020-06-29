package com.baizhi.travels.controller;

import com.baizhi.travels.entity.Place;
import com.baizhi.travels.entity.Result;
import com.baizhi.travels.service.PlaceService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/place")
public class PlaceController {


    @Autowired
    private PlaceService placeService;

//    @Value("${upload.dir}")
//    private String realPath;


    /**
     * 修改景点信息
     */
    @PostMapping("update")
    public Result update(MultipartFile pic, Place place,HttpServletRequest request) throws IOException {
//        Result result = new Result();
//
//        try{
//            //对接收文件做base64
//            String picpath = Base64Utils.encodeToString(pic.getBytes());
//            place.setPicpath(picpath);
//            //处理文件上传
//            String extension = FilenameUtils.getExtension(pic.getOriginalFilename());
//            String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + extension;
//            pic.transferTo(new File(realPath,newFileName));
//
//            //修改景点信息
//            placeService.update(place);
//            result.setMsg("修改景点信息成功");
//        }catch (Exception e){
//            e.printStackTrace();
//            result.setState(false).setMsg(e.getMessage());
//        }
//        return result;
        Result result = new Result();
        try {
            //获取文件在服务器的储存位置
            String path = request.getSession().getServletContext().getRealPath("/") + "upload/img";
            File filePath = new File(path);
            if (!filePath.exists() && !filePath.isDirectory()) {
                filePath.mkdir();
            }
            //获取原始文件名称(包含格式)
            String originalFileName = pic.getOriginalFilename();

            //获取文件类型，以最后一个`.`为标识
            String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
            //获取文件名称（不包含格式）
            String name = originalFileName.substring(0, originalFileName.lastIndexOf("."));

            //设置文件新名称: 当前时间+文件名称（不包含格式）
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String date = sdf.format(d);
            String fileName = date + "_"+ name + "." + type;

            //在指定路径下创建一个文件
            File targetFile = new File(path, fileName);
            //文件上传
            //base64编码处理
            place.setPicpath(Base64Utils.encodeToString(pic.getBytes()));

            pic.transferTo(targetFile);
            //保存place对象
            placeService.save(place);
            result.setMsg("保存景点信息成功!!!");
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(false).setMsg(e.getMessage());
        }

        return result;
    }

    /**
     * 查询景点信息
     */
    @GetMapping("findOne")
    public Place findOne(String id){
        return placeService.findOne(id);
    }

    /**
     * 删除景点信息
     * @param id
     * @return
     */
    @GetMapping("delete")
    public Result delete(String id){
        Result result = new Result();
        try{
            placeService.delete(id);
            result.setMsg("删除景点信息成功");
        }catch (Exception e){
            e.printStackTrace();
            result.setState(false).setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 保存景点信息
     *
     * @param pic
     * @return
     */
    @PostMapping("save")
    public Result save(MultipartFile pic, Place place, HttpServletRequest request) throws IOException {
        Result result = new Result();
        try {
        //获取文件在服务器的储存位置
        String path = request.getSession().getServletContext().getRealPath("/") + "upload/img";
        File filePath = new File(path);
        if (!filePath.exists() && !filePath.isDirectory()) {
            filePath.mkdir();
        }
        //获取原始文件名称(包含格式)
        String originalFileName = pic.getOriginalFilename();

        //获取文件类型，以最后一个`.`为标识
        String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        //获取文件名称（不包含格式）
        String name = originalFileName.substring(0, originalFileName.lastIndexOf("."));

        //设置文件新名称: 当前时间+文件名称（不包含格式）
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sdf.format(d);
        String fileName = date + "_"+ name + "." + type;

        //在指定路径下创建一个文件
        File targetFile = new File(path, fileName);
            //文件上传
            //base64编码处理
            place.setPicpath(Base64Utils.encodeToString(pic.getBytes()));

            pic.transferTo(targetFile);
            //保存place对象
            placeService.save(place);
            result.setMsg("保存景点信息成功!!!");
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(false).setMsg(e.getMessage());
        }

        return result;



    }


    /**
     * 根据省份id查询景点的方法
     */
    @GetMapping("findAllPlace")
    public Map<String, Object> findAllPlace(Integer page, Integer rows, String provinceId) {
        HashMap<String, Object> result = new HashMap<>();
        page = page == null ? 1 : page;
        rows = rows == null ? 3 : rows;
        //景点集合
        List<Place> places = placeService.findByProvinceIdPage(page, rows, provinceId);
        //处理分页
        Integer counts = placeService.findByProvinceIdCounts(provinceId);
        //总页数
        Integer totalPage = counts % rows == 0 ? counts / rows : counts / rows + 1;
        result.put("places", places);
        result.put("page", page);
        result.put("counts", counts);
        result.put("totalPage", totalPage);
        return result;
    }
}
