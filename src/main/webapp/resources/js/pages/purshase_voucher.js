

function disabelButton( boolean){
	
	if(boolean){
		
		$('.btn').addClass(
		'disabled');
		
		  var elem = document.getElementById('excel');
		  elem.classList.remove('disabled');
		
		  
		
		  
		allBtn = 	document.getElementsByClassName('btn');

		for(var i = 0; i < 	allBtn.length; i++)
		{
		
			
			allBtn[i].style.cursor = "not-allowed" ;
		}
	
		  elem.style.cursor='pointer';
		
	}
	
}