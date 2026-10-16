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

	function pathOf(href) {
		return (href || '').split(/[?#;]/)[0];
	}

	/* app.js compares full URLs; also match on the path alone (query strings, session ids ignored) */
	function matchCurrentPage() {
		if ($('.main-menu-inner .nav-link.active').length) {
			return;
		}
		var current = pathOf(window.location.href);
		var $links = $('.main-menu-inner .nav-link');
		var $link = $links.filter(function () {
			return pathOf(this.href) === current;
		}).first();
		if (!$link.length) {
			// pages without their own menu entry (e.g. worker-card): the visible entry of the same folder
			var folder = current.replace(/[^/]*$/, '');
			$link = $links.filter(function () {
				return $(this).css('visibility') !== 'hidden' && pathOf(this.href).replace(/[^/]*$/, '') === folder;
			}).first();
		}
		if (!$link.length) {
			return;
		}
		$link.addClass('active');
		var $pane = $link.closest('.main-icon-menu-pane').addClass('active');
		$pane.siblings().removeClass('active');
		$('.main-menu-inner').addClass('active');
		$('.main-icon-menu .nav-link').removeClass('active')
			.filter('[href="#' + $pane.attr('id') + '"]').addClass('active');
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

	/*
	 * Detail pages (e.g. deserved-card) match an invisible placeholder entry of the menu, kept there so the
	 * right group opens. Also mark the visible entry of the same section (same folder) as the current page.
	 */
	function highlightSectionOfDetailPage() {
		var $hidden = $('.main-menu-inner .nav-link.active').filter(function () {
			return $(this).css('visibility') === 'hidden';
		});
		if (!$hidden.length) {
			return;
		}
		var folder = function (href) {
			return pathOf(href).replace(/[^/]*$/, '');
		};
		var target = folder($hidden[0].href);
		$hidden.closest('.main-icon-menu-pane').find('.nav-link').filter(function () {
			return $(this).css('visibility') !== 'hidden' && folder(this.href) === target;
		}).first().addClass('active').attr('aria-current', 'page');
	}

	function markCurrentPage() {
		$('.main-menu-inner .nav-link.active').filter(function () {
			return $(this).css('visibility') !== 'hidden';
		}).attr('aria-current', 'page');
	}

	$(function () {
		matchCurrentPage();
		highlightSectionOfDetailPage();
		markCurrentPage();
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
