package com.reggie.core.modular.user.manager;

import cn.hutool.core.util.ObjectUtil;
import com.reggie.common.enums.HttpResultCode;
import com.reggie.common.exception.BizException;
import com.reggie.core.modular.user.dao.MemberMapper;
import com.reggie.core.modular.user.model.entity.Member;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MemberManager {

    private final MemberMapper memberMapper;

    public Member getMemberById(String id) {
        return memberMapper.selectById(id);
    }

    public Member getMemberByIdWithExp(String id) {
        Member member = memberMapper.selectById(id);
        if (ObjectUtil.isNull(member)) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTED);
        }
        return member;
    }

}
