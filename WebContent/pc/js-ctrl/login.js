(function(){
	$('.login_button .button').on('click',function(){
		var username = $('login_block input[name="username"]').val() ;
		var password = $('login_block input[name="password"]').val() ;
		var requestUrl = "/wxServer/pc/admin/user/login" ;
		
		$('.login_block').attr('action',requestUrl).submit() ;
	});
})()