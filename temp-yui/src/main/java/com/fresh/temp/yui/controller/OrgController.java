package com.fresh.temp.yui.controller;

import com.fresh.common.result.JsonResult;
import com.fresh.common.utils.AssertUtils;
import com.fresh.xy.mb.utils.IdGenerator;
import com.fresh.temp.yui.dto.OrgAddDto;
import com.fresh.temp.yui.dto.OrgUpdateDto;
import com.fresh.temp.yui.entity.Org;
import com.fresh.temp.yui.service.OrgService;
import com.fresh.temp.yui.vo.OrgVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequestMapping("org")
public class OrgController {

    @Autowired
    private OrgService<Long, Org> orgService;

    @GetMapping("getById")
    public JsonResult<OrgVo> getById(@RequestParam("id") Long id) {
        //AssertUtils.notNull(id, () -> "id不能为空", null);
        Org org = orgService.getById(id);
        AssertUtils.notNull(org, () -> "id[" + id + "]不存在", () -> "org_0001");

        OrgVo orgVo = new OrgVo();
        orgVo.setId(org.getId());
        orgVo.setOrgName(org.getOrgName());
        orgVo.setOrgCommissionMoney(org.getOrgCommissionMoney());
        orgVo.setCreate_time(org.getCreateTime());
        orgVo.setModify_time(org.getModifyTime());
        return JsonResult.buildSuccessResult(orgVo);
    }

    @PostMapping("add")
    public JsonResult<?> add(@RequestBody @Valid OrgAddDto orgDto) {
        //check orgCommissionMoney 的整数部分不超过9位，小数部分不超过2位
        checkDecimal(orgDto.getOrgCommissionMoney());

        Org org = new Org();
        org.setId(IdGenerator.timestamp());
        org.setOrgName(orgDto.getOrgName());
        org.setOrgCommissionMoney(orgDto.getOrgCommissionMoney());

        orgService.save(org);
        return JsonResult.buildSuccessResult("添加成功");
    }

    private void checkDecimal(BigDecimal bd) {
        String[] sps = bd.toString().split("\\.");
        AssertUtils.isTrue(sps[0].length() <= 9, () -> "orgCommissionMoney out of range", null);
        if(sps.length > 1) {
            AssertUtils.isTrue(sps[1].length() <= 2, () -> "orgCommissionMoney out of range", null);
        }
    }

    @PostMapping("updateById")
    public JsonResult<?> updateById(@RequestBody @Valid OrgUpdateDto orgDto) {
        if(orgDto.getOrgCommissionMoney() != null)
            //check orgCommissionMoney 的整数部分不超过9位，小数部分不超过2位
            checkDecimal(orgDto.getOrgCommissionMoney());

        Org org = new Org();
        org.setId(orgDto.getId());
        org.setOrgName(orgDto.getOrgName());
        org.setOrgCommissionMoney(orgDto.getOrgCommissionMoney());

        orgService.updateById(org);
        return JsonResult.buildSuccessResult("更新成功");
    }

    @PostMapping("deleteById")
    public JsonResult<?> deleteById(@RequestBody @Valid OrgUpdateDto orgDto) {
        orgService.removeById(orgDto.getId());

        return JsonResult.buildSuccessResult("删除成功");
    }

    @PostMapping("deleteOrg")
    public JsonResult<?> deleteOrg(@RequestBody @Valid OrgUpdateDto orgDto) {
        orgService.deleteOrg(orgDto.getId());

        return JsonResult.buildSuccessResult("删除成功");
    }

}
