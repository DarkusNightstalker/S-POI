
var NecessaryItem = function () {
    return {
        save: function () {
            if ($("#form").valid()) {
                save();
            }
        },
        back: function () {
            if ($("#form\\:code").val() != "" || $("#form\\:name").val() != "" || $("#form\\:generic-classifier").val() != "" || $("#form\\:price").val() != "" || $("#form\\:uom").val() != "") {
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
            NecessaryItem.save();
            return false;
        }
    });
    //$("#form\\:price").numeric();
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
            "form:name": {
                required: true
            },
            "form:code": {
                required: true
            }
        },
        /* @validation error messages 
         ---------------------------------------------- */

        messages: {
            "form:name": {
                required: 'Por favor ingrese un nombre'
            },
            "form:code": {
                required: 'Por favor ingrese un código'
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

