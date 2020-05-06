$(document).ready(() => {
    $(document).on('click', '.approve-review', function() {
        $(this).siblings('form').submit();


    });
});