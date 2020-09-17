(function($) {

	"use strict";

	var fullHeight = function() {

		$('.js-fullheight').css('height', $(window).height());
		$(window).resize(function(){
			$('.js-fullheight').css('height', $(window).height());
		});

	};
	fullHeight();

	$('.expandable-sidebar__sidebar-collapse').on('click', function () {
      $('.expandable-sidebar').toggleClass('active');
  });

})(jQuery);
