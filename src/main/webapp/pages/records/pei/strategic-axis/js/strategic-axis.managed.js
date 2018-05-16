/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var StrategicAxis = function () {
    return {
        save: function () {
            if ($("#form").valid()) {
                save();
            }
        },
        back: function () {
            if ($("#form\\:code").val() != "" || $("#form\\:name").val() != "" || $("#form\\:strategic-plan").val() != "") {
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

    $('#form\\:strategic-plan').select2({
        placeholder: 'Seleccione un plan estrategico',
        width: '100%'
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
            "form:code": {
                required: true
            },
            "form:name": {
                required: true
            },
            "form:description": {
                required: true
            },
            "form:strategic-plan": {
                required: true
            }
        },
        /* @validation error messages 
         ---------------------------------------------- */

        messages: {
            "form:code": {
                required: 'Por favor ingrese un codigo'
            },
            "form:name": {
                required: 'Por favor ingrese un nombre'
            },
            "form:description": {
                required: 'Por favor ingrese una descripción'
            },
            "form:strategic-plan": {
                required: 'Por favor seleccione un plan estrategico'
            }
        },
        highlight: function (element, errorClass, validClass) {
            $(element).closest('.form-group').addClass(errorClass).removeClass(validClass);
            if ($(element).hasClass("select2-offscreen")) {
                $("#s2id_" + $(element).attr("id").replace(":", "\\:") + " ul").addClass(errorClass);
            } else {
                $(element).addClass(errorClass);
            }
        },
        unhighlight: function (element, errorClass, validClass) {
            $(element).closest('.form-group').removeClass(errorClass).addClass(validClass);
            if ($(element).hasClass("select2-offscreen")) {
                $("#s2id_" + $(element).attr("id").replace(":", "\\:") + " ul").removeClass(errorClass).addClass(validClass);
            } else {
                $(element).removeClass(errorClass);
            }
        },
        errorPlacement: function (error, element) {
            error.insertAfter(element);
        }
    });
});

