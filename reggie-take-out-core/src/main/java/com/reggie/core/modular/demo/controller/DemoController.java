package com.reggie.core.modular.demo.controller;

import com.reggie.common.model.HttpResult;
import com.reggie.core.modular.common.convert.CommonConvert;
import com.reggie.core.modular.common.model.request.BaseQueryRequest;
import com.reggie.core.modular.common.model.vo.PageVO;
import com.reggie.core.modular.demo.model.request.DemoQueryRequest;
import com.reggie.core.modular.demo.model.request.DemoRequest;
import com.reggie.core.modular.demo.model.response.DemoResponse;
import com.reggie.core.modular.demo.service.DemoService;
import com.reggie.core.util.JavaBeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Api(tags = "Demo模块-数据管理Demo接口")
@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/demoModule/demo")
public class DemoController {

    private final DemoService demoService;

    @ApiOperation(value = "分页查询数据Demo接口")
    @GetMapping("/listPage")
    public HttpResult<PageVO<DemoResponse>> listPage(@Validated(BaseQueryRequest.ListPage.class)
                                                             DemoQueryRequest request) {
        return HttpResult.success(CommonConvert.convertPageToPageVO(demoService.listPage(request), DemoResponse.class));
    }

    @ApiOperation(value = "查询数据详情Demo接口")
    @GetMapping("/get")
    public HttpResult<DemoResponse> get(@RequestParam @NotBlank String id) {
        return HttpResult.success(JavaBeanUtils.map(demoService.get(id), DemoResponse.class));
    }

    @ApiOperation(value = "新增数据Demo接口")
    @PostMapping("/add")
    public HttpResult<Void> add(@RequestBody @Validated DemoRequest request) {
        demoService.add(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "修改数据Demo接口")
    @PostMapping("/edit")
    public HttpResult<Void> edit(@RequestBody @Validated(DemoRequest.Edit.class)
                                         DemoRequest request) {
        demoService.edit(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "删除数据Demo接口")
    @PostMapping("/delete")
    public HttpResult<Void> delete(@RequestBody @NotEmpty List<String> idList) {
        demoService.delete(idList);
        return HttpResult.success();
    }

}
