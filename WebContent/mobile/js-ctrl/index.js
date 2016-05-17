(function () {
    var that = me.define("index", {
        ctrl: function () {
            that.changeMenu(0);
        },

        changeMenu: function(menuIndex){
            that.$scope.menuIndex = menuIndex;

            switch(menuIndex){
                case 0:
                    me.show("menu1", {
                        showType: 0
                    });
                    break;
                case 1:
                    me.show("menu2", {
                        showType: 0
                    });
                    break;
                case 2:
                    me.show("menu3", {
                        showType: 0
                    });
                    break;
            }
        }
    });
    
    $(document).on("click",".menu .activity-type li a",function(){
    	var type = $(this).text() ;
    	$.ajax({
    		url:"/wxServer/mobile/manage/act/query/type",
    		data:{
    			act_type:type
    		},
    		success:function(data){
    			if(data=="null"||data==null){
    				alert("查询到的数据为空！")
    			}else{
    				var act = $("#menu1 .activity") ;
    				act.html('') ;
                    var json = JSON.parse(data);
                    var size = json.length;
                    for(var i = 0;i<size;i++){
                        var id = json[i].id;
                        var title = json[i].title;
                        var content = json[i].content;
                        var img = json[i].url;
                        var address = json[i].address;
                        var time = json[i].time;
                        var type = json[i].type;
                        var status = json[i].status;
                        var people = json[i].people;
                        var limit = json[i].limit;
                        var actdata = " <li id='"+ title +"'>"
                            +" <div class='active-condition red'>"
                            +(status==0?"即将开始":(status==1?"正在进行":"已经结束"))
                            +"</div>"
                            +" <img src='" + img +"'>"
                            +"  <div class='activity-tittle'><h4>"
                            + title
                            +"</h4></div>"
                            +"<span class='detail'><i class='fa fa-calendar'></i> "
                            + time
                            + "</span></br>"
                            +" <span class='detail'><i class='fa fa-map-marker'></i>"
                            +address
                            +"</span>"
                            +"<div class='follow red'><span><i class='fa fa-heart-o'></i>120</span></div>"
                            + "</li>"
                        act.append(actdata);
                    }
    			}
    		}
    	});
    });
})();