
var StrategicGoal = function () {
    return {
        save: function () {
            if ($("#form").valid()) {
                save();
            }
        },
        back: function () {
            if ($("#form\\:code").val() != "" || $("#form\\:name").val() != "" || $("#form\\:strategic-axis").val() != "") {
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

    $('#form\\:strategic-axis').select2({
        placeholder: 'Seleccione un eje estratégico',
        width: '100%'
    });
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
            "form:strategic-axis": {
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
            "form:strategic-axis": {
                required: 'Por favor seleccione un eje estrategico'
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

