$(document).ready(() => {
   $(document).on('click', '.delete-product', function(e) {
      e.preventDefault();
      $(this).siblings('form').submit();
   });
});