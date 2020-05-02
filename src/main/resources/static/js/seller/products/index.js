$(document).ready(() => {
   $(document).on('click', '.delete-product', function(e) {
      console.log("here");
      e.preventDefault();
      $(this).siblings('form').submit();
   });
});