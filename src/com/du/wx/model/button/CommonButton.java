package com.du.wx.model.button;

import com.du.wx.model.common.Button;

/**
 *普通button也称为子菜单
 * @author jing
 */
public class CommonButton extends Button{
    private String type ;
    private String key ;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    
    
}