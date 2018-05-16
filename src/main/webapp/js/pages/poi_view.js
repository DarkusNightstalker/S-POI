
var POIView = function () {
    var lastReportElement, lastBg, lastFg;
    return {
        delete: function (name) {
            $.SmartMessageBox({
                title: "Aviso!",
                content: "¿ Esta seguro de eliminar la actividad '" + name + "' ?",
                buttons: '[No][Si]'
            }, function (ButtonPressed) {
                if (ButtonPressed === "Si") {
                    del();
                }
            });
        },
        recovery: function (name) {
            $.SmartMessageBox({
                title: "Aviso!",
                content: "Esta seguro de restaurar la actividad POI '" + name + "'",
                buttons: '[No][Si]'
            }, function (ButtonPressed) {
                if (ButtonPressed === "Si") {
                    recovery();
                }
            });
        },
        saveManage: function () {
            if ($("#form-manage").valid()) {
                $("#btn-save-manage").prop("disabled", true).find("i").removeClass("hidden")
                saveManage();
                $("#form-manage\\:spg").prop("disabled", true)
                $("#form-manage\\:specific-activity").prop("disabled", true)
                $("#form-manage\\:activity-budge-program").prop("disabled", true)
                $("#form-manage\\:detail-act").prop("disabled", true)
                $("#form-manage\\:poi-unity").prop("disabled", true)
            }
        },
        endSaveManage: function () {
            $("#form-manage\\:spg").prop("disabled", false)
            $("#form-manage\\:specific-activity").prop("disabled", false)
            $("#form-manage\\:activity-budge-program").prop("disabled", false)
            $("#form-manage\\:detail-act").prop("disabled", false)
            $("#form-manage\\:poi-unity").prop("disabled", false)
            $("#btn-save-manage").prop("disabled", false).find("i").addClass("hidden")
            if (!document.getElementById('form-manage:create-again').checked) {
                $('#modal-manage').modal('hide')
            }
        },
        configureManageForm: function () {
            $("#form-manage").validate({
                errorClass: "has-error text-danger",
                validClass: "has-success",
                errorElement: "em",
                rules: {
                    "form-manage:spg": {
                        required: true
                    },
                    "form-manage:activity-budge-program": {
                        required: true
                    },
                    "form-manage:specific-activity": {
                        required: true
                    },
                    "form-manage:detail-act": {
                        required: true
                    },
                    "form-manage:poi-unity": {
                        required: true
                    },
                    "form-manage:priority": {
                        required: true
                    }
                },
                messages: {
                    "form-manage:spg": {
                        required: "Se necesita un objetivo especifico"
                    },
                    "form-manage:activity-budge-program": {
                        required: "Se necesita una actividad presupuestal"
                    },
                    "form-manage:specific-activity": {
                        required: "Se necesita una estrategia"
                    },
                    "form-manage:detail-act": {
                        required: "Se necesita el detalle"
                    },
                    "form-manage:poi-unity": {
                        required: "Se necesita una unidad"
                    },
                    "form-manage:priority": {
                        required: "Se necesita establecer prioridad"
                    }
                },
                /* @validation highlighting + error placement  
                 ---------------------------------------------------- */

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
                    if (element.hasClass("select2-offscreen")) {
                        error.insertAfter(element.closest(".input-group"));
                    } else {
                        error.insertAfter(element);
                    }
                }

            });
        }
    };
}();

$(function () {
});

