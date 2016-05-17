(function () {
    var that = me.define("menu2", {
        ctrl: function () {
            that.reserve();
        },
        reserve: function () {
            $.ajax({
                url: "/wxServer/mobile/manage/act/query/advance",
                type: "get",
                success: function (data) {
                	if(null==data||"null"==data){
                		alert("没有相应的数据！") ;
                	}else{
                    var act = $("#menu2 .activity");
                    act.html('') ;
                    var json = JSON.parse(data);
                    var size = json.length;
                    for (var i = 0; i < size; i++) {
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
                        var actdata = " <li id='" + title +"'>"
                            + "<div class='reserve-detail'>"
                            + " <img src='" + img + "'>"
                            + "  <div class='activity-tittle'><h4>"
                            + title
                            + "</h4></div>"
                            + "<div class='follow red'><span><i class='fa fa-heart-o'></i>120</span></div>"
                            + "</div>"
                            + "</li>"
                        act.append(actdata);
                    	}
                	}
                }
            });

        }

    });
})();
