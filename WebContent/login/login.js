window.jQuery || document.write("<script src='../assets/js/jquery.min.js'>"+"<"+"/script>");
window.jQuery || document.write("<script src='../assets/js/jquery1x.min.js'>"+"<"+"/script>");

jQuery(function($) 
{
	$(document).on('click', '.toolbar a[data-target]', function(e) 
	{
		e.preventDefault();
		var target = $(this).data('target');
		$('.widget-box.visible').removeClass('visible');//hide others
		$(target).addClass('visible');//show target
    });
});

function erroLogin(data)
{
	if(data.status == 'begin')
	{
		alert('ssss');
	}
	else if(data.status == 'sucess')
	{
		alert('ssss');
	}
}

	
