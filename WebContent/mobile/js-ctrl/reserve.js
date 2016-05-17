(function () {
    var that = me.define("reserve", {
        ctrl: function () {
        	//我的预约
            $.ajax({
            	url:"/wxServer/mobile/manage/act/query/mybooking",
            	success:function(data){
            		if(data==null||"null"==data){
            			alert("您还没有关注过任何活动") ;
            		}else{
            			var superDiv = $('.my-interest .my-reserve') ;
            			superDiv.html('');
            			var json = JSON.parse(data) ;
            			var size = json.length ;
            			for(var i = 0; i < size ;i++){
            				var time = json[i].time ;
            				var title = json[i].title ;
            				var content = "<li id='" + title + "'>"
            					+"<div class='icon'><i class='fa fa-check-circle'></i></div>"
            					+"<div class='reserve-condition'>"
            					+"<h3>"+time
            					+"</h3>"
            					+"<p>"+title
            					+"</p>"
            					+"</div>"
            					+"</li>"
            				
            				superDiv.append(content) ;
            			}
            		}
            	}
            })
        },
        reserve_detail:function(){
            me.show("reserve_detail", {
                showType: 0
            });
        },
    });
})();