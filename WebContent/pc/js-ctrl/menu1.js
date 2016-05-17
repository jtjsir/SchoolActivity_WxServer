(function () {
    var that = me.define("menu1", {
        ctrl: function () {
        	loadAllUser() ;
        }
    });
    
    var loadAllUser = function(){
    	$.ajax({
    		url:"/wxServer/pc/manage/admin/query/all",
    		success:function(data){
    			if(data==null||data=="null"){
    				$('.manage_list').html('没有管理员数据') ;
    			}else{
    				decodeJson(data) ;
    			}
    		}
    	});
    } 
    
    var decodeJson = function(data){
    	var json = JSON.parse(data) ;
    	var str = "<tr>" + "<th>用户名</th><th>学号</th><th>联系方式</th><th>删除</th><th>修改</th>"+ "</tr>" ;
    	var appendNode = $('.manage_list') ;
    	appendNode.html('') ;
    	appendNode.append(str) ;
    	var len = json.length ;
    	for(var i = 0; i <len; i++){
    		var appendStr = "<tr>" 
    			+"<td class='adminname'>" + json[i].name + "</td>"
    			+"<td>" + json[i].lno + "</td>"
    			+"<td>" + json[i].phone + "</td>"
    			+"<td class='delete'><a>删除</a></td>"
    			+"<td class='update'><a>修改</a></td>"
    			+"</tr>";
    		appendNode.append(appendStr);
    	}
    }
    
  //搜索
    $(document).on('click','.search_right .button',function(){
    	var adminname = $('.search_right .text').val() ;
    	$.ajax({
    		url:"/wxServer/pc/manage/admin/query/some",
    		data:{
    			searchname:adminname
    		},
    		success:function(data){
    			if(data==null||"null"==data){
    				console.log('没有对应的用户数据') ;
    				$('.message_list').html('没有对应的用户数据~~~')
    			}else{
    				decodeJson(data) ;
    			}
    		}
    	});
    });
    
    $(document).on('click','.manage_list .delete',function(){
    	var adminname = $(this).parent().children(".adminname").text() ;
    	$(this).parent().attr('hidden',true) ;
    	$.ajax({
    		url:"/wxServer/pc/manage/admin/delete",
    		data:{
    			name:adminname
    		},
    		success:function(){
    			alert("删除 " + adminname + " 用户成功") ;
    		}
    	});
    });
})();