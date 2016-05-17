package com.du.wx.model;
/**
*
* @author jing
*/
public class Item {
   private String Title ;
   private String Description ;
   //图片显示路径
   private String PicUrl ;
   //图片链接地址,供于跳转
   private String Url ;

   public String getTitle() {
       return Title;
   }

   public void setTitle(String Title) {
       this.Title = Title;
   }

   public String getDescription() {
       return Description==null?"JingSir's Home":Description;
   }

   public void setDescription(String Description) {
       this.Description = Description;
   }

   public String getPicUrl() {
       return PicUrl==null?"":PicUrl;
   }

   public void setPicUrl(String PicUrl) {
       this.PicUrl = PicUrl;
   }

   public String getUrl() {
       return Url==null ? "":Url;
   }

   public void setUrl(String Url) {
       this.Url = Url;
   }
   
   
}

