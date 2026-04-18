(function () {
  'use strict';

  var SCROLL_THRESHOLD = 50;
  var HEADER_SEL      = '[data-cmp-is="header"]';
  var TOGGLE_SEL      = '.cmp-header__menu-toggle';
  var NAV_SEL         = '.cmp-header__nav';
  var SCROLLED_CLASS  = 'cmp-header--scrolled';
  var OPEN_CLASS      = 'is-open';

  function init() {
    var header = document.querySelector(HEADER_SEL);
    if (!header || header.dataset.cmpInitialized) return;
    header.dataset.cmpInitialized = 'true';

    var toggle = header.querySelector(TOGGLE_SEL);
    var nav    = header.querySelector(NAV_SEL);

    // ── Sticky scroll class ──────────────────────────────────────
    function onScroll() {
      if (window.scrollY > SCROLL_THRESHOLD) {
        header.classList.add(SCROLLED_CLASS);
      } else {
        header.classList.remove(SCROLLED_CLASS);
      }
    }

    window.addEventListener('scroll', onScroll, { passive: true });
    onScroll();

    // ── Mobile menu toggle ───────────────────────────────────────
    if (!toggle || !nav) return;

    function openMenu() {
      nav.classList.add(OPEN_CLASS);
      toggle.setAttribute('aria-expanded', 'true');
      document.body.style.overflow = '';
    }

    function closeMenu() {
      nav.classList.remove(OPEN_CLASS);
      toggle.setAttribute('aria-expanded', 'false');
    }

    toggle.addEventListener('click', function () {
      var isOpen = toggle.getAttribute('aria-expanded') === 'true';
      if (isOpen) {
        closeMenu();
      } else {
        openMenu();
      }
    });

    // Close on Escape
    document.addEventListener('keydown', function (e) {
      if (e.key === 'Escape') closeMenu();
    });

    // Close on outside click
    document.addEventListener('click', function (e) {
      if (!header.contains(e.target)) closeMenu();
    });

    // Close when a nav link is clicked (SPA navigation)
    nav.addEventListener('click', function (e) {
      if (e.target.closest('.cmp-navigation__item-link')) {
        closeMenu();
      }
    });
  }

  if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', init);
  } else {
    init();
  }
})();
