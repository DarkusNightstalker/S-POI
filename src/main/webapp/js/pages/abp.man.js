/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var ABP = function () {

    return {
        create: function () {
            $("#form-search\\:create").click();
        },
        endSavedBudgetProgram: function () {
            if ($('#form-bp\\:saved').val() == 'true') {
                $('#modal-bp').modal('toggle');
            }
        },
        endSavedProductBudgetProgram: function () {
            if ($('#form-pbp\\:saved').val() == 'true') {
                $('#modal-pbp').modal('toggle');
            }
        },
        save: function () {
            if ($("#form").valid()) {
                save();
            }
        },
        configureBP: function () {
            $("#form\\:budget-program").select2({
                width: "100%",
                placeholder: "Seleccione un programa presupuestal"
            });
        },
        configurePBP: function (placeholder) {

            $("#form\\:product-budget-program").select2({
                width: "100%",
                placeholder: placeholder
            });

        },
        back: function () {
            var verification = true;
            $("#form input.form-control").each(function () {
                if ($(this).val() != "") {
                    verification = false;
                }
            });
            $("#form select").each(function () {
                if (this.value != "") {
                    verification = false;
                }
            });
            if (!verification) {
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
            ABP.save();
            return false;
        }
    })
    $("#code").focus();
    $("#create-again").on("change", function () {
        $("#form\\:create-again").val(this.checked);
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
            "form:budget-program": {
                required: true
            },
            "form:product-budget-program": {
                required: true
            },
            "form:functional-sequence": {
                minlength: 4,
                maxlength: 4
            }
        },
        messages: {
            "form:code": {
                required: "Por favor ingrese el codigo"
            },
            "form:name": {
                required: "Por favor ingrese el nombre"
            },
            "form:budget-program": {
                required: "Por favor seleccione un programa presupuestario"
            },
            "form:product-budget-program": {
                required: "Por favor seleccione un producto/proyecto"
            },
            "form:functional-sequence": {
                minlength: "La secuencia funcional debe tener 4 digitos",
                maxlength: "La secuencia funcional debe tener 4 digitos"
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
})

