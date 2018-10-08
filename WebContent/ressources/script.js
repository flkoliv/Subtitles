/**
 * 
 */
// ===== Scroll to Top ==== 
//$(window).scroll(function() {
  //  if ($(this).scrollTop() >= 50) {        // If page is scrolled more than 50px
    //    $('#return-to-top').fadeIn(200);    // Fade in the arrow
    //} else {
      //  $('#return-to-top').fadeOut(200);   // Else fade out the arrow
    //}
//});
$("#return-to-top").click(function() {      // When arrow is clicked
    $("body,html").animate({
        scrollTop : 0                       // Scroll to top of body
    }, 500);
});

setTimeout(function(){
	$('#message').fadeOut();
	//$('#message').text("");
}, 5000);


console.log($($('#listeFilms').contents().length));
console.log($($('#listeFilms').contents()));
if ($('#msg').text().length != 0)$('#message').show();
if ($('#msg').text().substr($('#msg').length - 2) ==="!") {
	$('#message').css("background-color", "#FFB6B8");
}
if ($('#listeFilms').contents().length <= 1)$('#choix').hide();


	

var inputs = document.querySelectorAll( '.inputfile' );
Array.prototype.forEach.call( inputs, function( input )
{
	var label	 = input.nextElementSibling,
		labelVal = label.innerHTML;

	input.addEventListener( 'change', function( e )
	{
		var fileName = '';
		if( this.files && this.files.length > 1 )
			fileName = ( this.getAttribute( 'data-multiple-caption' ) || '' ).replace( '{count}', this.files.length );
		else
			fileName = e.target.value.split( '\\' ).pop();

		if( fileName )
			label.querySelector( 'span' ).innerHTML = fileName;
		else
			label.innerHTML = labelVal;
	});
});