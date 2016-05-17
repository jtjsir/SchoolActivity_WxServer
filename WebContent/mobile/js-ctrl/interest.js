(function () {
    var that = me.define("interest", {
        ctrl: function () {
        	//我的关注
            $.ajax({
            	url:"/wxServer/mobile/manage/act/query/mynotice",
            	success:function(data){
            		if(data==null||"null"==data){
            			alert("您还没有关注过任何活动") ;
            		}else{
            			var superDiv = $('.my-interest .interest') ;
            			superDiv.html('');
            			var json = JSON.parse(data) ;
            			var size = json.length ;
            			for(var i = 0; i < size ;i++){
            				var time = json[i].time ;
            				var title = json[i].title ;
            				var content = "<li id='" + title + "'>"
            					+"<div>"
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

        activity_detail:function(){
            me.show("activity_detail", {
                showType: 0
            });
        },

    });
    
    //关注
    $('#menu3 .mine')
})();