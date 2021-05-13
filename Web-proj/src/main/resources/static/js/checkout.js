$(document).ready(function () {

  if (Cookies.get('City') != null) {
      $('#myBtn').text(Cookies.get('City'));
  }

  $(".delbutton").click(function() {
      $.ajax({
          contentType: 'application/String',
          url: '/deletProduct',
          data: {
              "idProduct": $(this).attr('id')
          },
          dataType: 'html',
          success: function(result) {
              $('#Sorti').html(result);
          }
      });
  });

  $(".addbutton").click(function() {
        $.ajax({
            contentType: 'application/String',
            url: '/addProduct',
            data: {
                "idProduct": $(this).attr('id')
            },
            dataType: 'html',
            success: function(result) {
                $('#Sorti').html(result);
            }
        });
  });

  $(".cellbutton").click(function() {
    $.ajax({
        contentType: 'application/String',
        url: '/cellProduct',
        dataType: 'html',
        success: function(result) {
            $('#Sorti').html(result);
        }
    });
  });


});
