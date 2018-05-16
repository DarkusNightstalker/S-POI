/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var SpecificActivity = function () {
    return {
        save: function () {
            if ($("#form").valid()) {
                save();
            }
        },
        configSP: function () {
            $('#form\\:strategic-plan').select2({
                placeholder: 'Seleccione un plan estrategico',
                width: '100%'
            });
        },
        configSA: function () {
            $('#form\\:strategic-axis').select2({
                placeholder: 'Seleccione un eje estrategico',
                width: '100%'
            });
        },
        configSPG: function () {
            $('#form\\:specific-goal').select2({
                placeholder: 'Seleccione un objetivo especifico',
                width: '100%'
            });
        },
        configSTG: function () {
            $('#form\\:strategic-goal').select2({
                placeholder: 'Seleccione un objetivo estrategico',
                width: '100%'
            });
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

    $("#form").validate({
        errorClass: "has-error text-danger",
        validClass: "has-success",
        errorElement: "em",
        rules: {
            "form:code": {
                required: true
            },
            "form:name": {
                required: true
            },
            "form:strategic-plan": {
                required: true
            },
            "form:strategic-axis": {
                required: true
            },
            "form:specific-goal": {
                required: true
            }
        },
        messages: {
            "form:code": {
                required: 'Por favor ingrese un codigo'
            },
            "form:name": {
                required: 'Por favor ingrese un nombre'
            },
            "form:strategic-plan": {
                required: 'Por favor seleccione un plan estrategico'
            },
            "form:strategic-axis": {
                required: 'Por favor seleccione un eje estrategico'
            },
            "form:specific-goal": {
                required: 'Por favor seleccione un objetivo especifico'
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

