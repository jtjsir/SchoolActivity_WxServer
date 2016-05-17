(function () {
    var that = me.define("menu3", {
        ctrl: function () {
        	bindSend();
        },

        interest:function(){
            me.show("interest", {
                showType: 0
            });
        },
        reserve:function(){
          me.show("reserve",{
              showType:0
          })
        },
        mine_history:function(){
            me.show("mine_history",{
                showType:0
            })
        }
    });
    
    var bindSend = function(){
    	var onlySave = '.savebtn .button_gray' ;
    	var saveAndSend = '.savebtn .button' ;
    	var actionPath = "/wxServer/pc/activity/add" ;
    	//只是保存
    	$(document).on('click',onlySave,function(){
    		actionPath = actionPath + "?isSend=0" ;
    		$('.activity_type').attr('action',actionPath).submit() ;
    	});
    	//保存并群发
    	$(document).on('click',saveAndSend,function(){
    		actionPath = actionPath + "?isSend=1" ;
    		$('.activity_type').attr('action',actionPath).submit() ;
    	});
    }
})();