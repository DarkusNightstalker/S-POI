
var BP = function () {
    return {
        save: function () {
            if ($("#form").valid()) {
                save();
            }
        },
        back: function () {
            if ($("#form\\:code").val() != "" || $("#form\\:name").val() != "") {
                $.SmartMessageBox({
                    title: "Aviso!",
                    content: "Esta seguro de salir? (Se perderan todos los cambios realizados)",
                    buttons: '[No][Si]'
                }, function (ButtonPressed) {
                    if (ButtonPressed === "Si") {
                        $("#form\\:back").trigger("click");
                    }
                });
            } else {
                $("#form\\:back").trigger("click");
            }
        }
    };
}();

$(function () {
    $("#form input").on("keypress", function (event) {
        if (event.charCode == 13) {
            BP.save();
            return false;
        }
    });
    $("#form\\:code").focus();
    $("#form").validate({
        /* @validation states + elements 
         ------------------------------------------- */
        errorClass: "has-error text-danger",
        validClass: "has-success",
        errorElement: "em",
        /* @validation rules 
         ------------------------------------------ */
        rules: {
            "form:code": {
                required: true,
                minlength: 4,
                maxlength: 10
            },
            "form:name": {
                required: true
            }
        },
        /* @validation error messages 
         ---------------------------------------------- */

        messages: {
            "form:code": {
                required: 'Por favor ingrese el codigo',
                minlength: "El tamaño minimo del codigo es 4",
                maxlength: "El tamaño maximo del codigo es 10"
            },
            "form:name": {
                required: "Por favor ingrese el nombre"
            }
        },
        /* @validation highlighting + error placement  
         ---------------------------------------------------- */

        highlight: function (element, errorClass, validClass) {
            $(element).closest('.form-group').addClass(errorClass).removeClass(validClass);
        },
        unhighlight: function (element, errorClass, validClass) {
            $(element).closest('.form-group').removeClass(errorClass).addClass(validClass);
        },
        errorPlacement: function (error, element) {
            element.parent().remove(".has-error");
            error.insertAfter(element);
        }

    });
})

