/*
 * ShariaFunds design system — shell behaviour that complements the Metrica theme scripts (app.js):
 *  - remembers the collapsed sidebar on desktop (app.js toggles body.enlarge-menu),
 *  - closes the drawer from the backdrop or the Escape key on smaller screens,
 *  - shows a menu panel when the current URL matches no menu item (e.g. the application root).
 */
(function ($) {
	'use strict';

	var STORAGE_KEY = 'sf.sidebarCollapsed';
	var DESKTOP = window.matchMedia('(min-width: 1025px)');

	function readCollapsed() {
		try {
			return window.localStorage.getItem(STORAGE_KEY) === '1';
		} catch (e) {
			return false;
		}
	}

	function writeCollapsed(collapsed) {
		try {
			window.localStorage.setItem(STORAGE_KEY, collapsed ? '1' : '0');
		} catch (e) {
			/* storage unavailable (private mode): the preference simply is not remembered */
		}
	}

	function syncToggleState() {
		$('.button-menu-mobile').attr('aria-expanded', String(!$('body').hasClass('enlarge-menu')));
	}

	function collapse() {
		$('body').addClass('enlarge-menu');
		syncToggleState();
	}

	function ensureActivePane() {
		if ($('.main-icon-menu-pane.active').length) {
			return;
		}
		var $first = $('.main-icon-menu .nav-link').first();
		if ($first.length) {
			$first.addClass('active');
			$($first.attr('href')).addClass('active');
		}
		$('.main-menu-inner').addClass('active');
	}

	$(function () {
		if (DESKTOP.matches && readCollapsed()) {
			$('body').addClass('enlarge-menu');
		}
		syncToggleState();
		ensureActivePane();

		// app.js toggles the class on the same click; read the result afterwards
		$('.button-menu-mobile').on('click', function () {
			window.setTimeout(function () {
				syncToggleState();
				if (DESKTOP.matches) {
					writeCollapsed($('body').hasClass('enlarge-menu'));
				}
			}, 0);
		});

		$('.sf-backdrop').on('click', collapse);

		$(document).on('keydown', function (e) {
			if (e.key === 'Escape' && !DESKTOP.matches && !$('body').hasClass('enlarge-menu')) {
				collapse();
			}
		});
	});
})(jQuery);
