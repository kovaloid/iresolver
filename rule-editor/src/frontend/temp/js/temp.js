
      $( function() {
        $( '#hide-file, #sortable2' ).sortable({
          connectWith: '.connectedSortable'
        }).disableSelection();
      } );

      $(document).ready(function(){
      $('#show').on('click', function () {
        var src = ($('#show').attr('src') === 'img/right.jpg')
            ? 'img/down.jpg'
            : 'img/right.jpg';
         $('#show').attr('src', src);
      });
    });

      $(document).ready(function(){
      $('#show1').on('click', function () {
        var src = ($('#show1').attr('src') === 'img/right.jpg')
            ? 'img/down.jpg'
            : 'img/right.jpg';
         $('#show1').attr('src', src);
      });
    });

      // Example starter JavaScript for disabling form submissions if there are invalid fields
      (function() {
        'use strict';

        window.addEventListener('load', function() {
          // Fetch all the forms we want to apply custom Bootstrap validation styles to
          var forms = document.getElementsByClassName('needs-validation');

          // Loop over them and prevent submission
          var validation = Array.prototype.filter.call(forms, function(form) {
            form.addEventListener('submit', function(event) {
              if (form.checkValidity() === false) {
                event.preventDefault();
                event.stopPropagation();
              }
              form.classList.add('was-validated');
            }, false);
          });
        }, false);
      })();
