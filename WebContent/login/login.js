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
	
	$(document).ready(function()
	{
		$('#btLogin').click(function()
		{
			$('#divError').fadeToggle('slow');
		});
		
		$('button.close').on('click', function(){
			$('#divError').hide(300);
		});
	});
});

function erroLogin(data)
{
	if(data.status == 'begin')
	{
		alert('ssss');
	}
}

	
