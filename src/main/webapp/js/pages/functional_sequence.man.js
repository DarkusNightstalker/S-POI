/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var FunctionalSequence = function () {
    return {
        save: function () {
            if ($("#fform").valid()) {
                $("#form\\:name").val($("#name").val());
                $("#form\\:code").val($("#code").val());
                $("#form\\:goal").val($("#goal").val());
                save();
            }
        },
        back: function () {
            if ($("#code").val() != "" || $("#name").val() != "" || $("#goal").val() != "") {
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
    $("#fform input").on("keypress", function (event) {
        if (event.charCode == 13) {
            FunctionalSequence.save();
            return false;
        }
    });$("#code").focus();
    $("#create-again").on("change", function () {
        $("#form\\:create-again").val(this.checked);
    })
    $("#fform").validate({
        /* @validation states + elements 
         ------------------------------------------- */
        errorClass: "has-error text-danger",
        validClass: "has-success",
        errorElement: "em",
        /* @validation rules 
         ------------------------------------------ */
        rules: {
            code :{
                required: true
            }
        },
        /* @validation error messages 
         ---------------------------------------------- */

        messages: {
            code: {
                required: 'El codigo no puede ser vacio'
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

