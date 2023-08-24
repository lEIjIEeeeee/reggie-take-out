package com.reggie.core.modular.dish.helper;

import com.reggie.core.modular.dish.manager.CatManager;
import com.reggie.core.modular.dish.model.entity.Cat;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CatHelper {

    private final CatManager catManager;

    public void fillCatInfo(Cat cat) {
        if (Cat.TREE_ROOT_ID.equals(cat.getPid())) {
            cat.setLevel(1);
        } else {
            Cat pCat = catManager.getByIdWithExp(cat.getPid());
            cat.setLevel(pCat.getLevel() + 1);
        }
    }

}
