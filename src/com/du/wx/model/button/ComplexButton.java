package com.du.wx.model.button;

import com.du.wx.model.common.Button;
/**
*父菜单项
* @author jing
*/
public class ComplexButton extends Button{
   private Button[] sub_button ;

   public Button[] getSub_button() {
       return sub_button;
   }

   public void setSub_button(Button[] sub_button) {
       this.sub_button = sub_button;
   }
   
   
}

