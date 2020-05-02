$(document).ready(() => {
    $(document).on('click', '.delete-category', function(e) {
        $(this).find('form').submit();
    })
});