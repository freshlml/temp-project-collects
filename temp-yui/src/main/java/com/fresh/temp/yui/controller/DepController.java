package com.fresh.temp.yui.controller;

import com.fresh.common.result.JsonResult;
import com.fresh.common.result.PageJsonResultVo;
import com.fresh.common.utils.AssertUtils;
import com.fresh.xy.mb.core.Page;
import com.fresh.xy.mb.utils.IdGenerator;
import com.fresh.xy.mb.utils.PageUtils;
import com.fresh.temp.yui.dto.DepAddDto;
import com.fresh.temp.yui.dto.DepPageDto;
import com.fresh.temp.yui.dto.DepUpdateDto;
import com.fresh.temp.yui.entity.Dep;
import com.fresh.temp.yui.service.DepService;
import com.fresh.temp.yui.vo.DepVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("dep")
public class DepController {

    //请求和响应消息转换接口: HttpMessageConverter，Spring MVC 提供并配置了多种 Converter，如 MappingJackson2HttpMessageConverter 基于 Jackson 库，处理 application/json 类型‌,
    //                   ‌StringHttpMessageConverter 处理纯文本，如 text/plain，支持自定义字符集,
    //                   FormHttpMessageConverter 处理表单数据（application/x-www-form-urlencoded）和文件上传, XmlHttpMessageConverter 等
    //1、请求参数解析
    //   @RequestParam: 指定解析 url 后附带的参数: 使用 FormHttpMessageConverter（note: url 后附带的请求参数和表单页面生成的参数格式一致，均为"user=root&password=123456"格式）
    //   @RequestBody: 指定解析请求体中数据: 根据请求报文的 Content-Type 头匹配对应的 HttpMessageConverter（如 MappingJackson2HttpMessageConverter‌）来解析参数
    //2、响应
    //   默认情况下，使用 ViewResolver 渲染 html（或者其他种类视图），并将 html 写入响应体，同时设置响应头 Content-Type = text/html
    //   @ResponseBody: 使用 HttpMessageConverter 处理相应数据:
    //                  根据控制器方法的返回值类型和请求报文的 Accept 头匹配对应的 HttpMessageConverter，将返回值序列化为响应体，并设置响应头 Content-Type 为相应的值

    @Autowired
    private DepService depService;

    @PostMapping("add")
    public JsonResult<?> addDep(@RequestBody @Valid DepAddDto depDto) {
        AssertUtils.notNull(depDto.getDepType(), () -> "未传depType参数或者值不合法", null);

        Dep dep = new Dep();
        dep.setId(IdGenerator.timestamp());
        dep.setDepName(depDto.getDepName());
        dep.setDepNo(depDto.getDepNo());
        dep.setOrgId(depDto.getOrgId());
        dep.setDepType(depDto.getDepType());

        depService.save(dep);
        return JsonResult.buildSuccessResult("添加成功");
    }

    @PostMapping("updateById")
    public JsonResult<?> updateDep(@RequestBody @Valid DepUpdateDto depDto) {
        Dep dep = new Dep();
        dep.setId(depDto.getId());
        dep.setDepName(depDto.getDepName());
        dep.setDepNo(depDto.getDepNo());
        dep.setOrgId(depDto.getOrgId());
        dep.setDepType(depDto.getDepType());

        depService.updateById(dep);
        return JsonResult.buildSuccessResult("更新成功");
    }

    @PostMapping("deleteById")
    public JsonResult<?> deleteDep(@RequestBody @Valid DepUpdateDto depDto) {
        depService.removeById(depDto.getId());
        return JsonResult.buildSuccessResult("删除成功");
    }

    @GetMapping("getById")
    public JsonResult<DepVo> getById(@RequestParam("id") Long id) {
        Dep dep = depService.getById(id);
        AssertUtils.notNull(dep, () -> "id[" + id + "]不存在", () -> "dep_0001");

        DepVo depVo = new DepVo();
        depVo.setId(dep.getId());
        depVo.setDepName(dep.getDepName());
        depVo.setDepNo(dep.getDepNo());
        depVo.setOrgId(dep.getOrgId());
        depVo.setDepType(dep.getDepType());
        depVo.setCreateTime(dep.getCreateTime());
        depVo.setModifyTime(dep.getModifyTime());

        return JsonResult.buildSuccessResult(depVo);
    }

    @GetMapping("pageByEntity")
    public JsonResult<PageJsonResultVo<Dep>> pageByEntity(@RequestBody DepPageDto depPageDto) {
        AssertUtils.isTrue(depPageDto.getPageSize() >= 0, "pageSize参数不能小于0");

        Dep dep = new Dep();
        dep.setId(depPageDto.getId());
        dep.setDepName(depPageDto.getDepName());
        dep.setDepNo(depPageDto.getDepNo());
        dep.setOrgId(depPageDto.getOrgId());
        dep.setDepType(depPageDto.getDepType());
        Page<Dep> page = depService.pageByEntity(PageUtils.mybatisPage(depPageDto), dep);
        //page = depService.pageByEntityForyui(PageUtils.mybatisPage(depPageDto));

        return JsonResult.buildSuccessResult(PageUtils.pageJsonResultVo(page));
    }

}
