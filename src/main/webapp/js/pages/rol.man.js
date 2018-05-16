/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var Rol = function () {
    return {
        save: function () {
            if ($("#form").valid()) {
                savePermission();
            }
        },
        back: function () {
            if ($("#form\\:code").val() != "" || $("#form\\:name").val() != "") {
                $.SmartMessageBox({
                    title: "<i class='fa fa-refresh' style='color:green'></i> Aviso!",
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
            Rol.save();
            return false;
        }
    });
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

