package com.reggie.core.context;

import com.reggie.core.modular.auth.model.dto.LoginUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Context {

    private LoginUser currentUser;

}
