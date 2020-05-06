$(document).ready(() => {
    $(document).on('click', '.follow-toggler', function() {
        const url = $(this).data('url');
        $.ajax({
            url: url,
            method: 'patch',
            dataType: 'json',
            contentType: 'application/json',
            xhrFields: {
                withCredentials: true,
            },
            crossDomain: true,
            success: () => {
                $i = $(this).find('i')
                if ($i.hasClass('fa-user-plus')) {
                    $i.removeClass('fa-user-plus text-success');
                    $i.addClass('fa-user-minus text-danger');
                } else {
                    $i.removeClass('fa-user-minus text-danger');
                    $i.addClass('fa-user-plus text-success');
                }
            },

        })
    })
});