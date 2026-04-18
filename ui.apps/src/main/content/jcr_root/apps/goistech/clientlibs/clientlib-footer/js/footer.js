(function () {
  'use strict';

  var FOOTER_SEL = '[data-cmp-is="footer"]';

  function init() {
    var footer = document.querySelector(FOOTER_SEL);
    if (!footer || footer.dataset.cmpInitialized) return;
    footer.dataset.cmpInitialized = 'true';

    // Animate social icons on hover with a subtle glow pulse
    var socialLinks = footer.querySelectorAll('.cmp-footer__social-link');
    socialLinks.forEach(function (link) {
      link.addEventListener('mouseenter', function () {
        link.style.transition =
          'border-color 0.25s, color 0.25s, background 0.25s, box-shadow 0.25s, transform 0.2s';
      });
    });

    // Update copyright year dynamically if it contains a year placeholder
    var copyright = footer.querySelector('.cmp-footer__copyright');
    if (copyright) {
      var currentYear = new Date().getFullYear();
      copyright.innerHTML = copyright.innerHTML.replace(/\b202[0-9]\b/g, currentYear);
    }
  }

  if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', init);
  } else {
    init();
  }
})();
